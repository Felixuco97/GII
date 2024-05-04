package objetos;

import Interfaces.*;

public class TransactionNotification implements IMessage{
    
    private Transaction transaction;

    public TransactionNotification(Transaction transaction){

        this.transaction = transaction;
    }

    public void process(Subnet subnet){
        System.out.println("[" + subnet.fullName() + "] " + this.getMessage());
    }

    //Getters

    public Transaction getTransaction(){

        return this.transaction;
    }

    public String getMessage(){

        String text = "";

        text += "Transaction " + this.transaction.getThisId() + "|" + " from: " + this.transaction.getOriginKey() + ", to: " + this.transaction.getDestinationKey() + ", quantity: " + this.transaction.getValue();

        return text;
    }
}
