package objetos;

import java.util.Date;
import AdditionalFiles.BlockConfig;

public class Block {
    private static int blockCount = 0;
    private final int id;
    private int version;
    private int nonce;
    private final int timestamp;
    private int difficulty;
    private final Transaction transaction;
    private boolean isValidated;
    private String hash;
    private final Block previousBlock;

    public Block(Transaction transaction, Block previousBlock) {
        this.id = blockCount++;
        this.version = BlockConfig.VERSION;
        this.nonce = (int)(Math.random()*1001); // Núm. aleatorio entre 0 y 1000 de un solo uso, usado para evitar ataques de replicación en las comunicaciones
        this.timestamp = (int)(new Date().getTime()/ 1000); // Momento en que se mina el nodo
        this.difficulty = BlockConfig.DIFFICULTY;
        this.transaction = transaction;
        this.isValidated = false; // El bloque no está validado por defecto
        this.previousBlock = previousBlock;
    }

    // Getters y Setters

    public int getId() {
		return id;
	}

	@Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", version=" + version +
                ", nonce=" + nonce +
                ", timestamp=" + timestamp +
                ", difficulty=" + difficulty +
                ", transaction=" + (transaction != null ? transaction.toString() : "null") +
                ", isValidated=" + isValidated +
                ", hash='" + hash + '\'' +
                ", previousBlock=" + (previousBlock == null ? "null" : previousBlock.getId()) +
                '}';
    }

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public int getVersion() {
		return version;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public String getHash() {
		return hash;
	}

	public Block getPreviousBlock() {
		return previousBlock;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}

	public boolean isValidated() {
		return isValidated;
	}

	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}

	public Transaction getTransaction() {
		return transaction;
	}
	
}