package modelo.centroExposiciones.exposiciones;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;


/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class Temporal extends Exposicion {

    private LocalDate fechaFin;
    protected Temporal(){}
    public Temporal(String nombre, String descripcion, LocalDate fechaInicio, int numEntradas, LocalDate fechaFin, double precioEntradas, Descuento desc, EstadoExpo estado, Estadisticas est){
        super(nombre, descripcion, fechaInicio, numEntradas, precioEntradas, desc, estado, est);
        
        this.fechaFin = fechaFin;
    }

    @Override
    public boolean comparar(String filtro, String filtrado){
        if (filtro.equalsIgnoreCase("Exposicion") && filtrado.equalsIgnoreCase("Temporal")) {
            return true;
        }
        return false;  
    }

    /**
    * Este método cierra la exposición.
    **/
    public void cerrar(){

        setEstado(EstadoExpo.EN_ALMACEN);
    }


    /**
     * Este método calcula la duración de la exposición en días.
     *
     * @return La duración de la exposición en días.
     **/
    public long calcularDuracion(){
        
        LocalDate init = this.getFechaInicio();
        LocalDate fin = this.getFechaFin();

        return ChronoUnit.DAYS.between(init, fin);
    }


    //Getters

    public LocalDate getFechaFin(){

        return this.fechaFin;
    }

    public Permanente getPermanentes(){
        return null;
    }

    public Temporal getTemporales(){
        return this;
    }

    //Setters

    public void setFechaFin(LocalDate fFin){

        this.fechaFin = fFin;
    }

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaInicio= this.getFechaInicio().format(formatter);
        String fechaFin = this.getFechaFin().format(formatter);
        return "---------TEMPORAL--------\n" + " Nombre: " + this.getNombre() + "\n Descripcion: " + this.getDescripcion() + "\n Fecha Inicio: " + fechaInicio + "\n Fecha Fin: " + fechaFin + "\n Precio Entrada: " + this.getPrecioEntradas() + "€\n" + "-------------------------";
    }
}
