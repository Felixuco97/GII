package modelo.sistema;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import modelo.centroExposiciones.CentroDeExposiciones;
import modelo.centroExposiciones.exposiciones.Temporal;
import modelo.centroExposiciones.salas.Exposala;
import modelo.centroExposiciones.sorteos.*;
import modelo.usuarios.*;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class Sistema implements Serializable{
  private ArrayList<Cliente> clientes = new ArrayList<>();
  private ArrayList<Empleado> empleados = new ArrayList<>();
  private Gestor gestor;
  private ArrayList<UsuarioRegistrado> usuarios_reg = new ArrayList<>();
  private ArrayList<UsuarioRegistrado> inicioSesion = new ArrayList<>();
  private CentroDeExposiciones centro;  
  
  /**
   * Inicia sesión de un usuario registrado con el NIF y contraseña proporcionados.
   * 
   * @param NIF El NIF del usuario.
   * @param contrasenia La contraseña del usuario.
   * @return El UsuarioRegistrado que inició sesión si las credenciales son válidas, o null si no se encuentra ninguna coincidencia.
   */
  public UsuarioRegistrado iniciarSesion(String NIF, String contrasenia){
    for(UsuarioRegistrado usr : this.usuarios_reg){
      if (usr.getNIF().equals(NIF)){
      //Estaría bien encriptar las contraseñas, sino 
      //se pueden juntar estos dos ifs en uno con un &&
        if( usr.getcontrasenia().equals(contrasenia)){
          this.inicioSesion.add(usr);
          return usr; // igual hay que comprobar si es empleado, usr o gestor
        }
      }
    }
    return null;
  }
  /**
   * Inicia el sistema a partir de un archivo de datos proporcionado.
   * 
   * @param path La ruta del archivo de datos.
   * @return El sistema inicializado.
   */
  public static Sistema iniciarSistema(String path) {
    Sistema sistema;

    try{
      sistema = Sistema.cargar(path);
    } catch (FileNotFoundException e){ //No existe el fichero a cargar == Nuevo
      sistema = new Sistema();
      //Horario estandard, luego se puede cambiar en la aplicacion
      sistema.centro = new CentroDeExposiciones(LocalTime.of(9,00), LocalTime.of(18,00)); 
      //sistema.gestor = new Gestor("Jaime", "03135608D", "$_G3sT0r_$");
      try{
        //Inicialmente cuando el centro de expo se acaba de abrir tiene obras y salas
        sistema.readUsuarios("resources/usuariosregistrados.csv");
        sistema.getCentro().parse("resources/obras.csv");
        ArrayList<Exposala> exposalas = sistema.getCentro().readSalas("resources/salas.csv");
        sistema.getCentro().readExposicion(sistema, "resources/exposiciones.csv", exposalas);
        sistema.getCentro().readSorteos("resources/sorteos.csv");
      } catch (IOException l){
        l.printStackTrace();
        return null;
      }
    }catch (IOException  | ClassNotFoundException e){
      e.printStackTrace();
      return null;
    }
    CentroDeExposiciones centro = sistema.getCentro();
    for (Temporal t : centro.getExposTemporales()){
      if (t.getFechaFin().isBefore(LocalDate.now())){
        try{
          centro.eliminarExposiciones(t);
        } catch (Exception e){
          e.printStackTrace();
        }
      }
    }

    for (Sorteo s: centro.getSorteos()){
      if (s.getFinInscripcion().isBefore(LocalDateTime.now())){
        Gestor.setFlagSorteo(s);
      }
    }

    return sistema;
  }

  
  /**
   * Carga, si existe, la informacion del sistema de las anteriores ejecuciones
   * @param path ruta al fichero de datos que se va a cargar
   * @throws IOException fallos de entrada/salida
   * @throws ClassNotFoundException fallos de si no existe la clase a cargar
  */
  public static Sistema cargar(String path) throws IOException, ClassNotFoundException {
    FileInputStream fileIn = new FileInputStream(path);
    ObjectInputStream in = new ObjectInputStream(fileIn);
    Sistema sistema = null;
    sistema = (Sistema) in.readObject();
    in.close();
    fileIn.close();
    return sistema;
  }
  /**
   * Guarda la informacion del sistema actual, normalmente se ejecuta cuando se vaya
   * ha cerrar la  apliacion para asegurar la permanencia de los datos
   * @param path ruta al fichero de datos que se va a cargar
   * @throws IOException fallos de entrada/salida
   */
  public void guardar(String path) throws IOException{
    // Guardar el estado del sistema
    this.inicioSesion.clear();
    FileOutputStream fileOut = new FileOutputStream(path);
    ObjectOutputStream out = new ObjectOutputStream(fileOut);
    out.writeObject(this);
    out.close();
    fileOut.close();
  }

  /**
   * Cierra la sesión del usuario registrado.
   * @param usr El usuario registrado cuya sesión se va a cerrar.
  **/
  public void cerrarSesion(UsuarioRegistrado usr){
    if(this.inicioSesion.contains(usr)){

      this.inicioSesion.remove(usr);
    }
  }


  /**
   * Modifica las contraseñas de los empleados proporcionados.
   * @param contrasenia La nueva contraseña.
   * @param empleados Los empleados a los que se les va a cambiar la contraseña.
   **/
  public void modificarContrasenias(String contrasenia,Empleado... empleados){
    for(Empleado emp: empleados){
      emp.setContrasenia(contrasenia);
    }
  }


  /**
   * Identifica el tipo de usuario registrado.
   * @param usuario El usuario registrado a identificar.
   * @return Una String que representa a que panel de inicio se tiene que mover
   **/
  public String identificarUsuario(UsuarioRegistrado usuario){ 
    int id = usuario.getId();
    if (id == 0) {
      return "InicioCliente";
    } else if (id == 1){
      return "InicioEmpleado";
    } else if (id == 2) {
      return "InicioGestor";
    }
    return "FALLO";
  }

  /**
   * Da de alta a un nuevo cliente.
   * @param nombre El nombre del cliente.
   * @param nif El NIF del cliente.
   * @param contrasenia La contraseña del cliente.
   * @param preferenciaNotificaciones La preferencia de notificaciones del cliente.
   * @return True si es unico y false si no
   **/
  public boolean darDeAltaCliente(String nombre, String nif, String contrasenia, boolean preferenciaNotificaciones){
    for (Empleado e : empleados){
      if (e.getNIF().equals(nif)){ //Unique
        return false;
      }
    }

    for (Cliente c : clientes){
      if (c.getNIF().equals(nif)){ //Unique
        return false;
      }
    }

    if (gestor != null){ //Unique
      if (gestor.getNIF().equals(nif))
        return false;
    }

    Cliente c = new Cliente(nombre, nif, contrasenia, preferenciaNotificaciones);
    clientes.add(c);
    usuarios_reg.add(c);
    return true;
  }



  /**
   * Da de alta a un nuevo empleado.
   * @param nombre El nombre del empleado.
   * @param nif El NIF del empleado.
   * @param contrasenia La contraseña del empleado.
   * @param numSegSocial El número de seguridad social del empleado.
   * @param numCuenta El número de cuenta del empleado.
   * @param direccion La dirección del empleado.
   * @param permisos Los permisos del empleado.
   * @return True si es unico y false si no
   **/
  public boolean darDeAltaEmpleado(String nombre, String nif , String contrasenia, String numSegSocial, String numCuenta, String direccion, ArrayList<Permiso> permisos){
    for (Empleado e : empleados){
      if (e.getNIF().equals(nif)){ //Unique
        return false;
      }
    }

    for (Cliente c : clientes){
      if (c.getNIF().equals(nif)){ //Unique
        return false;
      }
    }

    if (gestor.getNIF().equals(nif)) //Unique
      return false;

    Empleado e = new Empleado(nombre, nif, contrasenia, numSegSocial, numCuenta, direccion,permisos);

    empleados.add(e);
    usuarios_reg.add(e);
    return true;
  }


  /**
   * Comprueba si un empleado tiene un permiso específico.
   * @param empleado El empleado a comprobar.
   * @param permiso El permiso a comprobar.
   * @return Verdadero si el empleado tiene el permiso, falso en caso contrario.
   **/
  public boolean comprobarPermisos(Empleado empleado, Permiso permiso){
    if (empleado.getPermisos().contains(permiso)){
      return true;
    }
    return false;
  }



  /**
   * Envía notificaciones a los clientes si el empleado tiene permiso para notificar.
   * @param emp El empleado que envía las notificaciones.
   * @param nots Las notificaciones a enviar.
   **/
  public void enviarNotificaciones(Empleado emp, Notificacion... nots){
    if (emp != null && emp.getPermisos().contains(Permiso.NOTIFICAR)){
      for(Cliente c: clientes){
        c.actualizarBandejaNotificaciones(nots);
      }
    }
  }

  /**
   * Gestiona los permisos de un empleado.
   * @param empleado El empleado cuyos permisos se van a gestionar.
   * @param accion La acción a realizar: "Añadir" para añadir permisos, "Quitar" para quitar permisos.
   * @param perms Los permisos a añadir o quitar.
   **/
  public void gestionarPermisosEmpleado(Empleado empleado, String accion, Permiso... perms){ //Igual sobra
    if(accion.equals("Añadir")){

      empleado.aniadirPermisos(perms);
    }
    if(accion.equals("Quitar")){

      empleado.quitarPermisos(perms);
    }
  }
  /**
   * Lee los usuarios registrados desde un archivo CSV y los agrega al sistema.
   * 
   * @param fileName El nombre del archivo CSV que contiene los usuarios registrados.
   * @throws IOException si ocurre un error de lectura del archivo.
   */
  public void readUsuarios (String fileName) throws IOException{
    BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
    String linea;
    while ((linea = file.readLine()) != null){
      String lineas[] = linea.split(";");
      if (lineas[0].equals("CLIENTE")){
        boolean preferencias = lineas[5].equalsIgnoreCase("Si") ? true : false;
        if(!this.darDeAltaCliente(lineas[6], lineas[7], lineas[8], preferencias)){
          System.err.println("Ya existe un usario con ese NIF");
        }
      }
      else if (lineas[0].equals("EMPLEADO")){
        String[] permisoStr = lineas[4].split("\\|");
        ArrayList<Permiso> permisos = new ArrayList<>(); 
        for (String p : permisoStr){
          permisos.add(Permiso.fromString(p));
        }
        if(!this.darDeAltaEmpleado(lineas[6], lineas[7], lineas[8], lineas[1], lineas[2], lineas[3], permisos))
          System.err.println("Ya existe un usario con ese NIF");
      } 
      
      else if (lineas[0].equals("GESTOR")){
        Gestor g = new Gestor(lineas[6], lineas[7], lineas[8]);
        this.gestor = g;
        this.usuarios_reg.add(g);
      }
  
    }
    file.close();
  }

//Getters

public ArrayList<UsuarioRegistrado> getUsuarioRegistrados(){

    return this.usuarios_reg;
}

public ArrayList<UsuarioRegistrado> getIniciadosSesion(){

  return this.inicioSesion;
}

public ArrayList<Empleado> getEmpleadosRegistrados(){

  return this.empleados;
}

public ArrayList<Cliente> getClientes(){

  return this.clientes;
}

public CentroDeExposiciones getCentro (){
  return this.centro;
}

public Gestor setGestor(){
  return this.gestor;
}

//Setter
public void setCentro(CentroDeExposiciones centro){
  this.centro = centro;
}

public void setGestor(Gestor gestor){
  this.gestor = gestor;
  this.usuarios_reg.add(gestor);
}
}
