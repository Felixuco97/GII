package modelo.centroExposiciones.exposiciones;

import java.io.Serializable;

import modelo.centroExposiciones.Entrada;
import modelo.usuarios.Cliente;

/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public abstract class Descuento implements Serializable {
  
    private double porcentaje;
    protected Descuento(){}
    public Descuento(double porcentaje){
        this.porcentaje= porcentaje;
    }

    public abstract boolean validoParaDescuento(Cliente c, Entrada ent);

    //Setters

    public void setPorcentaje (double porcentaje){
      this.porcentaje = porcentaje;
    }

    //Getters

    public double getPorcentaje(){
      return this.porcentaje;
    }
}