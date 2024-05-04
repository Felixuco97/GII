package modelo.usuarios;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */
public enum TiposNotificacion {
   
    SORTEO("Sorteo"), DESCUENTO("Descuento"), NUEVA_EXPOSICION("Nueva Exposicion"), CANCELACION_EXPOSICION("Cancelacion Exposicion"), GANADOR_SORTEO("Ganador Sorteo");

    private final String name;

    private TiposNotificacion(String str){ this.name = str;}

    public String getName() { return this.name; }

    public static TiposNotificacion fromString(String str){
        for (TiposNotificacion tipo : TiposNotificacion.values()) {
            if (tipo.getName().equalsIgnoreCase(str)) {
                return tipo;
            }
        }
        return null;
    }
}