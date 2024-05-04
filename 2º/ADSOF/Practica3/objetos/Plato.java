/**
 * Representa un plato con distintos ingredientes u otros platos
 * y su información nutricional.
 * 
 * @author Felix felix.lopezl@estudiante.uam.es
 * @author Alex alex.pinneiro@estudiante.uam.es
 * 
 */
package objetos;

import java.util.*;
import objetos.infoNutricional.*;
import objetos.enums.*;

public class Plato {
    
    private String nombre;
    private Map<Ingrediente, Integer> ingredientes = new HashMap<>();
    private ArrayList<Plato> platos = new ArrayList<>();
    private double calorias = 0;
    private double hidratosCarbono = 0;
    private double grasas = 0;
    private double grasasSaturadas = 0;
    private double proteinas = 0;
    private double azucares = 0;
    private double fibra = 0;
    private double sodio = 0;
    private ArrayList<Alergeno> alerg = new ArrayList<>();

    /**
     * Constructor que crea un nuevo plato con el nombre especificado.
     *
     * @param nombre El nombre del plato.
     */
    public Plato(String nombre){

        this.nombre = nombre;
    }

    /**
     * Agrega un ingrediente al plato con una cantidad exacta, ya sea en gramos
     * o en unidades. Si el ingrediente ya existe en el plato, no se agrega de nuevo.
     *
     * @param ing El ingrediente a agregar.
     * @param valor La cantidad del ingrediente a agregar.
     * @return true si el ingrediente ya estaba presente; false en caso contrario.
     */
    public boolean addIngrediente(Ingrediente ing, int valor){

        if(this.ingredientes.containsKey(ing))
            return true;

        this.ingredientes.put(ing, valor);

        return false;
    }

    /**
     * Agrega un plato a la lista de platos contenidos dentro de este plato.
     * Esto permite construir platos compuestos por otros platos.
     *
     * @param p El plato a agregar.
     */
    public void addPlato(Plato p){

        this.platos.add(p);
    }

    /**
     * Recorre los ingredientes de un plato dado y usa otro método para ir calculando
     *  su información nutricional y alérgenos.
     *
     * @param p El plato a recorrer.
     */
    public void recorrerIngredientes(Plato p){
        double cantidad;

        for(Ingrediente ing : p.getIngredientes().keySet()){
            cantidad = (double)p.getIngredientes().get(ing);
            
            if(ing != null){
                calcularInfo(ing, cantidad);
                addAlergenos(ing);
            }
        }
    }

    /**
     * Agrega los alérgenos de un ingrediente a la lista de alérgenos del plato.
     *
     * @param in El ingrediente para añadir sus alérgenos.
     */
    public void addAlergenos(Ingrediente in){
        for(Alergeno a: in.getListaAlergenos()){
            if(!this.alerg.contains(a))
                this.alerg.add(a);
        }
        
    }

    /**
     * Suma a la información nutricional del plato los datos referentes a un ingredientes y su cantidad.
     *
     * @param i El ingrediente del cual se calculará la información.
     * @param cantidad La cantidad del ingrediente.
     */
    public void calcularInfo(Ingrediente i, double cantidad){

        InfoNutricional inf = i.getInfoNutricional();

        double factor;

        if(inf instanceof InfoNutricionalPeso){
            factor = cantidad / 100;
        } else if (inf instanceof InfoNutricionalUnidad) {
            factor = cantidad;
        } else {
            return;
        }

        this.calorias += factor*inf.getCalorias();
        this.hidratosCarbono += factor*inf.getHidratosCarbono();
        this.grasas += factor*inf.getGrasaTotal();
        this.grasasSaturadas += factor*inf.getGrasasSaturadas();
        this.proteinas += factor*inf.getProteinas();
        this.azucares += factor*inf.getAzucares();
        this.fibra += factor*inf.getFibra();
        this.sodio += factor*inf.getSodio();
    }

    /**
     * Genera y devuelve una cadena de texto con la información del plato.
     *
     * @return String con la información del plato.
     */
    public String toString(){

        String texto = "";
        texto += "[Plato] "+this.nombre+": INFORMACIÓN NUTRICIONAL DEL PLATO -> ";
        
        if(this.platos.size() > 0){
            for(Plato p: this.platos){
                recorrerIngredientes(p);
            }       
        }

        recorrerIngredientes(this);

        texto += String.format("Valor energético: %.2f kcal, Hidratos de carbono: %.2f g, Grasas: %.2f g, Saturadas: %.2f g, Proteínas: %.2f g, Azúcares: %.2f g, ",
                calorias, hidratosCarbono, grasas, grasasSaturadas, proteinas, azucares);
        texto += String.format("Fibra: %.2f g, Sodio: %.2f mg.", fibra, sodio);

        if(this.alerg.size() > 0){

            texto += " CONTIENE ";

            for(Alergeno a: this.alerg){

                if(a == Alergeno.FRUTOS_SECOS)
                    texto += "frutos secos";
                    
                if(a == Alergeno.GLUTEN)
                    texto += "gluten";

                if(a == Alergeno.HUEVO)
                    texto += "huevo";

                if(a == Alergeno.LACTOSA)
                    texto += "lactosa";
                    
                if(this.alerg.indexOf(a) != this.alerg.size()-1){
                    texto += ", ";
                }
            }
        }

        return texto;
    }

    //Getters

    public Map<Ingrediente, Integer> getIngredientes(){
        return this.ingredientes;
    }

    public ArrayList<Alergeno> getListAlerg(){
        return this.alerg;
    }

    public ArrayList<Plato> getPlatos(){
        return this.platos;
    }

    public String getNombre(){
        return this.nombre;
    }
}
