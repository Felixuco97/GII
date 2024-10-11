package explorador.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import explorador.*;

public class TrampaTester {
	
	@Test
	public void getDestinoNormalTester() {
		
		Posada p1 = new Posada("VIPS", 7);
		
		Posada p2 = new Posada("Aldi", 8);
		
		Camino t = new Trampa(p1, p2, 9, 3.5, 1);
		
		assertEquals(p1, t.getDestino());
	}
	
	@Test
	public void getDestinoRecursivoTester() {
		
		Posada p1 = new Posada("VIPS", 7);
		
		Posada p2 = new Posada("Aldi", 8);
		
		Camino t = new Trampa(p1, p2, 9, 5.5, 0.1);
		
		assertEquals(p1, t.getDestino());
	}
	
	@Test
	public void testToString() {
		Posada p1 = new Posada("VIPS", 7);
		
		Posada p2 = new Posada("Aldi", 8);
		
		Camino t = new Trampa(p1, p2, 9, 5.5, 0.1);
		assertEquals(t.toString(), "(VIPS--9-->Aldi)");
	}
}
