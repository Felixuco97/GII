package explorador;

import java.util.*;

public class Posada {
	private static final Integer ENERGIA_RECUPERADA_POR_DEFECTO = 2;
	private String nombre;
	private int energia;
	private ArrayList<Camino> caminos;
	private Luz luz = Luz.BLANCA;

	public Posada (String nombre, int energia) { 
		 this.nombre = nombre; 
		 this.energia = energia; 
		 caminos = new ArrayList<Camino>(); 
	}
	public Posada (String nombre, int energia, Luz luz) { 
		 this(nombre, energia);
		 this.luz = luz;
	}
	public Posada (String nombre) { 
		 this(nombre, ENERGIA_RECUPERADA_POR_DEFECTO);
	}
	public Luz getLuz() { return this.luz; }
	public void cambiarLuz(Luz luz) { this.luz = luz; }
	public int getEnergia() { return this.energia; }
	public String getNombre() { return this.nombre; }
	public boolean addCamino(Camino c) {
		if (c.getOrigen() != this) return false;
		caminos.add( c );
		return true;
	}
	public  void addCaminoOldVersionRemoved(Posada p, int c) {
		caminos.add( new Camino(this, p, c));
	}
	public Camino getCamino(int i) { return caminos.get(i); }
	public int getNumCaminos() { return caminos.size(); }
	public String toString() { return nombre + "(c:" + energia + ")" +  caminos + (this.luz == Luz.BLANCA ? "" : ". Luz: " + this.luz); }
	public Camino getCamino(Posada sgtePosada) { // getCaminoHacia
		for (Camino c : caminos) 
			 if (c.getDestino().getNombre().equals(sgtePosada.getNombre())) return c;
		return null;
	}
}

