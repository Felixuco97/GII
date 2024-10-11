package explorador;

public class Trampa extends Camino {
	private double factorCosteExtra;
	private double probabilidaRetorno;
	public Trampa (Posada origen, Posada destino, int coste, double factorCosteExtra, double probabilidaRetorno) {
		super(origen, destino, coste);
		this.factorCosteExtra = factorCosteExtra;
		this.probabilidaRetorno = probabilidaRetorno;
	}
	public boolean esTrampa() { return true; }
	public Posada getDestino() { 
		if (Math.random() <= probabilidaRetorno) return this.getOrigen();
		else return this.getDestino(); 
	}
	public int getCosteEspecial() { return  Math.round(  getCoste() *  (float) factorCosteExtra ) ; }
}

