package modelo.usuarios;

/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */
public enum Permiso {
    
    VENDER_ENTRADAS("Vender entradas"), MODIFICAR_TH("Modificar_th"), NOTIFICAR("Notificaciones");

    private final String name;

    private Permiso(String str){ this.name = str;}

    public String getName() { return this.name; }

    public static Permiso fromString(String str){
        for (Permiso tipo : Permiso.values()) {
            if (tipo.getName().equalsIgnoreCase(str)){
                return tipo;
            }
        }
        return null;
    }
}

/*
TIPO;NUM_SEGSOCIAL;NUM_CUENTA;DIRECCION;PERMISOS;PREFERENCIA;NOMBRE;NIF;CONTRASENIA;
CLIENTE;;;;;Si;Sara;12345678W;contrasenia1
CLIENTE;;;;;Si;Luis;43256784T;contrasenia2
CLIENTE;;;;;No;Elena;12345678W;contrasenia3
CLIENTE;;;;;No;Manuel;32765489J;contrasenia4
CLIENTE;;;;;Si;Isabel;23543214M;contrasenia5
CLIENTE;;;;;No;Samuel;234126784P;contrasenia6
CLIENTE;;;;;Si;Hailey;72016957D;Func10n@
CLIENTE;;;;;No;Rober;02016954B;Func10n@_S1si
EMPLEADO;111234786543;689309582034;"Calle de los Naranjos, 13";Vender entradas;Alvaro;34898765E;Emple@d0s
EMPLEADO;111232342133;765409582034;"Calle de los Limones, 42";Vender entradas|Notificaciones;Maria;08016051J;Emple@d0s
EMPLEADO;144354567890;876209582034;"Calle de las Rosas, 14";Modificar_th|Notificaciones;Lucia;34564321H;Emple@d0s
EMPLEADO;111234567890;689309582034;"Calle Alan Turin, 5";Vender entradas|Notificaciones;Jesus;15898324R;Emple@d0s
EMPLEADO;357234564570;389345582556;"Calle Ada Lovelace, 18";Modificar_th|Notificaciones;Nuria;34567432F;Emple@d0s
GESTOR;;;;;;Jaime;03135608D;$_G3sT0r_$ 


*/