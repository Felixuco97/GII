package modelo.usuarios;

import java.io.Serializable;

import modelo.centroExposiciones.sorteos.*;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class Participacion implements Serializable{

    private int numEntradas;
    private Sorteo sorteo;
    private Cliente cliente; //Si se mira bien el diagrama, es 1, no varios
    protected Participacion(){}
    public Participacion(int numEntradas, Cliente cliente, Sorteo sor){

        this.numEntradas = numEntradas;
        this.cliente = cliente;
        this.sorteo = sor;
    }

    //Getters

    public int getNumEntradas(){

        return this.numEntradas;
    }

    public Sorteo getSorteo(){

        return this.sorteo;
    }

    public Cliente getCliente(){

        return this.cliente;
    }

    //Setters

    public void setNumEntradas(int nEntradas){

        this.numEntradas = nEntradas;
    }

    public void setSorteo(Sorteo s){

        this.sorteo = s;
    }
}