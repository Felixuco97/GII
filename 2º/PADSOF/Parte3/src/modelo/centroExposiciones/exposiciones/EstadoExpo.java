package modelo.centroExposiciones.exposiciones;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public enum EstadoExpo {
    
    PROGRAMADA("Programada"), CANCELADA("Cancelada"), CIERRE_TEMPORAL("Cierre temporal"), EN_ALMACEN("En almacen"), EN_CURSO("En curso"), EN_CONSTRUCCION("En construccion");

    private final String name;

    private EstadoExpo(String str){ this.name = str;}

    public String getName() { return this.name; }

    public static EstadoExpo fromString(String str){
        for (EstadoExpo tipo : EstadoExpo.values()) {
            if (tipo.getName().equalsIgnoreCase(str)){
                return tipo;
            }
        }
        return null;
    }
}
