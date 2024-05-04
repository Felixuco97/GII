package modelo.usuarios;

import modelo.centroExposiciones.sorteos.Sorteo;

/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */
public class Gestor extends UsuarioRegistrado{
    public static final int id = 2;
    private static Sorteo sorteos = null;
    protected Gestor(){
        super(null, null, null);
    }

    public Gestor(String nombre, String nif, String contrasenia){

        super(nombre, nif, contrasenia);
    }

    public static void setFlagSorteo(Sorteo s){
        sorteos = s;
    }

    public static Sorteo getFlagSorteo(){
        return sorteos;
    }


    @Override
    public  int getId(){
        return id;
    }
    
}
