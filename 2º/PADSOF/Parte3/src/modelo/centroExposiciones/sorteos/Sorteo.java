package modelo.centroExposiciones.sorteos;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import modelo.centroExposiciones.exposiciones.Exposicion;
import modelo.usuarios.*;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public abstract class Sorteo implements Serializable{
  private int numeroEntradas;
  private int numGanadores;
  private LocalDateTime inicioInscripcion;
  private LocalDateTime finInscripcion;
  private int penalizacion = 3;
  private ArrayList<Cliente> ganadores = new ArrayList<>();
  private ArrayList<Participacion> participaciones = new ArrayList<>();
  private ArrayList<Integer> codigosValidos = new ArrayList<>();
  private Exposicion expo;

  // private ArrayList<Entrada> disponibles = new ArrayList<>();
  protected Sorteo(){}
  public Sorteo(int numeroEntradas, int numGanadores, LocalDateTime inicioInscripcion, LocalDateTime finInscripcion, int penalizacion, Exposicion expo){

    this.numeroEntradas = numeroEntradas;
    this.numGanadores = numGanadores;
    this.inicioInscripcion = inicioInscripcion;
    this.finInscripcion = finInscripcion;
    this.penalizacion = penalizacion;
    this.expo = expo;
  }
  /**
   * Determina si un cliente puede canjear un código en una fecha y hora específicas.
   * 
   * @param c El cliente que intenta canjear el código.
   * @param fechaHora La fecha y hora en la que se intenta canjear el código.
   * @return true si el cliente puede canjear el código en la fecha y hora especificadas, false en caso contrario.
   */
  public abstract boolean canjearCodigo(Cliente c, LocalDateTime fechaHora);
  /**
   * Reserva un número específico de entradas para un sorteo.
   * 
   * @param numEntradasReservar El número de entradas que se desea reservar para el sorteo.
   * @throws Exception si ocurre un error durante la reserva de entradas para el sorteo.
   */
  public abstract void reservarEntradasSorteo(int numEntradasReservar) throws Exception;

  /**
   * Este método devuelve las entradas sobrantes después de un sorteo.
   *
   * @param entradasReservadas Una lista de las entradas que se reservaron para el sorteo.
   **/
  public abstract void devolverEntradasSobrantes ();


  /**
   * Este método permite a un cliente participar en un sorteo.
   * @param c El cliente que quiere participar en el sorteo.
   * @param nEntradas El número de entradas con las que el cliente quiere participar.
   **/
  public abstract void apuntarseSorteo(Cliente c, int nEntradas);

  /**
   * Este método realiza el sorteo entre las participaciones.
   * @param participaciones La lista de participaciones en el sorteo.
   * @param numGanadores El número de ganadores que se quieren obtener del sorteo.
   * @param clientes La lista de clientes que participan en el sorteo.
   **/
  public void realizarSorteo(ArrayList<Participacion> participaciones, int numGanadores, ArrayList<Cliente> clientes){

    /*realizar sorteo usando una funcion random para elegir participante (de la lista) ganador  */
    /*En la siguiente ronda se excluira el participante (en la lista de participantes) ganador de la ronda anterior*/

    if (participaciones == null){
      return ;
    }
    for (int i= 0; i<numGanadores; i++){
      
      Collections.shuffle(participaciones);

      int indiceGanador = obtenerGanador(participaciones);  //obtengo un ganador para esta ronda
      Cliente ganador = participaciones.get(indiceGanador).getCliente(); //obtengo a que cliente corresponde ese indiice

      /*Generar un código aleatorio para el ganador y asignarlo*/
      int codigoGanador = generarCodigo();
      this.codigosValidos.add(codigoGanador);
      ganador.setCodigo(codigoGanador);

      ganadores.add(ganador); //agrego al ganador a los ganadores
      participaciones.remove(indiceGanador); //elimino  al ganador de las participaciones restantes

    }

    /*Notificar a todos los ganadores */
    String mensaje = "Enhorabuena, has ganado el sorteo";
    Notificacion notificacion= new Notificacion(TiposNotificacion.GANADOR_SORTEO, mensaje, LocalDate.now(), "GANADOR SORTEO");

    for(Cliente ganador : ganadores){
      ganador.actualizarBandejaNotificaciones((notificacion));
    }

    for (Participacion p : participaciones){
      Cliente c  = p.getCliente();
      if (!ganadores.contains(p.getCliente())){
        c.removeParticipacion(p);
      }
    }
    /*cuando ya se ha hecho el sorteo podemos incrementar en cada cliente penalizado su contador de sorteos penalizados y así saber cuando acaba la restricción */

    for (Cliente c : clientes){
      if (c.estaPenalizado()){
        c.incrementarSorteosDesdePenalizacion();
      }
    }
  }
 
  /**
   * Este método obtiene un ganador aleatorio de la lista de participaciones.
   * @param participaciones La lista de participaciones en el sorteo.
   * @return El índice del ganador en la lista de participaciones.
   **/
  public int obtenerGanador(ArrayList<Participacion> participaciones){
    /*De la lista "participaciones" cojo un ganador random*/

    Random rand = new Random();
    int ganador = rand.nextInt(participaciones.size()); //obtengo un ganador aleatorio de la lista (su indice)

    return ganador;
  }


  /**
   * Este método obtiene el recuento de entradas de los ganadores.
   * @param winners La lista de ganadores del sorteo.
   * @return El recuento total de entradas de los ganadores.
   **/
  public int obtenerRecuentoEntradasGanadores(ArrayList<Cliente> winners){
    int recuentoEntradas= 0;
    for (Cliente ganador : winners){
        ArrayList<Participacion> participacionesGanador = ganador.getParticipaciones();
        for (Participacion participacion : participacionesGanador){
            int numEntradasGanador = participacion.getNumEntradas(); //hago recuento del numero de entradas que "se supone" que iba a usar el participante en caso de ganar
            recuentoEntradas += numEntradasGanador;
        }
    }
    return recuentoEntradas; //lo usaremos para comparar las entradas reservadas y las usadas y así devolver al total de entradas disponibles las no usadas
  }

  

  /**
   * Este método configura la penalización para los ganadores que no han usado su código dentro del período límite.
   * @param winners La lista de ganadores del sorteo.
   **/
  public void configuracionPenalizacion(ArrayList<Cliente> winners){
    /*Especificar periodo limite (15 dias) para que los ganadores del sorteo puedan usar el codigo*/ 
    LocalDateTime fechaLimiteCanjear = finInscripcion.plusDays(15);

    for(Cliente c: winners){
      if((c.haUsadoCodigo() == false) && (LocalDateTime.now().isEqual(fechaLimiteCanjear) || LocalDateTime.now().isAfter(fechaLimiteCanjear))){
        /*se penaliza impidiendole participar en los proximos 3 sorteos */  
        c.penalizar(penalizacion);
      }
    }
  }


  /**
   * Este método verifica si un cliente puede participar en un sorteo, teniendo en cuenta la posible penalización por no haber usado el código.
   * @param cliente El cliente que quiere participar en el sorteo.
   * @return Verdadero si el cliente puede participar, falso en caso contrario.
   **/
  public boolean puedoParticipar(Cliente cliente) {
    if (cliente.estaPenalizado()) {
        return false;
    } else {
        return true;
    }
  }

  /**
   * Este método genera un número aleatorio de 7 dígitos.
   * @return El código aleatorio generado.
  **/
  public int generarCodigo (){
    Random random = new Random();

    //rango para el código
    int min = 1000000;
    int max = 9999999;

    int codigoAleatorio = random.nextInt(max - min + 1) + min;
    return codigoAleatorio;
  }

  public void addParticipacion (Participacion p){
    this.participaciones.add(p);
  }
 
  //Getters
  public Exposicion getExposicion(){
    return this.expo;
  }

  public int getPenalizacion(){

    return this.penalizacion;
  }
  
  public ArrayList<Integer> getCodigosValidos(){
      return this.codigosValidos;
  }

  public int getNumeroEntradas(){

    return this.numeroEntradas;
  }
  
  public int getnumGanadores(){

    return this.numGanadores;
  }

  public LocalDateTime getInicioInscripcion(){

    return this.inicioInscripcion;
  }

  public LocalDateTime getFinInscripcion(){

    return this.finInscripcion;
  }

  public ArrayList<Participacion> getParticipaciones(){

    return this.participaciones;
  }

  public ArrayList<Cliente> getGanadores(){
    return this.ganadores;
  }
  
  //Setters
  public void setExposicion(Exposicion  expo){
    this.expo = expo;
  }

  public void setPenalizacion(int  penalizacion){

    this.penalizacion = penalizacion;
  }

  public void setNumeroEntradas(int  numEntradasSorteo){

    this.numeroEntradas = numEntradasSorteo;
  }

  public void setInicioInscripcion(LocalDateTime inicioInscr){

    this.inicioInscripcion = inicioInscr;
  }

  public void setFinInscripcion(LocalDateTime finInscr){

    this.finInscripcion = finInscr;
  }
}
