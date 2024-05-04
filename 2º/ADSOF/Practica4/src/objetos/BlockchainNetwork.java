package objetos;

import java.util.*;
import Excepciones.*;
import Interfaces.*;

public class BlockchainNetwork implements IConnectable{

    private static int blockId = -1;
    private String name;
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Subnet> subnets = new ArrayList<>();
    private ArrayList<Object> elements = new ArrayList<>();
    private Map<Block, Integer> validationResponses = new HashMap<>();
    private int nodesTotal;

    public BlockchainNetwork(String name){
        this.name = name;
        this.nodesTotal = 0;
    }

    public void processNode(Node n){
        if (n instanceof MiningNode){
            processMiningNode((MiningNode) n);

        } else{
            if(this.elements.contains(n)){
            	throw new ConnectionException(n);
            }

            for(Subnet sb: this.subnets){
                if(sb.getNodes().contains(n)){
                    throw new DupplicateConnectionException(n);
                }
            }

            if(!(n.getParent() instanceof Subnet)){

                System.out.print("ADSOF blockchain - new peer connected: ");
                n.setParent(this);
                nodes.add(n);
                nodesTotal++;
                elements.add(n);
                System.out.println(NodeConnectionNotification(n));
            } else {
            	nodesTotal++;
            }
        }
    }

    public void processMiningNode(MiningNode mn){

        if(this.elements.contains(mn)){
            throw new ConnectionException(mn);
        }

        for(Subnet sb: this.subnets){
            if(sb.getNodes().contains(mn)){
                throw new DupplicateConnectionException(mn);
            }
        }

        if(!(mn.getParent() instanceof Subnet)){
            System.out.print("ADSOF blockchain - new peer connected: ");
            mn.setParent(this);
            nodes.add(mn);
            nodesTotal++;
            elements.add(mn);
            System.out.println(MiningNodeConnectionNotification(mn));
        } else {
        	nodesTotal++;
        }
        
    }

    public BlockchainNetwork connect(Object o) throws ConnectionGeneralException{
        if(o instanceof Subnet){
            for(Node n: ((Subnet)o).getNodes()){
                processNode(n);
            }

            System.out.print("ADSOF blockchain - new peer connected: ");
            ((Subnet)o).setParent(this);
            subnets.add((Subnet)o);   
            elements.add((Subnet)o);
            System.out.println(SubnetConnectionNotification((Subnet)o));

        } else {
            processNode((Node)o);
        }

        return this;
    }

    public String NodeConnectionNotification(Node n){

        String text = "";

        text += n.getWallet() + " | " + "@" + n.fullName();

        return text;
    }

    public String MiningNodeConnectionNotification(MiningNode mn){

        String text = "";

        text += mn.getWallet() + " | " + "@" + mn.fullName();

        return text;
    }

    public String SubnetConnectionNotification(Subnet sub){

        String text = "";

        text += "Node network of " + sub.getNodes().size() + " nodes: [";

        for(Node n: sub.getNodes()){

            if(n instanceof MiningNode){

                text += MiningNodeConnectionNotification((MiningNode)n);

            } else {

                text += NodeConnectionNotification(n);
            }

            if(sub.getNodes().indexOf(n) < (sub.getNodes().size()-1)){

                text += "; ";
            }
        }

        text += "]";

        return text;
    }

    @Override
    public String toString(){

        String text = "";

        text += "ADSOF blockchain consists of " + this.elements.size() + " elements:\n";

        for(Object o: this.elements){

            text += "* ";

            if(o instanceof Subnet){

                text += SubnetConnectionNotification((Subnet)o);
            }

            if(o instanceof Node){

                if(o instanceof MiningNode){

                    text += MiningNodeConnectionNotification((MiningNode)o);

                } else{

                    text += NodeConnectionNotification((Node) o);
                }
            }

            text += "\n";
        }

        return text;
    }

    public void broadcast(IMessage message){

    	if (message instanceof TransactionNotification) {

    		for(Object o: this.elements){

                if(o instanceof Node){
                	(o instanceof MiningNode ? (MiningNode)o : (Node)o).broadcast((TransactionNotification)message);
                }

                if(o instanceof Subnet){
                    ((Subnet)o).broadcast((TransactionNotification)message);
                }
            }
        } else if (message instanceof ValidateBlockRq) {
        	//int i = 0;
        	for(Object o: this.elements){
                if(o instanceof Node){
                	(o instanceof MiningNode ? (MiningNode)o : (Node)o).broadcast((ValidateBlockRq)message);
                }
                if(o instanceof Subnet){
                    ((Subnet)o).broadcast((ValidateBlockRq)message);
                }
                //i++;
            }
        } else if (message instanceof ValidateBlockRes) {
        	(((ValidateBlockRes)message).getBlock()).setValidated(true);
        	//aquí tenemos que mandar la ValidateBlockRes a todos los nodoss
        	for(Object o: this.elements){
                if(o instanceof Node){
                	(o instanceof MiningNode ? (MiningNode)o : (Node)o).broadcast((ValidateBlockRes)message);
                }

                if(o instanceof Subnet){
                    ((Subnet)o).broadcast((ValidateBlockRes)message);
                }
            }
          }
        
    }
    
    public void receiveValidation(Block block, boolean isValid) {
        validationResponses.merge(block, isValid ? 1 : 0, Integer::sum);
        checkConsensus(block);
    }
    
    private void checkConsensus(Block block) {
        int validCount = validationResponses.getOrDefault(block, 0);
        if (validCount == nodesTotal) {
            broadcast(new ValidateBlockRes(block, true));
        } else if (validCount + (nodesTotal - validCount) < nodesTotal) {
            broadcast(new ValidateBlockRes(block, false));
        }
    }
    

    public static int incrId(){
        return ++blockId;
    }

    //Getters

    public IConnectable getParent(){
        return null;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Object> getElements(){
        return this.elements;
    }
    
}
