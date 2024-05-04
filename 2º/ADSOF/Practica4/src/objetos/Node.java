package objetos;

import java.util.*;
import Excepciones.*;
import Interfaces.*;

public class Node implements IConnectable{

    private int Id;
    private Wallet wallet;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private IConnectable parent;

    public Node(Wallet wallet) {

        this.wallet = wallet;
        setThisId(BlockchainNetwork.incrId());
    }


    public Transaction createTransaction(Wallet destino, int n){

        Transaction trans = new Transaction(this.wallet, destino, n);

        this.transactions.add(trans);

        return trans;
    }

    public Transaction createTransaction(String destPublicKey, int amount) throws TransactionException{

        if(amount < 0){

            throw new TransactionException(this.wallet.getPublicKey(), destPublicKey, amount);
        }

        Transaction trans = new Transaction(this.wallet.getPublicKey(), destPublicKey, amount);

        this.transactions.add(trans);

        return trans;
    }

    public void broadcast(IMessage notif) {
        // Asegurarse de que el mensaje es una notificación de transacción
        if (notif instanceof TransactionNotification) {
           // Transaction transaction = ((TransactionNotification) notif).getTransaction();
            
            // Añadir la transacción a la lista de transacciones pendientes
            //this.getTransactions().add(transaction);
            ((TransactionNotification)notif).process(this instanceof MiningNode ? (MiningNode)this : this);
            
        } else if (notif instanceof ValidateBlockRq) {
        	((ValidateBlockRq)notif).process(this instanceof MiningNode ? (MiningNode)this : this);
        	// Si no es minero envío ya un true
        	if (this instanceof Node) {
        		((BlockchainNetwork)this.getTopParent()).receiveValidation(((ValidateBlockRq)notif).getBlock(), true);
        	}
        } else if (notif instanceof ValidateBlockRes) {
        	if (((ValidateBlockRes)notif).getRes()==true) {
        		((ValidateBlockRes)notif).process(this instanceof MiningNode ? (MiningNode)this : this);
        		// Modificamos todo lo que haga falta en el nodo para completar la transac
        		// Añadir transaccion a la lista del nodo
        		this.getTransactions().add((((ValidateBlockRes)notif).getBlock()).getTransaction());
        		System.out.println("["+ this.fullName()+"] Commiting transaction : Tx-" + (((ValidateBlockRes)notif).getBlock()).getTransaction().getThisId() + " in "+this.fullName());
        		// Miramos si hace falta actualizar la Wallet del nodo
                System.out.println("[" + this.fullName() + "] -> Tx details:Transaction " + (((ValidateBlockRes)notif).getBlock()).getTransaction().getThisId() + "| from " + ((ValidateBlockRes)notif).getBlock().getTransaction().getOriginKey() + ", to: " + ((ValidateBlockRes)notif).getBlock().getTransaction().getDestinationKey() + ", quantity: " + ((ValidateBlockRes)notif).getBlock().getTransaction().getValue());
        		// Miramos si hace falta actualizar la Wallet del nodo
                if(((ValidateBlockRes)notif).getBlock().getTransaction().getDestinationKey() == this.getWallet().getPublicKey()){
                    
                    System.out.println("[" + fullName() + "] Applied Transaction " + (((ValidateBlockRes)notif).getBlock()).getTransaction().getThisId() + " | from " + ((ValidateBlockRes)notif).getBlock().getTransaction().getOriginKey() + ", to: " + ((ValidateBlockRes)notif).getBlock().getTransaction().getDestinationKey() + ", quantity: " + ((ValidateBlockRes)notif).getBlock().getTransaction().getValue());
                    this.getWallet().setTotalBalance(this.getWallet().getTotalBalance()+((ValidateBlockRes)notif).getBlock().getTransaction().getValue());
                    System.out.println("[" + fullName() + "] New wallet value: "+this.getWallet().toString());
                }
                if(((ValidateBlockRes)notif).getBlock().getTransaction().getOriginKey() == this.getWallet().getPublicKey()){

                    System.out.println("[" + fullName() + "] Applied Transaction " + (((ValidateBlockRes)notif).getBlock()).getTransaction().getThisId() + " | from " + ((ValidateBlockRes)notif).getBlock().getTransaction().getOriginKey() + ", to: " + ((ValidateBlockRes)notif).getBlock().getTransaction().getDestinationKey() + ", quantity: " + ((ValidateBlockRes)notif).getBlock().getTransaction().getValue());
                    this.getWallet().setTotalBalance(this.getWallet().getTotalBalance()-((ValidateBlockRes)notif).getBlock().getTransaction().getValue());
                    System.out.println("[" + fullName() + "] New wallet value: "+this.getWallet().toString());
                }
        	}
        }
    }


    public String fullName(){

        String text = "";

        text += "Node#";

        if(getThisId() < 100){
            
            text += "0";
        }

        if(getThisId() < 10){

            text += "0";
        }

        text += getThisId();

        return text;
    }

    public String fullNameForException(){

        String text = "";

        text += "Node ";

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

    public Wallet getWallet(){

        return this.wallet;
    }

    public ArrayList<Transaction> getTransactions(){

        return this.transactions;
    }

    public int getThisId(){

        return this.Id;
    }

    //Setters

    public void setThisId(int id){

        this.Id = id;
    }

    public void setParent(IConnectable parent){

        this.parent = parent;
    }
    
    public String getWalletKey(){

        return this.getWallet().getPublicKey();
    }
}
