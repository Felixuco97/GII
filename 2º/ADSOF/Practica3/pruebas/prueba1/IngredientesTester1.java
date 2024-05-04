/**
 * Clase de prueba para la clase Ingrediente.
 * 
 * @author Felix felix.lopezl@estudiante.uam.es
 * @author Alex alex.pinneiro@estudiante.uam.es
 * 
 */
package pruebas.prueba1;

import java.util.*;
import objetos.infoNutricional.*;
import objetos.Ingrediente;
import objetos.enums.*;

public class IngredientesTester1 {
    
    public static void main (String args[]){

        IngredientesTester1 tester = new IngredientesTester1();
        for (Ingrediente ingrediente : tester.crearIngredientes().values())
            System.out.println("* "+ingrediente);
    }
    
    public Map<String, Ingrediente> crearIngredientes(){

        Map<String, Ingrediente> ingredientes = new LinkedHashMap<>();

        ingredientes.put("Arroz", new Ingrediente("Arroz", TipoIngrediente.LEGUMBRE, new InfoNutricionalPeso(754, 293.65, 56.78, 333.277, 201.34, 32.09, 12.3, 2025.79))
                        .tieneAlergenos(Alergeno.GLUTEN, Alergeno.HUEVO, Alergeno.FRUTOS_SECOS, Alergeno.HUEVO));

        ingredientes.put("Maiz", new Ingrediente("Maiz", "Vitamina", new InfoNutricionalUnidad(1003.569, 0, 0, 546.2, 60.6, 88.6, 0, 0))
                        .tieneAlergenos(Alergeno.HUEVO, Alergeno.GLUTEN));

        ingredientes.put("Queso", new Ingrediente("Queso", TipoIngrediente.LACTEO, new InfoNutricionalPeso(23, 500, 178.3, 0, 136.691, 0, 202, 8000))
                        .tieneAlergenos(Alergeno.LACTOSA, Alergeno.HUEVO));

        ingredientes.put("Lechuga", new Ingrediente("Lechuga", TipoIngrediente.FRUTA_VERDURA, new InfoNutricionalPeso(3095, 178.6, 0.191, 0, 0, 0, 555.55, 1000)));

        ingredientes.put("Limon", new Ingrediente("Limon", "Acido", new InfoNutricionalPeso(2675.003, 188, 144, 36.478, 171, 179, 0, 230.875)));

        ingredientes.put("Caldo", new Ingrediente("Caldo", "Caldo", new InfoNutricionalUnidad(267, 18, 14, 3.4, 17, 17, 0, 23.875)));

        return ingredientes;
    }
}