package objetos;

import Interfaces.*;

public class ValidateBlockRq implements IMessage{
    
    private Node nodo;
    private Block block;

    public ValidateBlockRq(Node nodo, Block block){

        this.nodo = nodo;
        this.block = block;
    }

    public void process(Node n){
        System.out.println("[" + n.fullName() + "] " + this.getMessage());
    }

    public void process(Subnet sb){
        System.out.println("[" + sb.fullName() + "] ValidateBlockRq");
    }

    //Getters

    public String getMessage(){

        String text = "";

        text += "Received Task: ValidateBlockRq: <b:" + this.getBlock().getId() + ", src:" + String.format("%03d", this.getNodo().getThisId()) + ">";

        return text;
    }

	public Node getNodo() {
		return nodo;
	}

	public void setNodo(Node nodo) {
		this.nodo = nodo;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
}