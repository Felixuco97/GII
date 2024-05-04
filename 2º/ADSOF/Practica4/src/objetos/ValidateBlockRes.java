package objetos;

import Interfaces.IMessage;

public class ValidateBlockRes implements IMessage {
	
	private Block block;
    private Boolean res;
    
    public Block getBlock() {
		return block;
	}


	public Boolean getRes() {
		return res;
	}


	public ValidateBlockRes(Block block, Boolean res){

        this.res = res;
        this.block = block;
    }


	@Override
	public String getMessage() {
		String text = "";

        text += "Received Task:  ValidateBlockRes <b:" + this.getBlock().getId() + ", res:" + this.getRes() + ", src:001>";

        return text;
	}

	public void emitir(MiningNode miningNode) {
		System.out.println("[" + miningNode.fullName() + "] " + "Emmited Task:  ValidateBlockRes <b:" + this.getBlock().getId() + ", res:" + this.getRes() + ", src: 001>");
	}
	
	public void process(Node n){
        System.out.println("[" + n.fullName() + "] " + this.getMessage());
    }
    
    public void process(Subnet sb){
        System.out.println("[" + sb.fullName() + "] ValidateBlockRes");
    }

}
