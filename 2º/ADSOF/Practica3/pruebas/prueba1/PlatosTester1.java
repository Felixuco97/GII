/**
 * Clase de prueba para la clase Plato.
 * 
 * @author Felix felix.lopezl@estudiante.uam.es
 * @author Alex alex.pinneiro@estudiante.uam.es
 * 
 */
package pruebas.prueba1;
import java.util.*;
import objetos.*;

public class PlatosTester1 extends IngredientesTester1{
    
    public static void main(String[] args){

        PlatosTester1 tester = new PlatosTester1();

        for (Plato plato: tester.crearPlatos().values())
            System.out.println("* "+plato);
    }

    public Map<String, Plato> crearPlatos(){

        Map<String, Ingrediente> ing = this.crearIngredientes();
        Plato p1, p2, p3;

        p1 = new Plato("Paella");

        if(p1.addIngrediente(ing.get("Arroz"), 80)) System.out.println("ingrediente repetido");
        if(p1.addIngrediente(ing.get("Arroz"), 80)) System.out.println("ingrediente repetido");


        p1.addIngrediente(ing.get("Maiz"), 10);
        p1.addIngrediente(ing.get("Queso"), 30);
        
        p2 = new Plato("Pizza");

        p2.addIngrediente(ing.get("Limon"), 80);
        p2.addIngrediente(ing.get("Lechuga"), 44);

        p3 = new Plato("Hamburguesa");

        p3.addPlato(p1);
        p3.addPlato(p2);
        p3.addIngrediente(ing.get("Caldo"), 5);

        return Map.of("Paella", p1, "Pizza", p2, "Hamburguesa", p3);
    }
}

