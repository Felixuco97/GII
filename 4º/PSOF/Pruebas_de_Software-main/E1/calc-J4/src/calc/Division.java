package calc;

public class Division {
	
	private double value = 1;
	
	public Division() {
	}
	
	public Division(int value) {
		this.value = value;
	}
	
	public double getDivision(int a, int b) {
		this.value = a / b;
		return this.value;
	}
	
	public double divide(int div) {
		this.value /= div;
		return this.value;
	}
}
