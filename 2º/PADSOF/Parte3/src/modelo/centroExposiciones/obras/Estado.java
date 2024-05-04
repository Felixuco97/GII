package modelo.centroExposiciones.obras;


/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public enum Estado {
  EN_ALMACEN("Almacen"), EN_REPARACION("Reparacion"), EN_EXPOSICION("Exposicion"), FUERA_DE_CENTRO("Fuera del centro");


  private final String name;

  private Estado(String str){ this.name = str;}

  public String getName() { return this.name; }

  public static Estado fromString(String str){
      for (Estado tipo : Estado.values()) {
          if (tipo.getName().equalsIgnoreCase(str)){
              return tipo;
          }
      }
      return null;
  }

}
