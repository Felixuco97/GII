package explorador;

import java.io.*;

public class ExploradoresBasicos {

	public static void main(String[] args) {
		
		Posada pA = new Posada("A", 3);
		Posada pB = new Posada("B", 10);
		Posada pC = new Posada("C"); // , 2);
		Posada pD = new Posada("D", 1); 
		Posada pE = new Posada("E", 3);
		Posada pF = new Posada("F", 0);
		Posada[] posadas = { pA, pB, pC, pD, pE, pF};
		pA.addCamino( new Camino(pA, pB, 1));
		pA.addCamino( new Camino(pA, pC, 5));
		pB.addCamino( new Camino(pB, pD, 7));
		pB.addCamino( new Camino(pB, pE, 4));
		pC.addCamino( new Camino(pC, pD, 6));
		pC.addCamino( new Camino(pC, pE, 8));
		pD.addCamino( new Camino(pD, pF, 3));
		pE.addCamino( new Camino(pE, pF, 0));
		/*
        pA.addCaminoOldVersionRemoved(pB, 1); 
        pA.addCaminoOldVersionRemoved(pC, 5);
        pB.addCaminoOldVersionRemoved(pD, 7); 
        pB.addCaminoOldVersionRemoved(pE, 4);
        pC.addCaminoOldVersionRemoved(pD, 6); 
        pC.addCaminoOldVersionRemoved(pE, 8);
        pD.addCaminoOldVersionRemoved(pF, 3);
        pE.addCaminoOldVersionRemoved(pF, 0);
        */
        for (Posada p : posadas) { System.out.println(p); }
        Explorador e1 = new Explorador("Marco", 15, pB); 
        Explorador e2 = new Explorador("Maria", 10, pA);
        System.out.println(e1);
        if ( e1.recorre( pA.getCamino(0) ) )  System.out.println(e1);
        else System.out.println("e1: no avanza");
        System.out.println(e2);
        System.out.println( pA.getCamino(1) );
        System.out.println( pC.getCamino(0) );
        System.out.println( e2.recorre( pA.getCamino(1)) ); System.out.println(e2);
        System.out.println( e2.recorre( pC.getCamino(0)) );
        System.out.println(e2);
        
        try {
        	System.out.println("--");
        	new Simulacion("src/explorador/POSADAS.csv", "src/explorador/CAMINOS.csv", "src/explorador/EXPLORADOR.csv");
        } catch (IOException e) { System.out.println("Error en archivos"); }
        

        Posada solana = new Posada("Solana", 1);
        Posada romeral = new Posada("Romeral", 5); 
        Posada tomelloso = new Posada("Tomelloso"); // por defecto energ�a recuperada 2
        Explorador sancho = new Explorador("Sancho", 50, solana);
        solana.addCamino(new Camino(solana, romeral, 68));
        solana.addCamino(new Camino(solana, tomelloso, 33));
        System.out.println(sancho);
        sancho.recorre(romeral, tomelloso); // ir� directo a tomelloso sin pasar por romeral
        System.out.println(sancho); // energ�a  19 = 50 - 33  + 2
        tomelloso.addCamino(new Camino(tomelloso, romeral, 11));
        sancho.recorre(tomelloso.getCamino(romeral)); // getCaminoHacia
        System.out.println(sancho); // en Romeral con energ�a 13 = 19 - 11  + 5

        /* objetos especiales */
        
	}
}



