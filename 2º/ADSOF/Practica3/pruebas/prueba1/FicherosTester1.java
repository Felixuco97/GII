/**
 * Clase de prueba para la clase ManejadorFicheros.
 * 
 * @author Felix felix.lopezl@estudiante.uam.es
 * @author Alex alex.pinneiro@estudiante.uam.es
 * 
 */
package pruebas.prueba1;

import fichero.ManejadorFicheros;
import java.util.*;
import objetos.*;

public class FicherosTester1 extends MenusTester1{
    
    public static void main(String[] args){

        FicherosTester1 tester = new FicherosTester1();
        List<Menu> menus = tester.crearMenus();
        // guardar lista de menús a fichero
        ManejadorFicheros.guardarFichero("comida1.txt", menus);
        // leer lista de menús de fichero, e imprimirlos por pantalla 
        ManejadorFicheros.leerFichero("comida1.txt");
        
        for(Menu menu: ManejadorFicheros.getMenus())
            System.out.println("* " + menu);
    }
}