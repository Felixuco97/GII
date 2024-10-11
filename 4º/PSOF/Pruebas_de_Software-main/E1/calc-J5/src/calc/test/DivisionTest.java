package calc.test;

import calc.Division;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DivisionTest {
	
	@Test
	public void testDivisionByZero() {
		Division division = new Division();
		Assertions.assertThrows(ArithmeticException.class,()-> division.getDivision(2, 0));
	}

	@Test
	public void testGetDivision() {
		
		Division division = new Division();
		
		double resultado = division.getDivision(100, 4);
		
		Assertions.assertEquals(resultado, 25);
	}
	
	@Test
	public void testDivide() {
		
		Division division = new Division(12);
		
		double resultado = division.divide(6);
		
		Assertions.assertEquals(resultado, 2);
	}
}
