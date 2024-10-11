package explorador.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import explorador.*;

public class PosadaTester {

	@Test
	public void addCaminoConExitoTester() {
		
		Posada p1 = new Posada("Cafeteria", 11);
		
		Posada p2 = new Posada("Panaderia", 12);
		
		Camino c = new Camino(p1, p2, 10);
		
		assertTrue(p1.addCamino(c));
	}
	
	@Test
	public void addCaminoConFracasoTester() {
		
		Posada p1 = new Posada("Cafeteria", 11);
		
		Posada p2 = new Posada("Panaderia", 12);
		
		Camino c = new Camino(p1, p2, 10);
		
		assertFalse(p2.addCamino(c));
	}
	
	@Test
	public void getCaminoConExitoTester() {
		
		Posada p1 = new Posada("Cafeteria");
		
		Posada p2 = new Posada("Panaderia", 10);
		
		Posada p3 = new Posada("Lidl", 12);
		
		Posada p4 = new Posada("Fosters", 11, Luz.BLANCA);
		
		Camino c1 = new Camino(p1, p2, 9);
		
		Camino c2 = new Camino(p1, p3, 11);
		
		Camino c3 = new Camino(p1, p4, 10);
		
		Camino c4 = new Camino(p2, p4, 9);
		
		p1.addCamino(c1); p1.addCamino(c2); p1.addCamino(c3); p2.addCamino(c4);
		
		assertEquals(c2, p1.getCamino(p3));
	}
	
	@Test
	public void getCaminoConFracasoTester() {
		
		Posada p1 = new Posada("Cafeteria");
		
		Posada p2 = new Posada("Panaderia", 10);
		
		Posada p3 = new Posada("Lidl", 12);
		
		Posada p4 = new Posada("Fosters", 11, Luz.BLANCA);
		
		Camino c1 = new Camino(p1, p2, 9);
		
		Camino c2 = new Camino(p1, p3, 11);
		
		Camino c3 = new Camino(p2, p4, 10);
		
		Camino c4 = new Camino(p3, p4, 9);
		
		p1.addCamino(c1); p1.addCamino(c2); p2.addCamino(c3); p3.addCamino(c4);
		
		assertNull(p1.getCamino(p4));
	}
	
	@Test
	public void testToString1() {
		Posada p1 = new Posada("MCDonalds", 8);
		assertEquals(p1.toString(), "MCDonalds(c:8)[]");
	}
	
	@Test
	public void testToString2() {
		Posada p1 = new Posada("MCDonalds", 8);
		p1.cambiarLuz(Luz.DIABOLICA);
		assertEquals(p1.toString(), "MCDonalds(c:8)[]. Luz: DIABOLICA");
	}
	
	@Test
	public void testAddCaminoOldVersionRemoved() {
		Posada p1 = new Posada("Cafeteria", 11);
		assertEquals(p1.getNumCaminos(), 0);
		p1.addCaminoOldVersionRemoved(p1, 1);
		assertEquals(p1.getNumCaminos(), 1);
	}

}
