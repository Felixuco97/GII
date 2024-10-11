package explorador.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import explorador.Camino;
import explorador.Hada;
import explorador.Luz;
import explorador.Posada;
import explorador.Trampa;

public class HadaTester {
	private Posada p1;
	private Posada p2;
	private Hada h;
	private Camino c;

	@BeforeEach
	void setUp() throws Exception {
		p1 = new Posada("Posada 1");
		p2 = new Posada("Posada 2");
	}

	@Test
	/**
	 * The test tries to host a fairy in an inn where his light is less than the inn
	 * light
	 */
	void testPuedeAlojarseEn1() {
		h = new Hada("Hada 1", 5, p1, 5);
		assertTrue(h.puedeAlojarseEn(p2));

	}

	@Test
	/**
	 * The test tries to host a fairy in an inn where his light is greater than the
	 * inn light
	 */
	void testPuedeAlojarseEnConFracaso() {
		h = new Hada("Hada 1", 5, p1, 5);
		p2.cambiarLuz(Luz.TENEBROSA);
		assertFalse(h.puedeAlojarseEn(p2));

	}

	@Test
	void testPuedeRecorrer() {
		h = new Hada("Hada 1", 5, p1, 5);
		c = new Camino(p1, p2, 1);
		assertTrue(h.puedeRecorrer(c));
	}

	@Test
	void testPuedeRecorrerConFracaso1() {
		h = new Hada("Hada 1", 1, p1, 5);
		c = new Camino(p1, p2, 3);
		assertFalse(h.puedeRecorrer(c));
	}

	@Test
	void testPuedeRecorrerConFracaso2() {
		h = new Hada("Hada 1", 1, p1, 5);
		c = new Trampa(p1, p2, 3, 3, 1);
		assertFalse(h.puedeRecorrer(c));
	}
	
	@Test
	void testPuedeRecorrerConFracaso3() {
		h = new Hada("Hada 1", 5, p1, 5);
		c = new Trampa(p1, p2, 1, 1, 1);
		assertFalse(h.puedeRecorrer(c));
	}
	
	@Test
	void testToString() {
		h = new Hada("Hada 1", 5, p1, 5);
		assertEquals(h.toString(), "Hada 1(e:5) en Posada 1 es Hada, con poder 5");
	}
}
