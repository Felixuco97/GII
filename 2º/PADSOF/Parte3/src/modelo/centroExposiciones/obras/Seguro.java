package modelo.centroExposiciones.obras;

import java.io.Serializable;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class Seguro implements Serializable{
    private int numero;
    private double precio;
    protected Seguro() {}
    public Seguro(int numero, double precio){

        this.numero = numero;
        this.precio = precio;
    }

    //Getters

    public int getNumero(){

        return this.numero;
    }

    public double getPrecio(){

        return this.precio;
    }

    //Setters

    public void setNumero(int num){

        this.numero = num;
    }

    public void setPrecio(double prec){

        this.precio = prec;
    }
}