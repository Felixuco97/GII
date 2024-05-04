/**
 * Clase que representa la información nutricional de un ingrediente por 100g.
 * 
 * @author Felix felix.lopezl@estudiante.uam.es
 * @author Alex alex.pinneiro@estudiante.uam.es
 * 
 */
package objetos.infoNutricional;

public class InfoNutricionalPeso extends InfoNutricional{

    public InfoNutricionalPeso(double calorias, double hidratosCarbono, double grasaTotal, double grasasSaturadas, double proteinas, double azucares,
                            double fibra, double sodio){

        super(calorias, hidratosCarbono, grasaTotal, grasasSaturadas, proteinas, azucares, fibra, sodio);
    }

    public String toString(){

        return super.toString();
    }
}
