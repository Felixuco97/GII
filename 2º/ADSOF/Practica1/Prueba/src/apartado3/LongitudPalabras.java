package apartado3;

import java.util.*;

/**
* 
* Esta clase calcula la longitud de las palabras y las almacena en un mapa.
* 
* @author Félix felix.lopezl@estudiante.uam.es
* @author Álex alex.pinneiro@estudiante.uam.es
* 
*/

public class LongitudPalabras {
	
	private Map<String, Integer> palabras = new LinkedHashMap<>();
	
	/**
	 * Variables adicionales del apartado 4
	 */
	
	private ArrayList<Integer> longitudes = new ArrayList<>(); 
	private HashSet<Integer> longitudesUnicas = new HashSet<>();
	private ArrayList<Integer> longitudesRepetidas = new ArrayList<>();
	private HashMap<Integer, Integer> dict = new HashMap<>();
	
	/**
	* Constructor con las palabras.
	* @param palabras Palabras de las que se quiere calcular las longitudes.
	*/
	public LongitudPalabras(String[] palabras) {
		for(String palabra: palabras) {
			this.palabras.put(palabra, palabra.length());
			this.longitudes.add(palabra.length());
			this.longitudesUnicas.add(palabra.length());
		}
		
		repetidas();

		diccionario();
	}
	/**
	* @return conjunto de palabras incluidas en el objeto.
	*/
	public Set<String> getPalabras() {
		return this.palabras.keySet();
	}
	/**
	* @return conjunto de longitudes de las palabras.
	*/
	public Collection<Integer> getLongitudes() {
		return this.palabras.values();
	}
	/**
	* Devuelve la longitud de una palabra, o -1 si no existe
	* @param palabra
	* @return su longitud
	*/
	public int longitud(String palabra) {
		if (!this.palabras.containsKey(palabra)) return -1;
		return this.palabras.get(palabra);
	}
	/**
	* @return cadena de texto que representa este objeto.
	*/
	@Override
	public String toString() {
		String str = "Las longitudes de las palabras son: \n";
		for (String palabra : this.palabras.keySet())
			str += "- " + palabra + " (" + this.palabras.get(palabra) + " caracteres).\n";
		return str;
	}
	/**
	 * Cógido adicional del apartado 4: obtiene las longitudes únicas
	 */
	public void repetidas() {
		for(int i=0;i<this.longitudes.size();i++) {
			for(int j=0;j<i;j++) {
				if(this.longitudes.get(i).equals(this.longitudes.get(j)))
					this.longitudesRepetidas.add(this.longitudes.get(i));
			}
		}
	}
	/**
	 * Almacena las longitudes junto con sus frecuencias en un diccionario
	 */
	public void diccionario() {
		for(int lon: this.palabras.values()) {
			if(!dict.containsKey(lon)) {
				dict.put(lon, 1);
			} else {
				dict.put(lon, dict.get(lon)+1);
			}
		}
	}
	/**
	 * Obtiene las longitudes únicas
	 *
	 * @return set de longitudes únicas
	 */
	
	public HashSet<Integer> getLongitudesUnicas() {
		
		return this.longitudesUnicas;
	}
	/**
	 * Obtiene las longitudes repetidas
	 * 
	 * @return lista de longitudes repetidas
	 */
	public ArrayList<Integer> getLongitudesRepetidas(){
		
		return this.longitudesRepetidas;
	}
	/**
	 * Obtiene las frecuencias
	 * 
	 * @param lon
	 * @return frecuencia de la longitud
	 */
	public int getFrecuencia(int lon) {
		
		return dict.get(lon);
	}
	
	/**
	* Punto de entrada a la aplicación.
	* Este método imprime las longitudes de palabras proporcionadas por la línea de comandos
	* @param args Los argumentos de la línea de comando. Se esperan palabras, como cadenas
	*/
	public static void main(String[] args) {
		if (args.length == 0)
			System.err.println("Se espera al menos una palabra como parametro.");
		else {
			LongitudPalabras palabras = new LongitudPalabras(args);
			System.out.println(palabras);
			System.out.println("Palabras almacenadas: " + palabras.getPalabras());
			System.out.println("Longitud de 'No_almacenada': " + palabras.longitud("No_almacenada"));
		}
	}
}