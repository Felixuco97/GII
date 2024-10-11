package explorador;

public class Hechicero extends Mago {
	public Hechicero(String nombre, int energia, Posada posada, int poder) {
		super(nombre, energia, posada, poder);
	}
	public boolean puedeAlojarseEn(Posada p) { return this.getPoder() > (p.getLuz().ordinal() - Luz.TENEBROSA.ordinal());   }
	public String toString() { return super.toString() + " es Hechicero, con poder " +  this.getPoder(); }
}

