package modelo.usuarios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import modelo.centroExposiciones.*;
import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;
import modelo.centroExposiciones.exposiciones.*;
import modelo.centroExposiciones.sorteos.*;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class Cliente extends UsuarioRegistrado{
    public static final int id = 0;
    private boolean preferenciaNotificaciones;
    private ArrayList<Notificacion> notificaciones = new ArrayList<>();
    private ArrayList<Entrada> entradas = new ArrayList<>();
    private ArrayList<Participacion> participaciones = new ArrayList<>();
    private boolean penalizado = false; /*Bandera para indicar si el cliente esta penalizado*/
    private int sorteosDesdePenalizacion; /*Contador de sorteos desde la penalización para luego saber cuando levantarla*/
    private int numSortPenalizados = 0;
    private boolean codigoUsado= false;
    private int codigo;

    

    protected Cliente(){
        super(null, null, null);
    }
    public Cliente(String nombre, String nif, String contrasenia, boolean preferenciaNotificaciones){

        super(nombre, nif, contrasenia);
        this.preferenciaNotificaciones = preferenciaNotificaciones;
    }

    /**
     * Añade una entrada de la lista de entradas.
     * @param e La entrada a añadir.
     **/
    public void addEntrada(Entrada e){

        this.entradas.add(e);
    }

    /**
     * Elimina una entrada de la lista de entradas.
     * @param e La entrada a eliminar.
     **/
    public void removeEntrada(Entrada e) {
        this.entradas.remove(e);
    }

    /**
     * Añade una participación a la lista de participaciones.
     * @param p La participación a añadir.
     * @return false si ya se tiene una participacion para ese sorteo y true si no
     **/
    public boolean addParticipaciones(Participacion p){
        for (Participacion pt : participaciones){
            if (p.getSorteo() == pt.getSorteo()){
               return false; 
            }
        }
        this.participaciones.add(p);
        return true;
    }

    public void removeParticipacion(Participacion p) {
        if (participaciones.contains(p)){
            participaciones.remove(p);
        }
    }

    /**
     * Permite las notificaciones.
     **/
    public void permitirNotificaciones(){

        setPreferenciaNotificaciones(true);
    }

    /**
    * Restringe las notificaciones.
    **/
    public void restringirNotificaciones(){

        setPreferenciaNotificaciones(false);
    } 


    /**
     * Actualiza la bandeja de notificaciones con las notificaciones proporcionadas.
     * Si las notificaciones están permitidas, se añaden todas las notificaciones.
     * Si las notificaciones están restringidas, solo se añaden las notificaciones de tipo GANADOR_SORTEO y CANCELACION_EXPOSICION.
     * @param nots Las notificaciones a añadir.
     **/
    public void actualizarBandejaNotificaciones(Notificacion... nots){

        
        if(this.preferenciaNotificaciones == true){
            
            for(Notificacion n: nots){

                this.notificaciones.add(n);
            }
        }
        else{
            for(Notificacion n: nots){
                if (n.getTiposNotificacion() == TiposNotificacion.GANADOR_SORTEO || n.getTiposNotificacion() == TiposNotificacion.CANCELACION_EXPOSICION)
                    this.notificaciones.add(n);
            }
        }
    }

    /**
     * Muestra las notificaciones y marca la fecha de lectura como la fecha actual.
     * @return Un array de strings con las notificaciones.
     **/
    public String[] mostrarNotificaciones(){
        String[] notiStr = new String[notificaciones.size()];
        int i = 0;
        for (Notificacion not: notificaciones){
            notiStr[i] = not.mostrar();
            not.setFechaLeida(LocalDate.now());
            i++;
        }
        return notiStr;
    }

    /**
     * Comprueba si el cliente ha usado el código del sorteo.
     * @return Verdadero si el cliente ha usado el código, falso en caso contrario.
     */
    public boolean haUsadoCodigo() {
        return this.codigoUsado;
    }

    /**
     * Marca el código como usado si el código ingresado coincide con el código del cliente.
     * @param codigoIngresado El código ingresado por el cliente.
     **/
    public void marcarCodigoUsado(int codigoIngresado) {
        if (codigoIngresado == codigo) {
            codigoUsado = true;
        }
    }

    /**
     * Comprueba si el cliente está penalizado.
     * @return Verdadero si el cliente está penalizado, falso en caso contrario.
     **/
    public boolean estaPenalizado() {
        return this.penalizado;
    }

    /**
     * Penaliza al cliente y reinicia el contador de sorteos desde la penalización.
     **/
    public void penalizar(int nPenalizacion) {
        this.penalizado = true;
        this.numSortPenalizados += nPenalizacion;  //establecer el numero de sorteos penalizados (a 3)
        sorteosDesdePenalizacion = 0; /*reiniciar el contador*/
    }

    /**
     * Incrementa el contador de sorteos desde la penalización.
     * Si el contador llega a 3, se levanta la penalización.
     **/
    public void incrementarSorteosDesdePenalizacion() {
        sorteosDesdePenalizacion++;
        if (sorteosDesdePenalizacion >= numSortPenalizados) {
            levantarPenalizacion();
        }
    }

    /**
     * Levanta la penalización al cliente y reinicia el contador de sorteos desde la penalización.
     */
    public void levantarPenalizacion() {
        this.penalizado = false;
        numSortPenalizados = 0;
        sorteosDesdePenalizacion = 0; /*reiniciar el contador*/
    }




    /**
     * Metodo que permite que el cliente compre entradas
     * 
     * @param exposicion La exposición para la cual se desean comprar las entradas
     * @param cardnum    El número de la tarjeta de crédito del cliente
     * @param array      Un array de objetos Entrada que se desean comprar
     * @throws Exception Se lanza una excepción si la exposición no está abierta en la fecha de la entrada o si no quedan entradas para esa hora
     **/
    public void comprarEntradas(Exposicion expo, String cardnum, String codigoSorteo, LocalDateTime fechaHora, Entrada... array) throws Exception{
        if (!codigoSorteo.equals("")){
            for (Participacion p : participaciones){
                Sorteo sorteo = p.getSorteo();
                if (p.getSorteo().getExposicion().equals(expo)){
                    if (sorteo.canjearCodigo(this, fechaHora))
                        return;
                }
            }
            throw new Exception("Codigo no válido");
        } //COMPROBAR
        int entradasNoDisponibles = expo.calcularDisponibilidadEntradas(array[0].getFechaHora());

        for(Entrada e: array){
            if(entradasNoDisponibles < expo.getNumEntradas() && !expo.getFechaInicio().isAfter(e.getFechaHora().toLocalDate())){
                String subject = "Entrada para exposicion: " + expo.getNombre();
                double precio= expo.getPrecioEntradas();
                
                if (expo.getDescuento() != null && expo.validoParaDescuento(this, e)){
                    e.setPrice(precio - precio *expo.getDescuento().getPorcentaje());
                }
                else{
                    e.setPrice(precio);
                }
                    
                //comprobamos que la entrada a comprar no esté ya en el array de entradas (compradas) de expo
                if(!expo.getEntradasExpo().contains(e)){
                    expo.setNumeroEntradas(expo.getNumEntradas() - 1);

                    try{
                        TeleChargeAndPaySystem.charge(cardnum, subject, -e.getPrice(), true);
                    }
                    catch (FailedInternetConnectionException ex){
                        ex.printStackTrace();
                    }
                    catch (InvalidCardNumberException ex){
                        ex.printStackTrace();
                    }
                    catch (OrderRejectedException ex){
                        ex.printStackTrace();
                    }
                    entradas.add(e);
                    expo.getEntradasExpo().add(e);
                    expo.getEstadisticas().setNumEntradasVendidas(e.getDiaSemana(), expo.getEstadisticas().getNumEntradasVendidas_dia(e.getDiaSemana()) + 1);
                    expo.getEstadisticas().setBeneficios(e.getDiaSemana(), expo.getEstadisticas().getBeneficios_dia(e.getDiaSemana()) +  e.getPrice());
                    entradasNoDisponibles++;
                    } else {
                        throw new Exception("La entrada ya está comprada para esta exposición");
                    }
                }
                else if(expo.getFechaInicio().isAfter(e.getFechaHora().toLocalDate())){
                    throw new Exception ("La exposicion no esta abierta para esa fecha");
                }
                else{
                throw new Exception("No quedan entradas para esa hora");
            }
        }
        if(!array[0].generarPdf(expo, array.length)) // todas las entradas que se compran a la vez tienen los mismos datos
            System.err.println("Entrada no generada");
    }

    

    //Getters

    public boolean getPreferenciaNotificaciones(){

        return this.preferenciaNotificaciones;
    }

    public ArrayList<Notificacion> getNotificaciones(){

        return this.notificaciones;
    }

    public ArrayList<Entrada> getEntradas(){

        return this.entradas;
    }

    public ArrayList<Participacion> getParticipaciones(){

        return this.participaciones;
    }

    public int getSorteosDesdePenalizacion(){
        return this.sorteosDesdePenalizacion;
    }

    public int getCodigo(){
        return codigo;
    }

    public int getNumSorteosPenalizado(){
        return this.numSortPenalizados;
    }

    @Override
    public int getId(){
        return id;
    }

    

    //Setters
    
    public void setNumSorteosPenalizado(int n){
        this.numSortPenalizados = n;
    }

    public void setPreferenciaNotificaciones(boolean preferenciaNotificaciones){

        this.preferenciaNotificaciones = preferenciaNotificaciones;
    }

    public void setNotificaciones(ArrayList<Notificacion> nots){

        this.notificaciones = nots;
    }

    public void setEntradas(ArrayList<Entrada> entrs){

        this.entradas = entrs;
    }

    public void setCodigo(int codigo){
        this.codigo = codigo;
    }

}
