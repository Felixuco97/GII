package explorador.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import explorador.*;

public class ExploradorTester {

	@Test
	public void recorreCaminoConExitoTester() {
		
		Posada p1 = new Posada("MCDonalds", 8);
		
		Posada p2 = new Posada("BurgerKing", 9);

		Camino c1 = new Camino(p1, p2, 6);
		
		Explorador exp = new Explorador("Mario", 10, p1);
		
		assertTrue(exp.recorre(c1));
	}
	
	@Test
	public void recorreCaminoConFracasoUnoTester() {
		
		Posada p1 = new Posada("MCDonalds", 8);
		
		Posada p2 = new Posada("BurgerKing", 9);
		
		Explorador exp = new Explorador("Mario", 10, p2);

		Camino c1 = new Camino(p1, p2, 6);
		
		assertFalse(exp.recorre(c1));
	}
	
	@Test
	public void recorreCaminoConFracasoDosTester() {
		
		Posada p1 = new Posada("MCDonalds", 8);
		
		Posada p2 = new Posada("BurgerKing", 9);
		
		Explorador exp = new Explorador("Mario", 4, p1);

		Camino c1 = new Camino(p1, p2, 6);
		
		assertFalse(exp.recorre(c1));
	}
	
	@Test
	public void recorreCaminoConFracasoTresTester() {
		
		Posada p1 = new Posada("MCDonalds", 8);
		
		Posada p2 = new Posada("BurgerKing", 9, Luz.NEGRA);
		
		Explorador exp = new Hada("Campanilla", 7, p1, 5);

		Camino c1 = new Camino(p1, p2, 6);
		
		assertFalse(exp.recorre(c1));
	}
	
	@Test
	public void recorrePosadasConExitoTester() {
		
		Posada p1 = new Posada("MCDonalds", 8);
		
		Posada p2 = new Posada("BurgerKing", 9);
		
		Posada p3 = new Posada("LeroyMerlin", 7);
		
		Posada p4 = new Posada("Carrefour", 7);

		Camino c1 = new Camino(p1, p2, 6);
		
		Camino c2 = new Camino(p2, p3, 5);
		
		Camino c3 = new Camino(p3, p4, 7);
		
		p1.addCamino(c1); p2.addCamino(c2); p3.addCamino(c3);
	
		Explorador exp = new Explorador("Mario", 9, p1);
		
		assertTrue(exp.recorre(p2, p3, p4));
	}
	
	@Test
	public void recorrePosadasConFracasoTester() {
		
		Posada p1 = new Posada("MCDonalds", 8);
		
		Posada p2 = new Posada("BurgerKing", 9);
		
		Posada p3 = new Posada("LeroyMerlin", 7);
		
		Posada p4 = new Posada("Carrefour", 7);

		Camino c1 = new Camino(p1, p2, 7);
		
		Camino c2 = new Camino(p2, p3, 10);
		
		Camino c3 = new Camino(p3, p4, 9);
		
		p1.addCamino(c1); p2.addCamino(c2); p3.addCamino(c3);
	
		Explorador exp = new Explorador("Mario", 9, p1);
		
		assertFalse(exp.recorre(p2, p3, p4));
	}
	
	@Test
	public void testToString() {
		Posada p1 = new Posada("MCDonalds", 8);
		Explorador exp = new Explorador("Mario", 9, p1);
		assertEquals(exp.toString(), "Mario(e:9) en MCDonalds");
	}

}
