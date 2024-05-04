package objetos;

import java.util.*;

import Interfaces.*;

public class MiningNode extends Node{

    private int compCapacity;
    private IMiningMethod miningMethod = new SimpleMining();
    private IValidateMethod validationMethod = new SimpleValidate();
    private ArrayList<Block> minedBlocks = new ArrayList<>();

    public MiningNode(Wallet wallet, int compCapacity){

        super(wallet);
        this.compCapacity = compCapacity;
    }

    public String fullName(){

        String text = "";

        text += "MiningNode#";

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

    public int getCompCapacity(){

        return this.compCapacity;
    }

    public ArrayList<Transaction> getTransactions(){

        return super.getTransactions();
    }

    public IMiningMethod getMiningMethod(){

        return this.miningMethod;
    }

    public ArrayList<Block> getMinedBlock(){

        return this.minedBlocks;
    }

	public IValidateMethod getValidationMethod() {
		return validationMethod;
	}

	public void setValidationMethod(IValidateMethod validationMethod) {
		this.validationMethod = validationMethod;
	}

	public void setMiningMethod(IMiningMethod miningMethod) {
		this.miningMethod = miningMethod;
	}
	public Block getLastMinedBlock() {
        if (minedBlocks.isEmpty()) {
            return null;
        }
        return minedBlocks.get(minedBlocks.size() - 1);
    }
	
	@Override
	public void broadcast(IMessage notif) {
        if (notif instanceof TransactionNotification) {
        	notif.process(this);
            Transaction transaction = ((TransactionNotification) notif).getTransaction();

            // Verificar si la transacción no está confirmada
            if (!this.getTransactions().contains(transaction)) {
            	
                Block previousConfirmedBlock = this.getLastMinedBlock();
                String minerKey = this.getWalletKey();

                // Minar un nuevo bloque con la transacción
                Block newBlock = this.getMiningMethod().mineBlock(transaction, previousConfirmedBlock, minerKey);
                System.out.println(this.getStringMinado(newBlock));
                this.minedBlocks.add(newBlock);
                
                // Solicitud de validar el bloque
                ValidateBlockRq ValidateBR = new ValidateBlockRq(this, newBlock);
                this.getTopParent().broadcast(ValidateBR);
                
                // En algun momento añadiremos la transac a las hechas por el nodo
                this.getTransactions().add(transaction);
                
            } else {
                System.out.println("[" + this.fullName() + "] " + "Transaction already confirmed: Tx-1");
            }
        } else if (notif instanceof ValidateBlockRq) {
        	((ValidateBlockRq)notif).process(this);
        	// Ahora toca validar de verdad :))
        	// Si es su propio bloque y no lo puede validar, manda 1 true sin comprobar nada
        	if (minedBlocks.contains(((ValidateBlockRq)notif).getBlock())) {
        		System.out.println("[" + this.fullName() + "] " + "You cannot validate your own block");
        		((BlockchainNetwork)this.getTopParent()).receiveValidation(((ValidateBlockRq)notif).getBlock(), true);
        	} else {
        		ValidateBlockRes validateRes = new ValidateBlockRes( ((ValidateBlockRq)notif).getBlock() , (this.getValidationMethod()).validate(this.getMiningMethod(), ((ValidateBlockRq)notif).getBlock()) );
        		validateRes.emitir(this);
        		((BlockchainNetwork)this.getTopParent()).receiveValidation(validateRes.getBlock(), validateRes.getRes());
        		
        	}
        } else if (notif instanceof ValidateBlockRes) {
        	if (((ValidateBlockRes)notif).getRes()==true) {
        		((ValidateBlockRes)notif).process(this);
        		// Modificamos todo lo que haga falta en el nodo para completar la transac
        		// Añadir transaccion a la lista del nodo
        		this.getTransactions().add((((ValidateBlockRes)notif).getBlock()).getTransaction());
        		System.out.println("["+ this.fullName()+"] Commiting transaction : Tx-" + (((ValidateBlockRes)notif).getBlock()).getTransaction().getThisId() + " in "+this.fullName());
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
	public String getStringMinado(Block block) {
		
        String text = "";

        text += "[" + this.fullName() + "] Mined block: " ;
        text += "id:" + block.getId() + ", ";
        text += "v:" + block.getVersion() + ", ";
        text += "nonce:" + block.getNonce() + ", ";
        text += "ts:" + block.getTimestamp() + ", ";
        text += "diff:" + block.getDifficulty() + ", ";
        text += "hash:" + block.getHash() + ", ";
        text += "minerK: " + this.getWalletKey();

        return text;
    }
}
