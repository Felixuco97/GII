/**
 * Clase de prueba para la clase PlanificadorMenu.
 * 
 * @author Felix felix.lopezl@estudiante.uam.es
 * @author Alex alex.pinneiro@estudiante.uam.es
 * 
 */
package pruebas.prueba1;
import java.util.*;
import objetos.*;
import objetos.enums.*;

public class PlanificadorTester1 extends PlatosTester1{
    public static void main (String[] args){

        PlanificadorTester1 tester = new PlanificadorTester1();
        List<Plato> platos = new ArrayList<>(tester.crearPlatos().values());
        PlanificadorMenu planificador = new PlanificadorMenu(platos)
                    .conMaximo(ElementoNutricional.GRASA_SATURADA, 1500.0)
                    .conMaximo(ElementoNutricional.AZUCARES, 5000.0)
                    .sinAlergenos(Alergeno.GLUTEN, Alergeno.HUEVO);
        //búsqueda 1
        Menu menu = planificador.planificar(2000, 30000);
        System.out.println("* " + menu);
        //búsqueda 2
        planificador = new PlanificadorMenu(platos)
                    .conMaximo(ElementoNutricional.GRASA_SATURADA, 2000.0)
                    .conMaximo(ElementoNutricional.AZUCARES, 1005.0)
                    .sinAlergenos(Alergeno.FRUTOS_SECOS);
        menu = planificador.planificar(2000, 30000);
        System.out.println("* " + menu);
    }
}
