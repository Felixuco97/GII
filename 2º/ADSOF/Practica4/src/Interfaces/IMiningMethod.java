package Interfaces;

import objetos.Block;
import objetos.Transaction;

public interface IMiningMethod {
	String createHash(Block block);
	Block mineBlock(Transaction transaction, Block previousConfirmedBlock, String minerKey);
}