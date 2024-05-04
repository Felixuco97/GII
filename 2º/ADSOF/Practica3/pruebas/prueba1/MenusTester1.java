/**
 * Clase de prueba para la clase Menu.
 * 
 * @author Felix felix.lopezl@estudiante.uam.es
 * @author Alex alex.pinneiro@estudiante.uam.es
 * 
 */
package pruebas.prueba1;

import java.util.*;
import objetos.*;

public class MenusTester1 extends PlatosTester1{
    
    public static void main(String[] args){

        MenusTester1 tester = new MenusTester1();

        for(Menu menu: tester.crearMenus())
            System.out.println("* " + menu);
    }

    public List<Menu> crearMenus(){

        Map<String, Plato> platos = this.crearPlatos();

        Menu m1 = new Menu(platos.get("Paella"));
        Menu m2 = new Menu(platos.get("Pizza"), platos.get("Hamburguesa"));
        Menu m3 = new Menu(platos.get("Paella"), platos.get("Pizza"), platos.get("Hamburguesa"));

        return List.of(m1, m2, m3);
    }
}
