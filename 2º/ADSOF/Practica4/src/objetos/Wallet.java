package objetos;

public class Wallet {
    
    private String userName;
    private String key;
    private int totalBalance;

    public Wallet(String userName, String key, int totalBalance){

        this.userName = userName;
        this.key = key;
        this.totalBalance = totalBalance;
    }

    public String toString(){

        return "u: " + this.getUserName() + ", PK:" + this.getPublicKey() + ", balance: " + this.getTotalBalance();
    }

    //Getters

    public String getUserName(){

        return this.userName;
    }

    public String getPublicKey(){

        return this.key;
    }

    public int getTotalBalance(){

        return this.totalBalance;
    }

    //Setters

    public void setUserName(String name){

        this.userName = name;
    }

    public void setKey(String k){

        this.key = k;
    }

    public void setTotalBalance(int balance){

        this.totalBalance = balance;
    }
}
