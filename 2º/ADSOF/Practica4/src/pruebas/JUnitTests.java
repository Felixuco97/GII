package pruebas;

import static org.junit.Assert.*;
import org.junit.Test;
import AdditionalFiles.CommonUtils;
import Excepciones.*;
import objetos.*;

public class JUnitTests {
    
    @Test
    public void test(){

        Wallet wallet1, wallet2, wallet3, wallet4;
        MiningNode miningNode, miningNode2, miningNode3;
        Node node;
        Subnet subnet;
        BlockchainNetwork network;

        wallet1 = new Wallet("Adrian", CommonUtils.sha1("PK-Adrian"), 1359);
        wallet2 = new Wallet("Fernando", CommonUtils.sha1("PK-Fernando"), 5603);
        wallet3 = new Wallet("Cristina", CommonUtils.sha1("PK-Cristina"), 2208);
        wallet4 = new Wallet("Mario", CommonUtils.sha1("PK-Mario"), 1103);

        //Create the nodes with the wallets
        node = new Node(wallet1);
        miningNode = new MiningNode(wallet2, 99999);

        //Create a subnet inside a network
        miningNode2 = new MiningNode(wallet3, 77777);
        miningNode3 = new MiningNode(wallet4, 66666);
        subnet = new Subnet(miningNode2, miningNode3);

        //Check the number of nodes in a subnet is correct
        assertEquals(2, subnet.getNodes().size());

        //Create the network and connect the elements
        network = new BlockchainNetwork("ADSOF JUnit Tests blockchain");
        network.connect(node).connect(subnet).connect(miningNode);

        //Check the number of elements in the network is correct
        assertEquals(3, network.getElements().size());

        //Create example transaction, which transfers 10 coins from wallet1 to wallet2
        try{
            network.connect(node); //cannot connect: node already in the network

        } catch(ConnectionException e){
            System.err.println(e);
        }

        //Check that the number of elements in the network has not been updated
        assertEquals(3, network.getElements().size());

        try{
            network.connect(miningNode3); //cannot connect: miningNode in a subnet
        } catch(DupplicateConnectionException e){
            System.err.println(e);
        }

        //Check that the number of elements in the network has not been updated in this case either
        assertEquals(3, network.getElements().size());

        miningNode.setMiningMethod(new SimpleMining());
		miningNode.setValidationMethod(new SimpleValidate());
		miningNode2.setMiningMethod(new SimpleMining());
		miningNode2.setValidationMethod(new SimpleValidate());

        try{
            Transaction tr0 = miningNode.createTransaction(wallet1.getPublicKey(), -758); //negative fails
            network.broadcast(new TransactionNotification(tr0));
        } catch (TransactionException e){
            System.err.println(e);
        }

        //Check the transaction has not been made
        assertEquals(1359, node.getWallet().getTotalBalance());
        assertEquals(5603, miningNode.getWallet().getTotalBalance());

        Transaction tr1 = node.createTransaction(wallet2, 557);
        network.broadcast(new TransactionNotification(tr1));

        //Check the transaction has been successful
        assertEquals(802, node.getWallet().getTotalBalance());
        assertEquals(6160,miningNode.getWallet().getTotalBalance());

        Transaction tr2 = miningNode2.createTransaction(wallet1.getPublicKey(), 643);
		network.broadcast(new TransactionNotification(tr2));

        //Check the transaction has been successful in this case, too
        assertEquals(1565, miningNode2.getWallet().getTotalBalance());
        assertEquals(1445, node.getWallet().getTotalBalance());
    }
}
