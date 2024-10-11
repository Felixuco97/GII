package explorador;

import java.io.*;
import java.util.*;


public class Simulacion {
	private ArrayList<Posada> posadas = new ArrayList<Posada>();
	private Explorador ex;
	private Posada getPosada(String n) {
		for (Posada p : posadas) { if (p.getNombre().equals(n)) return p; }
		return null;
	}

	public Simulacion(String archivoPosadas, String archivoCaminos, String archivoExplorador)
			throws IOException {
		System.out.println("ABRIENDO FICHEROS...");
		BufferedReader entradaPosadas =
			new BufferedReader (new InputStreamReader (new FileInputStream (archivoPosadas )));
		BufferedReader entradaCaminos =
			new BufferedReader (new InputStreamReader (new FileInputStream (archivoCaminos )));
		BufferedReader entradaExplorador =
			new BufferedReader (new InputStreamReader (new FileInputStream (archivoExplorador )));
        String linea;
        System.out.println("SI ESTA LÍNEA SE EJECUTA, LOS FICHEROS SE HAN ABIERTO CORRECTAMENTE");
        System.out.println("LEYENDO FICHEROS...");
        //POSADAS comprobado
        while ((linea = entradaPosadas.readLine()) != null) {
        	String[] campos = linea.split(";");
        	String nombre = campos[0];
        	int energia = Integer.parseInt(campos[1]);
        	Posada p = new Posada(nombre, energia);
        	posadas.add(p);
        }
        Posada p1 = posadas.get(0);
        System.out.println(p1.getNombre() + "; "+p1.getEnergia());
        Posada p2 = posadas.get(1);
        System.out.println(p2.getNombre() + "; "+p2.getEnergia());
        Posada p3 = posadas.get(2);
        System.out.println(p3.getNombre() + "; "+p3.getEnergia());
        Posada p4 = posadas.get(3);
        System.out.println(p4.getNombre() + "; "+p4.getEnergia());
        Posada p5 = posadas.get(4);
        System.out.println(p5.getNombre() + "; "+p5.getEnergia());
        Posada p6 = posadas.get(5);
        System.out.println(p6.getNombre() + "; "+p6.getEnergia());              
        //POSADAS comprobado
        //CAMINOS comprobado
        while ((linea = entradaCaminos.readLine()) != null) {
        	String[] campos = linea.split(";");
        	Posada p = getPosada(campos[0]);
        	// p.addCamino( getPosada(campos[1]), Integer.parseInt(campos[2] ));
        	p.addCamino( new Camino( p, getPosada(campos[1]), Integer.parseInt(campos[2] )));
        }
        System.out.println(p1.getCamino(p2));
        System.out.println(p1.getCamino(p3));
        System.out.println(p2.getCamino(p4));
        System.out.println(p2.getCamino(p5));
        System.out.println(p3.getCamino(p4));
        System.out.println(p3.getCamino(p5));
        System.out.println(p4.getCamino(p6));
        System.out.println(p5.getCamino(p6));
        //CAMINOS comprobado
        //EXPLORADOR comprobado
        linea = entradaExplorador.readLine();
        String[] campos = linea.split(";");
        String sgtePosada;
        ex = new Explorador(campos[0],  Integer.parseInt(campos[1]), getPosada(campos[2]));
        while ((sgtePosada = entradaExplorador.readLine()) != null) {
        	Camino sgteCamino = ex.getUbicacion().getCamino(getPosada(sgtePosada));
        	if (sgteCamino == null)  System.out.println("No hay camino a " + sgtePosada); 
        	else  { ex.recorre( sgteCamino );
        			System.out.println(ex);
        	}
        }
        System.out.println(ex.getNombre() + "; " + ex.getEnergia() + "; " + ex.getUbicacion());
        //EXPLORADOR comprobado
        System.out.println("FICHEROS LEÍDOS CORRECTAMENTE");
        entradaPosadas.close();
        entradaCaminos.close();
        entradaExplorador.close();
        System.out.println("FICHEROS CERRADOS");
	}
}

