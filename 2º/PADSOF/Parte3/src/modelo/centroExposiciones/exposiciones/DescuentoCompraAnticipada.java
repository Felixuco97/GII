package modelo.centroExposiciones.exposiciones;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import modelo.centroExposiciones.Entrada;
import modelo.usuarios.Cliente;

/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class DescuentoCompraAnticipada extends Descuento {

    private int diasAntelacion;
    protected DescuentoCompraAnticipada(){}
    public DescuentoCompraAnticipada(double porcentaje, int diasAntelacion){
        super(porcentaje);
        this.diasAntelacion = diasAntelacion;
    }  
    /**
     * Verifica si un cliente es elegible para un descuento basado en la antelación de la compra de la entrada.
     * 
     * @param c El cliente para el que se verifica la elegibilidad.
     * @param ent La entrada que se está considerando para el descuento.
     * @return true si el cliente es elegible para un descuento, false en caso contrario.
     * @throws NullPointerException si c o ent es null.
     */
    public boolean validoParaDescuento(Cliente c, Entrada ent){
        if (c == null|| ent == null)
            return false;
        if (ChronoUnit.DAYS.between(LocalDate.now(), ent.getFechaHora().toLocalDate()) >= diasAntelacion){
                return true;
        }
        return false;
    }
    
    //Getters

    public int getDiasAntelacion(){

        return this.diasAntelacion;
    }

    //Setters

    public void setDiasAntelacion(int dias){

        this.diasAntelacion = dias;
    }
}