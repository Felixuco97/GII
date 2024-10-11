package explorador.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import explorador.*;

public class CaminoTester {
	private int costePrevio;
	@Test
	public void getCosteTotal() {
		costePrevio = Camino.getCosteTotal();
		
		Camino.setCoste(0);
		
		Posada p1 = new Posada("Supermercado", 7);
		
		Posada p2 = new Posada("Hotel", 8);
		
		Posada p3 = new Posada("Bar", 5);
		
		Posada p4 = new Posada("Lonja", 6);
		
		Camino c1 = new Camino(p1, p2, 6);
		
		Camino c2 = new Camino(p2, p3, 4);
		
		Camino c3 = new Camino(p3, p4, 5);
		
		
		
		p1.addCamino(c1); p2.addCamino(c2); p3.addCamino(c3);
		
		assertEquals(15, Camino.getCosteTotal());
		
		Camino.setCoste(costePrevio);
		
	}
	
	@Test
	public void testToString() {
		Posada p1 = new Posada("VIPS", 7);
		Posada p2 = new Posada("Aldi", 8);
		Camino c1 = new Camino(p1, p2, 6);
		assertEquals(c1.toString(), "(VIPS--6-->Aldi)");
	}
}
