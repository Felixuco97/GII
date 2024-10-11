package calc.test;

import calc.Division;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class DivisionTest {
	
	@Test
	public void testDivisionByZero() {
		Division division = new Division();
		Assert.assertThrows(ArithmeticException.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				division.getDivision(2, 0);
			}
       });
	}

	@Test
	public void testGetDivision() {
		
		Division division = new Division();
		
		double resultado = division.getDivision(100, 5);
		
		Assert.assertEquals("Al dividir 100 entre 5 debería dar 20", resultado, 20, 0);
	}
	
	@Test
	public void testDivide() {
		
		Division division = new Division(12);
		
		double resultado = division.divide(6);
		
		Assert.assertEquals("Al dividir 12 entre 6 debería dar 2", resultado, 2, 0);
	}
}
