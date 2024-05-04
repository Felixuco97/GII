package modelo.centroExposiciones.exposiciones;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import modelo.centroExposiciones.Entrada;
import modelo.usuarios.Cliente;

/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class DescuentoClienteFrecuente extends Descuento {

    private int visitas;
    private int ndias; //para ver la frecuencia en un mes por ejemplo (30 dias)
    protected DescuentoClienteFrecuente(){}
    public DescuentoClienteFrecuente(double porcentaje, int visitas, int ndias){
        super(porcentaje);
        this.visitas = visitas;
        this.ndias = ndias;
    }
    /**
     * Verifica si un cliente es elegible para un descuento basado en el número de visitas
     * que ha realizado y el tiempo transcurrido desde su última visita.
     * 
     * @param c El cliente para el que se verifica la elegibilidad.
     * @param ent La entrada que se está considerando para el descuento.
     * @return true si el cliente es elegible para un descuento, false en caso contrario.
     * @throws NullPointerException si c o ent es null.
     */
    public boolean validoParaDescuento(Cliente c, Entrada ent){
        int nvisitadas = 0;
        if (c == null || ent == null)
            return false;
        for(Entrada e : c.getEntradas()){
            if (ChronoUnit.DAYS.between(e.getFechaHora(), LocalDateTime.now()) <= ndias) {
                nvisitadas++;
            }
            if (nvisitadas >= visitas)
                return true;
        }
        if (nvisitadas >= visitas){
            return true;
        }
        return false;
    }

    //Getters

    public int getVisitas(){

        return this.visitas;
    }

    public int getNumeroDias(){

        return this.ndias;
    }

    //Setters

    public void setVisitas(int vis){

        this.visitas = vis;
    }

    public void setNumeroDias(int ndias){

        this.ndias = ndias;
    }
}