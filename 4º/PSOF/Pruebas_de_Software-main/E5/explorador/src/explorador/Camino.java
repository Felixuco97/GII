package explorador;

public class Camino {
	private static Integer costeTotal = 0;
	private Posada origen;
	private Posada destino;
	private int coste;
	public Camino (Posada origen, Posada destino, int coste) {
		this.origen = origen; this.destino = destino;  this.coste = coste;
		costeTotal = costeTotal + getCoste();
	}
	public static Integer getCosteTotal() { return Camino.costeTotal; }
	public Posada getOrigen() { return this.origen; }
	public Posada getDestino() { return this.destino; }
	public int getCoste() { return this.coste; }
	public static void setCoste(int coste) {Camino.costeTotal = coste;}
	public int getCosteReal() { return this.coste + getCosteEspecial(); }
	public String toString() { return "(" + origen.getNombre() + "--" + coste + "-->" +  destino.getNombre() + ")"; }
	public boolean esTrampa() { return false; }
	public int getCosteEspecial() { return 0; }
}
