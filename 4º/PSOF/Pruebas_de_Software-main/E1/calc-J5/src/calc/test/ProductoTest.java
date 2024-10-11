package calc.test;

import calc.Producto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductoTest {

	@Test
	public void testGetProducto() {
		
		Producto producto = new Producto();
		double resultado = producto.getProducto(2.0, 2.0);
		Assertions.assertEquals(resultado, 4);
	}
	
	@Test
	public void testMultiplica() {
		
		Producto producto = new Producto(8.0);
		double resultado = producto.multiplica(5.5);
		Assertions.assertEquals(resultado, 44.0);
	}
}
