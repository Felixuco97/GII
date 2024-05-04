package modelo.centroExposiciones.sorteos;

import java.time.LocalDateTime;
import java.util.ArrayList;

import modelo.centroExposiciones.Entrada;
import modelo.centroExposiciones.exposiciones.Exposicion;
import modelo.usuarios.Cliente;
import modelo.usuarios.Participacion;

public class SorteoFechaFija extends Sorteo {
    private LocalDateTime fechaFija;
    private ArrayList<Entrada> entradas = new ArrayList<>();


    protected SorteoFechaFija(){}
    public SorteoFechaFija(int numeroEntradas, int numGanadores, LocalDateTime inicioInscripcion, LocalDateTime finInscripcion, int penalizacion, Exposicion expo, LocalDateTime fechaFija){
        super( numeroEntradas, numGanadores, inicioInscripcion, finInscripcion, penalizacion, expo);
        this.fechaFija = fechaFija;
    }
    /**
     * Determina si un cliente puede canjear un código en una fecha y hora específicas.
     * 
     * @param c El cliente que intenta canjear el código.
     * @param fechaHora La fecha y hora en la que se intenta canjear el código.
     * @return true si el cliente puede canjear el código en la fecha y hora especificadas, false en caso contrario.
     */
    @Override
    public boolean canjearCodigo(Cliente c, LocalDateTime fechaHora){ //fechaHora == null
        int cod = c.getCodigo();
        boolean valido = false;
        Exposicion expo = this.getExposicion();
        ArrayList<Integer> codigosValidos = this.getCodigosValidos();
        for (int codigo: codigosValidos){
            if (codigo == cod){
                valido = true;
                break;
            }
        }

        if (!valido)
            return false;

        for (Participacion p : c.getParticipaciones()){
            if (p.getSorteo() == this){
                int n = p.getNumEntradas();
                if (n == 1){
                    Entrada ent = expo.getEntradasReservadas().get(0);
                    c.addEntrada(ent);
                    expo.removeEntradaReservada(ent);
                }
                if (n == 2){
                    Entrada ent = expo.getEntradasReservadas().get(0);
                    Entrada ent1 = expo.getEntradasReservadas().get(1);
                    c.addEntrada(ent);
                    c.addEntrada(ent1);
                    expo.removeEntradaReservada(ent);
                    expo.removeEntradaReservada(ent1);
                }
                c.marcarCodigoUsado(cod);
                c.removeParticipacion(p);
                break;
            }
        }
        return true;
    }
    /**
     * Reserva un número específico de entradas para un sorteo.
     * 
     * @param numEntradasReservar El número de entradas que se desea reservar para el sorteo.
     * @throws Exception si ocurre un error durante la reserva de entradas para el sorteo.
     */
    @Override
    public void reservarEntradasSorteo(int numEntradasReservar) throws Exception {
        /*especificar el numero de entradas que se reservaran para el sorteo --> se retirara esa cantidad del numero de entradas disponible por cada expo(hora)*/
        Exposicion expo = this.getExposicion();
        ArrayList<Entrada> entradas = this.getEntradas();
        if ((expo.calcularDisponibilidadEntradas(fechaFija) + numEntradasReservar) > expo.getNumEntradas())//COMPROBAR
            throw new Exception("No hay disponibilidad de entradas suficientes para ese dia");

        for(int i = 0; i < numEntradasReservar; i++){
            Entrada ent = null;
            ent = new Entrada("EventHub", expo.getNombre(), expo.getNumEntradas(), fechaFija, 0, 1, expo.getPrecioEntradas(), "", "", null);
            expo.getEntradasReservadas().add(ent); //COMPROBAR
            entradas.add(ent);
        }
        this.setEntradas(entradas);
        //this.sorteo.venderEntradas(entradasReservadas);
    }

    /**
     * Este método devuelve las entradas sobrantes después de un sorteo.
     *
     * @param entradasReservadas Una lista de las entradas que se reservaron para el sorteo.
     **/
    public void devolverEntradasSobrantes (){
        Exposicion expo = this.getExposicion();
        int recuentoEntradasUsadas = this.obtenerRecuentoEntradasGanadores(getGanadores());
        int numReservadas = entradas.size();
        if(recuentoEntradasUsadas < numReservadas){
          int entradasSobrantes = numReservadas - recuentoEntradasUsadas;
          for(int j = 0; j < entradasSobrantes; j++){
            expo.removeEntradaReservada(entradas.remove(0)); //eliminamos de "entradasReservadas" y las movemos a "disponibles"
          }
        }
    }

    @Override
    public void apuntarseSorteo(Cliente c, int nEntradas){
        Participacion p = new Participacion(nEntradas, c, this);
        if (!c.estaPenalizado()){
            c.addParticipaciones(p);

            // setNumeroEntradas(getNumeroEntradas() - nEntradas);
            this.addParticipacion(p);
        }
    }

    public ArrayList<Entrada> getEntradas(){
        return this.entradas;
    }

    public void setEntradas(ArrayList<Entrada> entradas){
        this.entradas = entradas;
    }

    public void setFechaHoraFija (LocalDateTime fechaHora){
        this.fechaFija = fechaHora;
    }

    public LocalDateTime getFechaHoraFija (){
        return this.fechaFija;
    }
}   
