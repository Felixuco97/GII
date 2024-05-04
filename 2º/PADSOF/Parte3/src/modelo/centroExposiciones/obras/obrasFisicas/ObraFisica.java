package modelo.centroExposiciones.obras.obrasFisicas;

import modelo.centroExposiciones.obras.*;
import modelo.centroExposiciones.salas.Exposala;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public abstract class ObraFisica extends Obra {
    private Dimension dimensiones;
    private Rango temp;
    private Rango humedad;
    protected ObraFisica(){}
    public ObraFisica(String autor, String titulo, int anio,  String descripcion, Seguro seguro, Dimension dimensiones, Rango temp, Rango humedad, String duenio, Estado estado){
        super(autor, titulo, anio, descripcion, seguro, duenio, estado);
        this.dimensiones = dimensiones;
        this.temp = temp;
        this.humedad = humedad;
    }


    /**
     * Este método verifica si una obra física es compatible con la sala.
     * @param sl La sala donde se va añadir la obra
     * @return Verdadero si la obra física es compatible con la sala, falso en caso contrario.
     * La compatibilidad se verifica en base a las dimensiones de la obra y la sala, y las condiciones de humedad y temperatura de la sala.
     **/
    @Override
    public boolean compatibilidadObraSala(Exposala sl){
        double anchoObras = 0;
        double largoObras = 0;

        for(Obra o: sl.getObrasSalaActual()){
            anchoObras += o.getDimension().getX();
            largoObras += o.getDimension().getY();
        }
        double largoSala = sl.getDimension().getY();
        double anchoSala = sl.getDimension().getX();
        double altoSala = sl.getDimension().getZ();

        double difX = anchoSala - anchoObras;
        double difY = largoSala - largoObras;

        if((dimensiones.getX() > difX) || dimensiones.getZ() > altoSala || dimensiones.getY() > difY)
            return false;
        
        if (humedad.getInicio() == 0 &&  humedad.getFin() == 0 && temp.getInicio() == 0 &&  temp.getFin() == 0)
            return true; //si no tiene ni humedad ni temperatura definida

        if((humedad.getInicio() > sl.getHumedad()) || (humedad.getFin() < sl.getHumedad()))
            return false;

        if((temp.getInicio() > sl.getTemperatura()) || (temp.getFin() < sl.getTemperatura()))
            return false;

        return true;  
    }
    
    public void setDimension (Dimension dimensiones){
        this.dimensiones = dimensiones;
    }
      
    public void setTemperatura (Rango temp){
        this.temp = temp;
    }
  
    public void setHumedad (Rango humedad){
        this.humedad = humedad;
    }
    
    public Dimension getDimension (){
        return this.dimensiones;
    }
      
    public Rango getTemperatura (){
        return this.temp;
    }
  
    public Rango getHumedad (){
        return this.humedad;
    }
    
}
