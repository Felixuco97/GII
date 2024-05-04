package modelo.centroExposiciones.exposiciones;
import java.time.*;
import java.time.format.DateTimeFormatter;


/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class Permanente extends Exposicion {
    protected Permanente(){}
    public Permanente(String nombre, String descripcion, LocalDate fechaInicio, int numEntradas, double precioEntradas, Descuento desc, EstadoExpo estado, Estadisticas est){
        super(nombre, descripcion, fechaInicio, numEntradas, precioEntradas, desc, estado, est);
    }
    /**
     * Elimina exposiciones.
     * 
     * @return false.
     */
    public boolean eliminarExposiciones() {    
        return false;
    }

    @Override
    public boolean comparar(String filtro, String filtrado){
        if (filtro.equalsIgnoreCase("Exposicion") && filtrado.equalsIgnoreCase("Permanente")) {
            return true;
        }
        return false;  
    }

    /**
    * Este método cierra temporalmente la exposición.
    **/
    public void cerrarTemporalmente(){

        setEstado(EstadoExpo.CIERRE_TEMPORAL);
    }


    /**
    * Este método reabre la exposición.
    **/
    public void reabrir(){

        setEstado(EstadoExpo.PROGRAMADA);
    }

    public Permanente getPermanentes(){
        return this;
    }
    public Temporal getTemporales(){
        return null;
    }

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaInicio= this.getFechaInicio().format(formatter);
        return "---------PERMANENTE--------\n" + " Nombre: " + this.getNombre() + "\n Descripcion: " + this.getDescripcion() + "\n Fecha Inicio: " + fechaInicio  + "\n Precio Entrada: " + this.getPrecioEntradas() + "€\n" + "---------------------------";
    }
}
