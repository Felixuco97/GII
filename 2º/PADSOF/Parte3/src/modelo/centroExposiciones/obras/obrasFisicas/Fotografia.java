package modelo.centroExposiciones.obras.obrasFisicas;
import modelo.centroExposiciones.obras.*;

/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */
public class Fotografia extends ObraFisica{
    private Disenio disenio; 
    protected Fotografia(){}
    public Fotografia(String autor, String titulo, int anio, String descripcion, Seguro seguro, Dimension dimensiones, Rango temp, Rango humedad, String duenio, Estado estado, Disenio disenio){
        super(autor, titulo, anio, descripcion, seguro, dimensiones, temp, humedad, duenio, estado);
        this.disenio = disenio;
    }

    @Override
    public boolean comparar(String filtro, String filtrado){
        if (filtro.equalsIgnoreCase("Obra") && filtrado.equalsIgnoreCase("Fotografia")) {
            return true;
        }
        return false;  
    }

    //Setters

    public void setDisenio(Disenio dis){

        this.disenio = dis;
    }

    //Getters

    public Disenio getDisenio(){

        return this.disenio; 
    }

    public static Fotografia parse(String[] str){
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
        //REVISAR, ESTO ESTA MAL, DUENIO TAMBIEN DEBERÍA CAMBIAR
        Estado estado_act = Estado.EN_ALMACEN;
        Disenio dis = Disenio.fromString(str[10]); //str[11], esto cuando ponga todos los nombres de clases en masyusculas estará bien
        //FIN DE REVISION
        //TIPO;ORIGEN;TITULO;AUTOR;AÑO;DESCRIPCION;CUANTIA_SEGURO;NUMERO_POLIZA;TECNICA_CUADRO;MATERIAL_ESCULTURA;COLOR_FOTOGRAFIA;DURACION_AV;IDIOMA_AV;ANCHURA_CM;ALTURA_CM;PROFUNDIDAD_CM;TEMPERATURA;HUMEDAD

        Fotografia fotografia = new Fotografia(str[3], str[2], Integer.parseInt(str[4]), str[5], seguro, dimensiones, rangoTemp, rangoHumd, str[1], estado_act, dis);
        return fotografia;
    }
}
