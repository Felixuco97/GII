package explorador.test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import explorador.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimulacionTester {
	
	//private Camino c;
	private Explorador e;
	//private Posada p;

	@ParameterizedTest(name = "{index} => {0} {1} {2}")
	@CsvFileSource(resources = "../CAMINOS.csv", numLinesToSkip = 0, delimiter = ';')
	public void testCamino(String origen, String destino, int distancia) {
		Posada o = new Posada(origen);
		Posada d = new Posada(destino);
		Camino c = new Camino(o, d, distancia);
		assertEquals(c.toString(), "(" + origen + "--" + distancia + "-->" + destino + ")");
	}
	
	@ParameterizedTest(name = "{index} => {0} {1}")
	@CsvFileSource(resources = "../POSADAS.csv", numLinesToSkip = 0, delimiter = ';')
	public void testPosada(String nombre, int energia) {
		
		Posada p = new Posada(nombre, energia);
		Posada d = new Posada("G", 8);
		Camino c = new Camino(p, d, 5);
		p.addCamino(c);

		assertEquals(p.toString(), nombre + "(c:" + energia + ")" +  "[" + p.getCamino(d).toString() + "]" + (p.getLuz() == Luz.BLANCA ? "" : ". Luz: " + p.getLuz()));
	}
	
	@ParameterizedTest(name = "{index} => {0} {1} {2}")
	@CsvFileSource(resources = "../EXPLORADOR1.csv", numLinesToSkip = 0, delimiter = ';')
	public void testExplorador(String nombre, int energia, String origen) {
		Posada o = new Posada(origen);
		this.e = new Explorador(nombre, energia, o);
		assertEquals(e.toString(), "Maria(e:10) en A");
	}
	
	@ParameterizedTest(name = "{index} => {0} ")
	@CsvFileSource(resources = "../EXPLORADOR.csv", numLinesToSkip = 1, delimiter = ';')
	public void testExplorador2(String nombre) throws IOException {
		BufferedReader entradaExplorador =
				new BufferedReader (new InputStreamReader (new FileInputStream ("src/explorador/EXPLORADOR.csv" )));
		String linea;
		linea = entradaExplorador.readLine();
        String[] campos = linea.split(";");
        Posada o = new Posada(campos[2]);
        e = new Explorador(campos[0],  Integer.parseInt(campos[1]), o);
		Posada d = new Posada(nombre);
        entradaExplorador.close();
        assertTrue( e.puedeAlojarseEn(d));
		
	}
	
}
