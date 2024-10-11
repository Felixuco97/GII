package explorador;

public class Explorador {
	private String nombre;
	private int energia;
	private Posada ubicacion;
	public Explorador (String nombre, int energia, Posada posada) { 
		this.nombre = nombre; this.energia = energia; this.ubicacion = posada;
	}	
	public String getNombre() { return this.nombre; }
	public Integer getEnergia() { return this.energia; }
	public Posada getUbicacion() { return this.ubicacion; }
	public boolean recorre(Camino c) {
		if ( this.ubicacion != c.getOrigen() ) return false;
		if (! this.puedeRecorrer(c) ) return false; // verison previa: if ( c.getCoste() >= this.energia ) return false;
		if (! this.puedeAlojarseEn(c.getDestino()) ) return false;
		ubicacion = c.getDestino();
		energia = energia - c.getCosteReal() +c.getDestino().getEnergia() ;
		return true;
	}
	public boolean puedeRecorrer(Camino c) { return c.getCoste() < this.energia; }
	public boolean puedeAlojarseEn(Posada c) { return true; }
	public boolean recorre(Posada... posadas) {
		boolean recorridoSinFallos = true;
		for (Posada p : posadas) {
			Camino c = this.getUbicacion().getCamino(p);
			Boolean caminoRecorrido = recorre(c);
			if (! caminoRecorrido) recorridoSinFallos = false;
		}
		return recorridoSinFallos;
	}
	public String toString() { return nombre + "(e:" + energia + ") en " + ubicacion.getNombre(); }
}


