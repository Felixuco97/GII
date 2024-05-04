package modelo.centroExposiciones.obras.obrasFisicas;

import java.io.Serializable;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class Rango implements Serializable{
    private double inicio, fin;
   protected Rango(){}
    public Rango(double inicio,double fin){
        this.inicio = inicio;
        this.fin = fin;
    }

    //Getters

    public double getInicio(){

        return this.inicio;
    }

    public double getFin(){

        return this.fin;
    }

    //Setters

    public void setInicio(double i){

        this.inicio = i;
    }

    public void setFin(double f){

        this.fin = f;
    }
}
