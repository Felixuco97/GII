package modelo.usuarios;

import java.io.Serializable;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public abstract class UsuarioRegistrado  implements Serializable{
    
    private String nombre;
    private String nif;
    protected String contrasenia;
    protected UsuarioRegistrado(){}
    public UsuarioRegistrado(String nombre, String nif, String contrasenia){

        this.nombre = nombre;
        this.nif = nif;
        this.contrasenia = contrasenia;
    }

    //Getters

    public String getNombre(){

        return this.nombre;
    }

    public String getNIF(){

        return this.nif;
    }

    public String getcontrasenia(){

        return this.contrasenia;
    }
    
    public abstract int getId();

    //Setters

    public void setNombre(String nombre){

        this.nombre = nombre;
    }

    public void setNIF(String nif){

        this.nif = nif;
    }
}
