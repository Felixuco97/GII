package explorador;

public abstract class Mago extends Explorador {
	private int poder;
	public Mago(String nombre, int energia, Posada posada, int poder) {
		super(nombre, energia, posada);
		this.poder = poder;
	}
	public int getPoder() {return this.poder; }
	public boolean puedeRecorrer(Camino c) { return super.puedeRecorrer(c) && ! c.esTrampa(); }
	public abstract boolean puedeAlojarseEn(Posada p);
}

