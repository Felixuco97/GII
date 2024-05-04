/**
 * Representa un ingrediente con información nutricional y posibles alérgenos.
 * Esta clase admite ingredientes que pueden categorizarse en tipos conocidos,
 * así como tipos personalizados especificados como una cadena de texto.
 * 
 * @author Felix felix.lopezl@estudiante.uam.es
 * @author Alex alex.pinneiro@estudiante.uam.es
 * 
 */
package objetos;

import java.util.ArrayList;
import objetos.infoNutricional.*;
import objetos.enums.*;

public class Ingrediente{
    
    private String nombre;
    private TipoIngrediente tipo;
    private String otroTipo;
    private InfoNutricional info;
    private boolean alergenos = false;
    private ArrayList<Alergeno> listaAlergenos;

    /**
     * Construye un nuevo Ingrediente con un tipo conocido.
     *
     * @param nombre El nombre del ingrediente.
     * @param tipo El tipo del ingrediente.
     * @param info La información nutricional del ingrediente.
     */
    public Ingrediente(String nombre, TipoIngrediente tipo, InfoNutricional info){
        this.nombre = nombre;
        this.tipo = tipo;
        this.info = info;
        this.listaAlergenos = new ArrayList<Alergeno>();
    }

    /**
     * Construye un nuevo Ingrediente con un tipo personalizado.
     *
     * @param nombre El nombre del ingrediente.
     * @param tipo El tipo del ingrediente especificado como una cadena de texto.
     * @param info La información nutricional del ingrediente.
     */
    public Ingrediente(String nombre, String tipo, InfoNutricional info){
        this.nombre = nombre;
        this.otroTipo = tipo;
        this.info = info;
        this.listaAlergenos = new ArrayList<Alergeno>();
    }

    /**
     * Añade alérgenos al ingrediente.
     *
     * @param array Los alérgenos a añadir al ingrediente.
     * @return El objeto Ingrediente.
     */
    public Ingrediente tieneAlergenos(Alergeno... array){
        for (Alergeno a : array){
            if(a != null)
                this.listaAlergenos.add(a);
        }
        this.alergenos = true;
        return this;
    }

 // Getters


    public String getNombre(){
        return this.nombre;
    }

    public TipoIngrediente getTipo(){
        return this.tipo;
    }

    public String getOtroTipo(){
        return this.otroTipo;
    }

    public InfoNutricional getInfoNutricional(){
        return this.info;
    }

    public boolean getAlergenos(){
        return this.alergenos;
    }

    public ArrayList<Alergeno> getListaAlergenos(){
        return this.listaAlergenos;
    }

    /**
     * Genera y devuelve una cadena de texto con la información del ingrediente.
     *
     * @return String con la información del ingrediente.
     */
    public String toString(){

        String texto = "";

        if(this.tipo == TipoIngrediente.CARNE)
            texto += "[Carne]";
        else if(this.tipo == TipoIngrediente.CEREAL)
            texto += "[Cereal]";
        else if(this.tipo == TipoIngrediente.FRUTA_VERDURA)
            texto += "[Frutas y verduras]";
        else if(this.tipo == TipoIngrediente.HUEVO)
            texto += "[Huevo]";
        else if(this.tipo == TipoIngrediente.LACTEO)
            texto += "[Lácteo]";
        else if(this.tipo == TipoIngrediente.LEGUMBRE)
            texto += "[Legumbre]";
        else if(this.tipo == TipoIngrediente.PESCADO)
            texto += "[Pescado]";
        else
            texto += "[" + this.otroTipo + "]";

        texto += " "+this.nombre+": INFORMACIÓN NUTRICIONAL ";

        if(this.info instanceof InfoNutricionalPeso)
            texto += "POR 100 g";
        if(this.info instanceof InfoNutricionalUnidad)
            texto += "POR UNIDAD";

        texto += " -> "+this.info.toString();

        if(this.alergenos == true){

            texto += " CONTIENE ";

            for(Alergeno a: listaAlergenos){
                if(a == Alergeno.FRUTOS_SECOS)
                    texto += "frutos secos";
                if(a == Alergeno.GLUTEN)
                    texto += "gluten";
                if(a == Alergeno.HUEVO)
                    texto += "huevo";
                if(a == Alergeno.LACTOSA)
                    texto += "lactosa";
                if(listaAlergenos.indexOf(a) != listaAlergenos.size()-1)
                    texto += ", ";
            }
        }

        return texto;
    }
}