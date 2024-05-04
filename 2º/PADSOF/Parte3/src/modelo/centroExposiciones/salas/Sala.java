package modelo.centroExposiciones.salas;
import java.io.Serializable;
import modelo.centroExposiciones.obras.obrasFisicas.*;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class Sala implements Serializable {
    private String nombre;
    private Dimension dimensiones;
    private int aforo;
    private int tomasCorriente;
    private double temperatura;
    private double humedad;

    protected Sala() {}
    public Sala(String nombre, Dimension dimensiones, int aforo, int tomasCorriente, double temperatura, double humedad){

        this.nombre = nombre;
        this.dimensiones = dimensiones;
        this.aforo = aforo;
        this.tomasCorriente = tomasCorriente;
        this.temperatura = temperatura;
        this.humedad = humedad;
    }

    //Getters

    public String getNombre(){

        return this.nombre;
    }

    public Dimension getDimension(){

        return this.dimensiones;
    }

    public int getAforo(){

        return this.aforo;
    }

    public int getTomasCorriente(){

        return this.tomasCorriente;
    }

    public double getTemperatura (){

        return this.temperatura;
    }

    public double getHumedad(){

        return this.humedad;
    }

    //Setters

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setDimension(Dimension dimensiones){
        this.dimensiones = dimensiones;
    }

    public void setAforo(int aforo){
        this.aforo = aforo;
    }

    public void setTomasCorriente(int tomasCorriente){
        this.tomasCorriente = tomasCorriente;
    }

    public boolean setTemperatura (double temperatura){
        this.temperatura = temperatura;
        return true;
    }

    public boolean setHumedad(double humedad){
        this.humedad = humedad;
        return true;
    }
}
