package explorador.test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import explorador.Hechicero;
import explorador.Camino;
import explorador.Posada;
import explorador.Trampa;

public class HechiceroTester {
	private Posada p1;
	private Posada p2;
	private Camino c;
	private Hechicero h;

	
	@BeforeEach
	void setUp() throws Exception{
		p1 = new Posada("Posada 1");
		p2 = new Posada("Posada 2");
		c = new Camino(p1, p2, 1);


	}
	
	@Test
	/**
	 * The test tries to host a sorcerer in an inn where his power is enough because he has 5 of power and he needs at least 4 of power
	 */
	void testPuedeAlojarseEn1() {
		h = new Hechicero("Hechicero 1", 5, p1, 5);
		assertTrue(h.puedeAlojarseEn(p2));
		
	}
	
	@Test
	/**
	 * The test tries to host a sorcerer in an inn where his power is not enough because he needs at least 4 of power
	 */
	void testPuedeAlojarseEnConFracaso() {
		h = new Hechicero("Hechicero 1", 5, p1, 3);
		assertFalse(h.puedeAlojarseEn(p2));
		
	}
	

	@Test
	void testPuedeRecorrer() {
		h = new Hechicero("Hechicero 1", 5, p1, 5);
		c = new Camino(p1, p2, 1);
		assertTrue(h.puedeRecorrer(c));
	}
	
	@Test
	void testPuedeRecorrerConFracaso1() {
		h = new Hechicero("Hechicero 1", 1, p1, 5);
		c = new Camino(p1, p2, 3);
		assertFalse(h.puedeRecorrer(c));
	}
	
	@Test
	void testPuedeRecorrerConFracaso2() {
		h = new Hechicero("Hechicero 1", 1, p1, 5);
		c = new Trampa(p1, p2, 3, 3, 1);
		assertFalse(h.puedeRecorrer(c));
	}
	
	@Test
	void testPuedeRecorrerConFracaso3() {
		h = new Hechicero("Hechicero 1", 5, p1, 5);
		c = new Trampa(p1, p2, 1, 1, 1);
		assertFalse(h.puedeRecorrer(c));
	}
	
	@Test
	void testToString() {
		h = new Hechicero("Hechicero 1", 5, p1, 5);
		assertEquals(h.toString(), "Hechicero 1(e:5) en Posada 1 es Hechicero, con poder 5");
	}
}