package Excepciones;

public class TransactionException extends RuntimeException{

    private String source;
    private String receiver;
    private int amount;
    
    public TransactionException(String source, String receiver, int amount){

        this.source = source;
        this.receiver = receiver;
        this.amount = amount;
    }

    public String toString(){

        return "Negative transfer attempt: source: " + this.source + ", receiver: " + this.receiver + ", amount: " + this.amount;
    }
}
