package modelo.centroExposiciones.exposiciones;
import java.io.Serializable;
import java.time.*;
import java.util.*;

import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;
import modelo.centroExposiciones.*;
import modelo.centroExposiciones.obras.*;
import modelo.centroExposiciones.salas.Exposala;
import modelo.usuarios.*;
import modelo.utils.IBusqueda;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public abstract class Exposicion implements Serializable, IBusqueda{

    private String nombre, descripcion;
    private LocalDate fechaInicio;
    private int numEntradas;
    private double precioEntradas;
    private EstadoExpo estado;
    private Descuento desc;
    private ArrayList<Obra> obras = new ArrayList<>();
    private ArrayList<Entrada> entradas = new ArrayList<>();
    private ArrayList<Entrada> entradasReservadas= new ArrayList<>();
    private ArrayList<Exposala> salas = new ArrayList<>();
    
    private Estadisticas estadisticas;
    protected Exposicion(){}
    public Exposicion(String nombre, String descripcion, LocalDate fechaInicio, int numEntradas, double precioEntradas, Descuento desc, EstadoExpo estado, Estadisticas est){
        
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.numEntradas = numEntradas;
        this.precioEntradas = precioEntradas;
        this.desc = desc;
        this.estado = estado;
        if (est == null){
            this.estadisticas = new Estadisticas(nombre);
        }
        else{
            this.estadisticas = est;
        }
    }


    /**
     * Este método cancela la exposición y realiza las devoluciones correspondientes.
     * @param cardnum El número de tarjeta para realizar las devoluciones.
     **/
    public void cancelar(String cardnum){

        setEstado(EstadoExpo.CANCELADA);
        ArrayList<Entrada> canceladas = new ArrayList<>();
        
        for(Entrada e : this.entradas){
            Cliente cliente = e.getCliente();

            if (e.getFechaHora().isAfter(LocalDateTime.now())){
            try {
                TeleChargeAndPaySystem.charge(cardnum,"Devolucion a " + this.nombre, e.getPrice(), true);
            }catch (FailedInternetConnectionException ex){
                ex.printStackTrace();
            }
            catch (InvalidCardNumberException ex){
                ex.printStackTrace();
            }
            catch (OrderRejectedException ex){
                ex.printStackTrace();
            }

            cliente.removeEntrada(e); //eliiminar entrada de Cliente

            

            /*actualizar estadisticas */
            this.estadisticas.setNumEntradasVendidas(e.getDiaSemana(), this.estadisticas.getNumEntradasVendidas_dia(e.getDiaSemana()) - 1);
            this.estadisticas.setBeneficios(e.getDiaSemana(), this.estadisticas.getBeneficios_dia(e.getDiaSemana()) - e.getPrice());
            canceladas.add(e);
            }
        
        }
        entradas.removeAll(canceladas);
    }


    /**
     * Este método publica la exposición.
     **/
    public void publicar (){
        this.estado = EstadoExpo.EN_CURSO;
    }

    /**
     * Este método prorroga la fecha de inicio de la exposición.
     * @param dias El número de días para prorrogar la fecha de inicio.
     **/
    public void prorrogar(int dias){

        setFechaInicio(getFechaInicio().plusDays(dias));
    }


    /**
     * Este método configura el descuento para la exposición.
     * @param porcentaje El porcentaje de descuento a configurar.
     **/
    public void configurarDescuento(double porcentaje){

        this.desc.setPorcentaje(porcentaje);
    }

    /**
     * Este método verifica si un cliente es válido para un descuento.
     * @param c El cliente a verificar.
     * @param ent La entrada del cliente.
     * @return Verdadero si el cliente es válido para el descuento, falso en caso contrario.
     **/
    public boolean validoParaDescuento(Cliente c, Entrada ent){
        return this.desc.validoParaDescuento(c, ent);
    }

    /**
     * Este método añade obras a la exposición.
     * @param array El array de obras a añadir.
     * @return Verdadero si las obras se añadieron correctamente, falso en caso contrario.
     **/
    public boolean aniadirObras(Obra... array){

        if(this.estado == EstadoExpo.EN_CURSO){ 

            return false;
        }

        for(Obra o: array){
            o.moverToExpo();
            this.obras.add(o);
        }

        return true;
    }

    /**
     * Este método añade salas a la exposición.
     * @param array El array de salas a añadir.
     **/
    public void aniadirSalas(Exposala... array){
        for(Exposala sl: array){
            ArrayList<Obra> obrasSala = sl.getObras();
            for(Obra obra: obrasSala){
                if(!this.obras.contains(obra)){
                    this.obras.add(obra);
                }
            }
            this.salas.add(sl);
        }
    }

    /**
     * Este método calcula la disponibilidad de entradas para una fecha específica.
     * @param fecha La fecha para la que se quiere calcular la disponibilidad de entradas.
     * @return El número de entradas no disponibles para la fecha especificada.
     **/
    public int calcularDisponibilidadEntradas (LocalDateTime fecha){
        int entradasNoDisponibles = 0;
        for(Entrada e: this.entradas){
            if(e.getFechaHora().getHour() == fecha.getHour() && e.getFechaHora().toLocalDate().equals(fecha.toLocalDate()))
                entradasNoDisponibles++;
            if (e.getFechaHora().isBefore(LocalDateTime.now().plusDays(-1)))
                entradas.remove(e);
        }
        
        for(Entrada e: this.entradasReservadas){
            if(e.getFechaHora().getHour() == fecha.getHour() && e.getFechaHora().toLocalDate().equals(fecha.toLocalDate()))
                entradasNoDisponibles++;
        }
        return entradasNoDisponibles;
    }
    /**
     * Elimina una entrada reservada de la lista de entradas reservadas.
     * 
     * @param ent La entrada reservada a eliminar.
     */
    public void removeEntradaReservada(Entrada ent){
       this.entradasReservadas.remove(ent);
    }

    //Getters

    public abstract Temporal getTemporales();

    public abstract Permanente getPermanentes();

    public String getNombre(){

        return this.nombre;
    }

    public String getDescripcion(){

        return this.descripcion;
    }

    public LocalDate getFechaInicio(){

        return this.fechaInicio;
    }

    public int getNumEntradas(){

        return this.numEntradas;
    }

    public double getPrecioEntradas(){

        return this.precioEntradas;
    }

    public Descuento getDescuento(){

        return this.desc;
    }

    public EstadoExpo getEstado(){

        return this.estado;
    }

    public ArrayList<Obra> getObras(){

        return this.obras;
    }

    public ArrayList<Exposala> getSalas(){
        return this.salas;
    }

    public ArrayList<Exposala> getAllSalas(){
        ArrayList<Exposala> allSalas = new ArrayList<>();
        allSalas.addAll(salas);
        for (Exposala sl : salas){
            allSalas.addAll(sl.getSubSalas());
        }
        return this.salas;
    }

    public ArrayList<Entrada> getEntradasExpo(){

        return this.entradas;
    }

    public ArrayList<Entrada> getEntradasReservadas(){

        return this.entradasReservadas;
    }

    public Estadisticas getEstadisticas(){

        return this.estadisticas;
    }

    //Setters
    public void setNombre(String nombre){

        this.nombre = nombre;
    }

    public void setDescripcion(String desc){

        this.descripcion = desc;
    }

    public void setFechaInicio(LocalDate fInicio){

        this.fechaInicio = fInicio;
    }

    public void setNumeroEntradas(int n){

        this.numEntradas = n;
    }

    public void setPrecioEntradas(double pEntradas){

        this.precioEntradas = pEntradas;
    }

    public void setDescuento(Descuento des){

        this.desc = des;
    }

    public void setEstado(EstadoExpo est){

        this.estado = est;
    }

    public void setObras(ArrayList<Obra> o){

        this.obras = o;
    }
}
