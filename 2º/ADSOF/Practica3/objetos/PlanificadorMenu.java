/**
 * La clase PlanificadorMenu permite crear un menú a partir de una lista de platos,
 * aplicando filtros de restricciones nutricionales y alérgenos.
 * 
 * @author Felix felix.lopezl@estudiante.uam.es
 * @author Alex alex.pinneiro@estudiante.uam.es
 * 
 */
package objetos;

import java.util.*;
import objetos.enums.*;

public class PlanificadorMenu {

	/**
     * Lista de platos disponibles para incluir en el menú.
     */
    private ArrayList<Plato> platos = new ArrayList<>();
    /**
     * Lista de alérgenos a excluir en el menú.
     */
    private ArrayList<Alergeno> sinAlergenos = new ArrayList<>();
    /**
     * Mapa que asocia elementos nutricionales con su valor máximo permitido en el menú.
     */
    private Map<ElementoNutricional, Double> maximo = new HashMap<>();

    /**
     * Constructor que inicializa el planificador con una lista de platos.
     * 
     * @param platos Lista de platos disponibles para el menú.
     */
    public PlanificadorMenu(List<Plato> platos){
        this.platos.addAll(platos);
    }

    /**
     * Planifica un menú en base a las restricciones establecidas.
     * 
     * @param min Calorías mínimas permitidas en el menú.
     * @param max Calorías máximas permitidas en el menú.
     * @return Un objeto Menu que cumple con las restricciones, o null si no se puede cumplir.
     */
    public Menu planificar(int min, int max){

        Map<String, Plato> nombrePlatos = new HashMap<>();
        List<Plato> platoEnPlato = new ArrayList<>();

        double cals = 0;
        double hidrCarbono = 0;
        double grasa = 0;
        double grasaSat = 0;
        double prot = 0;
        double azuc = 0;
        double fibr = 0;
        double sod = 0;

        for(Plato plato: this.platos){

            if(plato.getPlatos().size() > 0){

                for(Plato p: plato.getPlatos()){

                    platoEnPlato.add(p);

                    for(Ingrediente i: p.getIngredientes().keySet()){

                        cals += i.getInfoNutricional().getCalorias();
                        hidrCarbono += i.getInfoNutricional().getHidratosCarbono();
                        grasa += i.getInfoNutricional().getGrasaTotal();
                        grasaSat += i.getInfoNutricional().getGrasasSaturadas();
                        prot += i.getInfoNutricional().getProteinas();
                        azuc += i.getInfoNutricional().getAzucares();
                        fibr += i.getInfoNutricional().getFibra();
                        sod += i.getInfoNutricional().getSodio();

                        for(Alergeno a: i.getListaAlergenos()){

                            if(sinAlergenos.contains(a)){

                                nombrePlatos.put(plato.getNombre(), plato);
                            }
                        }
                    }
                }
            } 

            for(Ingrediente i: plato.getIngredientes().keySet()){

                cals += i.getInfoNutricional().getCalorias();
                hidrCarbono += i.getInfoNutricional().getHidratosCarbono();
                grasa += i.getInfoNutricional().getGrasaTotal();
                grasaSat += i.getInfoNutricional().getGrasasSaturadas();
                prot += i.getInfoNutricional().getProteinas();
                azuc += i.getInfoNutricional().getAzucares();
                fibr += i.getInfoNutricional().getFibra();
                sod += i.getInfoNutricional().getSodio();

                for(Alergeno a: i.getListaAlergenos()){

                    if(sinAlergenos.contains(a)){

                        nombrePlatos.put(plato.getNombre(), plato);
                    }
                }
            }

            if(this.maximo.containsKey(ElementoNutricional.HIDRATOS_CARBONO)){
                if(hidrCarbono > this.maximo.get(ElementoNutricional.HIDRATOS_CARBONO)){

                    nombrePlatos.put(plato.getNombre(), plato);
                }
            }
            if(this.maximo.containsKey(ElementoNutricional.GRASA_TOTAL)){
                if(grasa > this.maximo.get(ElementoNutricional.GRASA_TOTAL)){

                    nombrePlatos.put(plato.getNombre(), plato);
                }
            }
            if(this.maximo.containsKey(ElementoNutricional.GRASA_SATURADA)){
                if(grasaSat > this.maximo.get(ElementoNutricional.GRASA_SATURADA)){

                    nombrePlatos.put(plato.getNombre(), plato);
                }
            }
            if(this.maximo.containsKey(ElementoNutricional.PROTEINAS)){
                if(prot > this.maximo.get(ElementoNutricional.PROTEINAS)){

                    nombrePlatos.put(plato.getNombre(), plato);
                }
            }
            if(this.maximo.containsKey(ElementoNutricional.AZUCARES)){
                if(azuc > this.maximo.get(ElementoNutricional.AZUCARES)){

                    nombrePlatos.put(plato.getNombre(), plato);
                }
            }
            if(this.maximo.containsKey(ElementoNutricional.FIBRA)){
                if(fibr > this.maximo.get(ElementoNutricional.FIBRA)){

                    nombrePlatos.put(plato.getNombre(), plato);
                }
            }
            if(this.maximo.containsKey(ElementoNutricional.SODIO)){
                if(sod > this.maximo.get(ElementoNutricional.SODIO)){
    
                    nombrePlatos.put(plato.getNombre(), plato);
                }
            }
        }

        for(String nom: nombrePlatos.keySet()){

            this.platos.remove(nombrePlatos.get(nom));
        }

        Menu menu = new Menu(this.platos);

        if(cals < min || cals > max){

            menu = null;         
        }

        if(this.platos.size() == 0){

            menu = null;
        }

        return menu;
    }

    /**
     * Establece los valores máximos permitidos para determinados elementos nutricionales.
     * 
     * @param ele El elemento nutricional a restringir.
     * @param max El valor máximo permitido para el elemento nutricional.
     * @return El objeto PlanificadorMenu.
     */
    public PlanificadorMenu conMaximo(ElementoNutricional ele, double max){

        this.maximo.put(ele, max);

        return this;
    }

    /**
     * Configura los alérgenos a evitar en el menú.
     * 
     * @param array Lista de alérgenos a excluir.
     * @return El propio objeto PlanificadorMenu
     */
    public PlanificadorMenu sinAlergenos(Alergeno... array){

        for(Alergeno a: array){

            if(!this.sinAlergenos.contains(a))
                this.sinAlergenos.add(a);
        }

        return this;
    }
}
