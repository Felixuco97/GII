package calc.test;

import calc.Producto;
import org.junit.Assert;
import org.junit.Test;

public class ProductoTest {

	@Test
	public void testGetProducto() {
		
		Producto producto = new Producto();
		double resultado = producto.getProducto(2.0, 2.0);
		Assert.assertEquals("Al multiplicar 2 por 2 debería dar 4", resultado, 4, 0);
	}
	
	@Test
	public void testMultiplica() {
		
		Producto producto = new Producto(8.0);
		double resultado = producto.multiplica(5.5);
		Assert.assertEquals("Al multiplicar 8 por 5 debería dar 44", resultado, 44.0, 0);
	}
}
