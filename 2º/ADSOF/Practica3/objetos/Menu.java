/**
 * Representa un menú con información nutricional y alérgenos.
 * El menú está compuesto por una lista de platos.
 * 
 * @author Felix felix.lopezl@estudiante.uam.es
 * @author Alex alex.pinneiro@estudiante.uam.es
 * 
 */
package objetos;

import java.util.*;
import objetos.enums.*;
import objetos.infoNutricional.*;

public class Menu {
    
    private static int id = 0; // Identificador único para cada menú, se incrementa automáticamente.
    private ArrayList<Plato> pl = new ArrayList<>();
    private double cal = 0;
    private double hCar = 0;
    private double gr = 0;
    private double grSat = 0;
    private double prot = 0;
    private double azuc = 0;
    private double fibr = 0;
    private double sd = 0;
    private ArrayList<Alergeno> al = new ArrayList<>();

    /**
     * Constructor que crea un menú a partir de un array de platos.
     *
     * @param array Los platos que compondrán el menú.
     */
    public Menu(Plato... array){
        for(Plato p: array)
            this.pl.add(p);
    }

    /**
     * Constructor que crea un menú a partir de una lista de platos.
     *
     * @param platos Los platos que compondrán el menú.
     */
    public Menu(List<Plato> platos){

        this.pl.addAll(platos);
    }

    /**
     * Añade alérgenos a la lista de alérgenos del menú.
     *
     * @param lista Alérgenos a añadir.
     */
    public void obtenerAlerg(ArrayList<Alergeno> lista){

        for(Alergeno a: lista)
            this.al.add(a);
    }

    /**
     * Recorre los ingredientes de un plato para calcular su información nutricional y alérgenos.
     *
     * @param p El plato a recorrer.
     */
    public void recorrerIngredientes(Plato p){
        double cantidad;

        for(Ingrediente ing : p.getIngredientes().keySet()){
            cantidad = (double)p.getIngredientes().get(ing);

            if(ing != null){
                calcularInformacion(ing, cantidad);
                addAlerg(ing);
            }
        }
    }

    /**
     * Añade los alérgenos de un ingrediente a la lista de alérgenos del menú.
     *
     * @param in El ingrediente a tener cuenta.
     */
    public void addAlerg(Ingrediente in){
        for(Alergeno a: in.getListaAlergenos()){
            if(!this.al.contains(a))
                this.al.add(a);
        }
        
    }

    /**
     * Calcula y acumula la información nutricional de un ingrediente basándose en su cantidad.
     *
     * @param i El ingrediente a calcular.
     * @param cantidad La cantidad del ingrediente.
     */
    public void calcularInformacion(Ingrediente i, double cantidad){

        InfoNutricional inf = i.getInfoNutricional();

        double factor;

        if(inf instanceof InfoNutricionalPeso){
            factor = cantidad / 100;
        } else if(inf instanceof InfoNutricionalUnidad){
            factor = cantidad;
        } else {
            return;
        }

        this.cal += factor*inf.getCalorias();
        this.hCar += factor*inf.getHidratosCarbono();
        this.gr += factor*inf.getGrasaTotal();
        this.grSat += factor*inf.getGrasasSaturadas();
        this.prot += factor*inf.getProteinas();
        this.azuc += factor*inf.getAzucares();
        this.fibr += factor*inf.getFibra();
        this.sd += factor*inf.getSodio();
    }


    /**
     * Genera y devuelve una cadena de texto con la información del menú.
     *
     * @return String con la información del menú.
     */
    public String toString(){

        String texto = "";
        texto += "Menú " + getId() + " [";

        for(Plato p: this.pl){
        	
            if(p.getPlatos().size() != 0)
                for(Plato plato: p.getPlatos())
                    recorrerIngredientes(plato);

            texto += p.getNombre();
            recorrerIngredientes(p);
                
            if(this.pl.indexOf(p) != this.pl.size()-1)
                texto += ", ";
        }

        texto += "]: ";

        texto += String.format("INFORMACIÓN NUTRICIONAL DEL MENÚ -> Valor energético: %.2f kcal, Hidratos de carbono: ", cal);
        texto += String.format("%.2f g, Grasas: %.2f g, Saturadas: %.2f g, Proteínas: %.2f g, Azúcares: %.2f g, Fibra: %.2f g, Sodio: %.2f mg.", hCar, gr, grSat, prot, azuc, fibr, sd);
        
        if(this.al.size() > 0){
            
            texto += String.format(" CONTIENE ");

            for(Alergeno a: this.al){

                if(a == Alergeno.FRUTOS_SECOS)
                    texto += "frutos secos";
                    
                if(a == Alergeno.GLUTEN)
                    texto += "gluten";

                if(a == Alergeno.HUEVO)
                    texto += "huevo";

                if(a == Alergeno.LACTOSA)
                    texto += "lactosa";
                    
                if(this.al.indexOf(a) != this.al.size()-1)         
                    texto += ", ";
            }
        }

        return texto;
    }

    //Getters

    public int getId(){

        return ++id;
    }

    public ArrayList<Plato> getPlatos(){

        return this.pl;
    }
}