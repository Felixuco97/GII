package objetos;

import AdditionalFiles.*;
import Interfaces.IMiningMethod;

public class SimpleMining implements IMiningMethod {

	@Override
    public String createHash(Block block) {
		String previousBlockHash = (block.getPreviousBlock() == null) ? BlockConfig.GENESIS_BLOCK : block.getPreviousBlock().getHash();
        String input = block.getVersion() + previousBlockHash + block.getTimestamp() 
                       + block.getDifficulty() + block.getNonce();
        return CommonUtils.sha256(input);
    }

	@Override
	public Block mineBlock(Transaction transaction, Block previousConfirmedBlock, String minerKey) {
	    Block newBlock = new Block(transaction, previousConfirmedBlock);
	    newBlock.setNonce((int)(Math.random()*1001));
	    String hash;
	    /*int nonce = newBlock.getNonce();*/
        hash = createHash(newBlock);
        newBlock.setHash(hash);
	    /*do {
	        int nonce = newBlock.getNonce();
	        hash = createHash(newBlock);
	        if (!hash.startsWith(getDifficultyString(newBlock.getDifficulty()))) {
	            nonce++;
	            newBlock.setNonce(nonce);
	        } else {
	            newBlock.setHash(hash);
	            System.out.println("[MiningNode#" + minerKey + "] Mined block: id:" + newBlock.getId() + 
	                               ", v:" + newBlock.getVersion() + ", nonce:" + newBlock.getNonce() + 
	                               ", ts:" + newBlock.getTimestamp() + ", diff:" + newBlock.getDifficulty() + 
	                               ", hash:" + newBlock.getHash() + ", minerK: " + minerKey);
	            break;
	        }
	    } while (true);*/

	    return newBlock;
	}

    /*private String getDifficultyString(int difficulty) {
        // Genera una cadena de ceros con longitud igual a la dificultad, para comparar con el hash
        return String.format("%0" + difficulty + "d", 0).replace("0", "0");
    }*/

}
