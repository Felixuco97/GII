package explorador;

public class Hada extends Mago {
	public Hada(String nombre, int energia, Posada posada, int poder) {
		super(nombre, energia, posada, poder);
	}
	public boolean puedeAlojarseEn(Posada p) { return p.getLuz().compareTo(Luz.GRIS) > 0; }
	public String toString() { return super.toString() + " es Hada, con poder " +  this.getPoder(); }
}

