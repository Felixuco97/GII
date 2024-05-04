package modelo.centroExposiciones.obras.obrasFisicas;

import modelo.centroExposiciones.obras.*;

/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class Escultura extends ObraFisica{
    private String material;
    protected Escultura(){}
    public Escultura(String autor, String titulo, int anio, String descripcion, Seguro seguro, Dimension dimensiones, Rango temp, Rango humedad, String duenio, Estado estado, String material){
        super(autor, titulo, anio, descripcion, seguro, dimensiones, temp, humedad, duenio, estado);
        this.material= material;
    }

    @Override
    public boolean comparar(String filtro, String filtrado){
        if (filtro.equalsIgnoreCase("Obra") && filtrado.equalsIgnoreCase("Escultura")) {
            return true;
        }
        return false;  
    }

    //Setters

    public void setMaterial (String material){
        this.material = material;
    }

    //Getters

    public String getMaterial (){
        return this.material;
    }
    
    public static Escultura parse(String[] str){
        Seguro seguro = new Seguro(Integer.parseInt(str[7].replace("SEG", "")), Double.parseDouble(str[6]));        
        Rango rangoHumd = new Rango (0,0);
        Rango rangoTemp = new Rango (0,0); 
        Dimension dimensiones = new Dimension(
            str[13].isEmpty() ? 0.0 : Double.parseDouble(str[13]),
            str[14].isEmpty() ? 0.0 : Double.parseDouble(str[14]),
            str[15].isEmpty() ? 0.0 : Double.parseDouble(str[15])
        );
        if (!str[16].isEmpty()){      
            String[] rangoTempString = str[16].split("--");
            rangoTemp = new Rango(Double.parseDouble(rangoTempString[0]), Double.parseDouble(rangoTempString[1]));
        }
        if (!str[17].isEmpty()){
            String[] rangoHumdString = str[17].split("--");
            rangoHumd = new Rango(Double.parseDouble(rangoHumdString[0]), Double.parseDouble(rangoHumdString[1]));
        }
        Estado estado_act = Estado.EN_ALMACEN;
        //FIN DE REVISION
 
        return new Escultura(str[3], str[2], Integer.parseInt(str[4]), str[5], seguro, dimensiones, rangoTemp, rangoHumd, str[1], estado_act, str[9]);
    }

}
