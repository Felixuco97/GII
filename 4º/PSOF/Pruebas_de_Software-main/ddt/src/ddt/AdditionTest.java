package ddt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class AdditionTest {
	
	@ParameterizedTest(name = "{index} => calculates the sum of {0}: ({0}, {1})")
	@CsvFileSource(resources = "/ddt/VALUES.CSV", numLinesToSkip = 0, delimiter = ';')
	public void test(int a, int b) {
		
		assertEquals(b + a, a + b, 0);
	}


}
