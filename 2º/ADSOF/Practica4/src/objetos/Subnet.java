package objetos;

import java.util.ArrayList;
import Interfaces.*;

public class Subnet implements IConnectable{
    
    private int Id;
    private ArrayList<Node> nodeList = new ArrayList<>();
    private IConnectable parent;

    public Subnet(Node... nodes){

        setThisId(BlockchainNetwork.incrId());

        for(Node n: nodes){

            if(n instanceof MiningNode){
                
                nodeList.add((MiningNode)n);
                ((MiningNode)n).setParent(this);

            } else{
                
                nodeList.add(n);
                n.setParent(this);  
            }
        }
    }

    public void broadcast(IMessage message){
    	if (message instanceof TransactionNotification) {
    		((TransactionNotification)message).process(this);
    	} else if (message instanceof ValidateBlockRq){
    		((ValidateBlockRq)message).process(this);
    	} else if (message instanceof ValidateBlockRes){
    		((ValidateBlockRes)message).process(this);
    	}

        System.out.println("Broadcasting to " + this.nodeList.size() + " nodes:");

        for(Node n: this.nodeList){
        	(n instanceof MiningNode ? (MiningNode)n : n).broadcast(message);
        }
    }

    public String fullName(){

        String text = "";

        text += "Subnet#";

        if(getThisId() < 100){
            
            text += "0";
        }

        if(getThisId() < 10){

            text += "0";
        }

        text += getThisId();

        return text;
    }

    //Getters

    public IConnectable getParent(){

        return this.parent;
    }

    public int getThisId(){

        return Id;
    }

    public ArrayList<Node> getNodes(){

        return this.nodeList;
    }

    //Setters

    public void setThisId(int n){

        Id += n;
    }

    public void setParent(IConnectable parent){

        this.parent = parent;
    }
}
