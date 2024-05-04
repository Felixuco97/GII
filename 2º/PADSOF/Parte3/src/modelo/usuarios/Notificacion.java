package modelo.usuarios;

import java.io.Serializable;
import java.time.LocalDate;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class Notificacion implements Serializable{
   
    private TiposNotificacion tipoNot;
    private String contenido;
    private LocalDate fechaLeida;
    private String asunto;
    protected Notificacion(){}
    public Notificacion(TiposNotificacion tipoNot, String contenido, LocalDate fechaLeida, String asunto){

        this.tipoNot = tipoNot;
        this.contenido = contenido;
        this.fechaLeida = fechaLeida;
        this.asunto = asunto;
    }

    /**
     * Muestra la notificación en un formato específico.
     * @return Un string que representa la notificación. El string incluye el tipo de notificación, el asunto, el contenido y la fecha de lectura.
     **/
    public String mostrar(){

        String texto = "";

        texto += "------------------NOTIFICACIÓN: "+this.tipoNot+"------------------\n";
        texto += "ASUNTO: "+this.asunto+"\n";
        texto += this.contenido+"\n";
        texto += this.fechaLeida;

        return texto;
    }

    //Getters

    public TiposNotificacion getTiposNotificacion(){

        return this.tipoNot;
    }

    public String getContenido(){

        return this.contenido;
    }

    public LocalDate getFechaLeida(){

        return this.fechaLeida;
    }

    public String getAsunto(){

        return this.asunto;
    }

    //Setters

    public void setTiposNotificacion(TiposNotificacion tips){

        this.tipoNot = tips;
    }

    public void setContenido(String cont){

        this.contenido = cont;
    }

    public void setFechaLeida(LocalDate fLeida){

        this.fechaLeida = fLeida;
    }

    public void setAsunto(String asunt){

        this.asunto = asunt;
    }
}