package modelo.centroExposiciones.sorteos;

import java.time.LocalDateTime;
import java.util.ArrayList;

import modelo.centroExposiciones.Entrada;
import modelo.centroExposiciones.exposiciones.Exposicion;
import modelo.usuarios.Cliente;
import modelo.usuarios.Participacion;

public class SorteoFechaLibre extends Sorteo{

    protected SorteoFechaLibre(){}
    public SorteoFechaLibre(int numeroEntradas, int numGanadores, LocalDateTime inicioInscripcion, LocalDateTime finInscripcion, int penalizacion, Exposicion expo){
        super( numeroEntradas, numGanadores, inicioInscripcion, finInscripcion, penalizacion, expo);
    }
    /**
     * Determina si un cliente puede canjear un código en una fecha y hora específicas.
     * 
     * @param c El cliente que intenta canjear el código.
     * @param fechaHora La fecha y hora en la que se intenta canjear el código.
     * @return true si el cliente puede canjear el código en la fecha y hora especificadas, false en caso contrario.
     */
    @Override
    public boolean canjearCodigo(Cliente c, LocalDateTime fechaHora){
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
                    Entrada ent = null;
                    if ((expo.calcularDisponibilidadEntradas(fechaHora) + n) > expo.getNumEntradas()){
                        return false;
                    }
                    ent = new Entrada("EventHub", expo.getNombre(), expo.getNumEntradas(), fechaHora, 0, 0, 0, "", "", c);
                    expo.getEntradasExpo().add(ent); //Comprobar
                    //igual se puede meter un metodo como comprar pero que sea simplemente lo de pago y eso, asi tambien sería mas corto
                    c.addEntrada(ent);
                    this.setNumeroEntradas(this.getNumeroEntradas() - 1);
                }
                if (n == 2){
                    Entrada ent = null;
                    Entrada ent2 = null;
                    if ((expo.calcularDisponibilidadEntradas(fechaHora) + n) > expo.getNumEntradas()){
                        return false;
                    }
                    ent = new Entrada("EventHub", expo.getNombre(), expo.getNumEntradas(), fechaHora, 0, 0, 0, "", "",c);
                    ent = new Entrada("EventHub", expo.getNombre(), expo.getNumEntradas(), fechaHora, 0, 0, 0, "", "", c);

                    expo.getEntradasExpo().add(ent); //Comprobar
                    expo.getEntradasExpo().add(ent2);
                    c.addEntrada(ent);
                    c.addEntrada(ent2);
                    this.setNumeroEntradas(this.getNumeroEntradas() - 2);
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
        this.setNumeroEntradas(numEntradasReservar);
    }

    /**
     * Este método "devuelve" las entradas sobrantes después de un sorteo.
     *
     **/
    public void devolverEntradasSobrantes (){
        int recuentoEntradasUsadas = this.obtenerRecuentoEntradasGanadores(getGanadores());
        if(recuentoEntradasUsadas < this.getNumeroEntradas()){
            this.setNumeroEntradas(recuentoEntradasUsadas);
        }
    }

    @Override
    public void apuntarseSorteo(Cliente c, int nEntradas){
        Participacion p = new Participacion(nEntradas, c, this);
        if (!c.estaPenalizado()){
            c.addParticipaciones(p);

            //setNumeroEntradas(getNumeroEntradas() - nEntradas);
            this.addParticipacion(p);
        }
    }
}
