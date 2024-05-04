/**
 * Clase abstracta que representa la información nutricional de un ingrediente.
 * 
 * @author Felix felix.lopezl@estudiante.uam.es
 * @author Alex alex.pinneiro@estudiante.uam.es
 * 
 */
package objetos.infoNutricional;

public abstract class InfoNutricional {
    
    private double calorias;
    private double hidratosCarbono;
    private double grasaTotal;
    private double grasasSaturadas;
    private double proteinas;
    private double azucares;
    private double fibra;
    private double sodio;

    /**
     * Constructor que inicializa la información nutricional con los valores dados.
     */
    public InfoNutricional(double calorias, double hidratosCarbono, double grasaTotal, double grasasSaturadas, double proteinas, double azucares,
                            double fibra, double sodio){

        this.calorias = calorias;
        this.hidratosCarbono = hidratosCarbono;
        this.grasaTotal = grasaTotal;
        this.grasasSaturadas = grasasSaturadas;
        this.proteinas = proteinas;
        this.azucares = azucares;
        this.fibra = fibra;
        this.sodio = sodio;                                
    }

    //Getters

    public double getCalorias(){

        return this.calorias;
    }

    public double getHidratosCarbono(){

        return this.hidratosCarbono;
    }

    public double getGrasaTotal(){

        return this.grasaTotal;
    }

    public double getGrasasSaturadas(){

        return this.grasasSaturadas;
    }

    public double getProteinas(){

        return this.proteinas;
    }

    public double getAzucares(){

        return this.azucares;
    }

    public double getFibra(){

        return this.fibra;
    }

    public double getSodio(){

        return this.sodio;
    }

    public String toString(){

        return String.format("Valor energético: %.2f kcal, Hidratos de carbono: %.2f g, Grasas: %.2f g, Saturadas: %.2f g, Proteínas: %.2f g, Azúcares: %.2f g, Fibra: %.2f g, Sodio: %.2f mg.",
        this.calorias, this.hidratosCarbono, this.grasaTotal, this.grasasSaturadas, this.proteinas, this.azucares, this.fibra, this.sodio);
    }
}
