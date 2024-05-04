package modelo.centroExposiciones.obras.obrasFisicas;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public enum Disenio {
    B_W("Blanco y negro"), COLOR("Color");

    private String tipo;

    private Disenio(String tipo){
        this.tipo = tipo;
    }

    public String getTipo(){
        return this.tipo;
    }

    public static Disenio fromString(String tipo) {
        if (B_W.getTipo().equalsIgnoreCase(tipo)) {
            return B_W;
        }
        return COLOR;
    }
}
