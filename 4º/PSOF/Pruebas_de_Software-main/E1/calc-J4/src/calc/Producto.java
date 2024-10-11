package calc;

public class Producto {

	private double value;
	
	public Producto() {
	}
	
	public Producto(double value) {
		this.value = value;
	}
	
	public double getProducto(double a, double b) {
		this.value = a * b;
		return this.value;
	}
	
	public double multiplica(double prod) {
		this.value *= prod;
		return this.value;
	}
}
