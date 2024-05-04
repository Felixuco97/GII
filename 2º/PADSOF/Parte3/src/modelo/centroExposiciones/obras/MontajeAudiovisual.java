package modelo.centroExposiciones.obras;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import modelo.centroExposiciones.salas.Exposala;

/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */


public class MontajeAudiovisual extends Obra {
   private String idioma;
   private LocalTime duracion;

   protected MontajeAudiovisual(){}
    public MontajeAudiovisual(String autor,String titulo, int anio, String descripcion, Seguro seguro, String duenio, Estado estado, String idioma, LocalTime duracion){
      
        super(autor, titulo, anio, descripcion, seguro, duenio, estado);
        this.idioma= idioma;
        this.duracion= duracion;
    }
    
    @Override
    public boolean comparar(String filtro, String filtrado){
        if (filtro.equalsIgnoreCase("Obra") && filtrado.equalsIgnoreCase("Montaje Audiovisual")) {
            return true;
        }
        return false;  
    }

    /**
     * Este método verifica si una obra (no física) es compatible con la sala.
     * @param sl La sala donde se va añadir la obra
     * @return Verdadero si la obra es compatible con la sala, falso en caso contrario.
     * La compatibilidad se verifica en base si hay o no suficientes tomas de corriente para todos los montajes AudioVisuales
    */
    @Override
    public boolean compatibilidadObraSala(Exposala sl){
        if (sl.getTomasCorriente() == 0){
            return false;
        }
        sl.setTomasCorriente(sl.getTomasCorriente() - 1);
        return true;  
    }

    //Setters

    public void setIdioma(String idioma){
        this.idioma = idioma;
    }

    public void setDuracion(LocalTime duracion){
        this.duracion = duracion;
    }

    //Getters

    public String getIdioma(){
        return this.idioma;
    }

    public LocalTime getDuracion(){
        return this.duracion;
    }
    
    public static MontajeAudiovisual parse(String[] str){
        Seguro seguro = new Seguro(Integer.parseInt(str[7].replace("SEG", "")), Double.parseDouble(str[6]));
        Estado estado_act = Estado.EN_ALMACEN;
        String formattedTimeString = str[11].replace('h', ':').replace('m', ':');
        formattedTimeString = formattedTimeString.replaceAll("s$", "");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime duracion = LocalTime.parse(formattedTimeString, formatter);
        //FIN DE REVISION
        MontajeAudiovisual montajeAudiovisual = new MontajeAudiovisual(str[3], str[2], Integer.parseInt(str[4]), str[5], seguro, str[1], estado_act, str[12], duracion);
        return montajeAudiovisual;
    }
}
 