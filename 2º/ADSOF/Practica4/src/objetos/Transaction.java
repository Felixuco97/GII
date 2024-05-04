package objetos;

public class Transaction {
    
    private static int id = 0;
    private int Id;
    private String originKey;
    private String destKey;
    private int value;

    public Transaction(Wallet origin, Wallet destination, int value){

        this.originKey = origin.getPublicKey();
        this.destKey = destination.getPublicKey();
        this.value = value;
        setThisId(incrId());
    }

    public Transaction(String originKey, String destKey, int value){

        this.originKey = originKey;
        this.destKey = destKey;
        this.value = value;
        setThisId(incrId());
    }

    public static int incrId(){

        return id++;
    }

    //Getters

    public int getId(){

        return id;
    }

    public int getThisId(){

        return this.Id;
    }

    public String getOriginKey(){

        return this.originKey;
    }

    public String getDestinationKey(){

        return this.destKey;
    }

    public int getValue(){

        return this.value;
    }

    //Setters

    public void setThisId(int n){

        this.Id = n;
    }
}
