package modelo.usuarios;

import java.util.ArrayList;

import modelo.centroExposiciones.*;
import modelo.centroExposiciones.exposiciones.*;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class Empleado extends UsuarioRegistrado{
    public static final int id = 1;
    private String numSegSocial;
    private String numCuenta;
    private String direccion; 
    private ArrayList<Permiso> permisos = new ArrayList<>();
    private ArrayList<Entrada> entradas = new ArrayList<>();

    protected Empleado(){
        super(null, null, null);
    }
    public Empleado(String nombre, String nif, String contrasenia, String numSegSocial, String numCuenta, String direccion, Permiso... perms){
        super(nombre, nif, contrasenia);
        this.numSegSocial = numSegSocial;
        this.numCuenta = numCuenta;
        this.direccion = direccion;
        for (Permiso perm: perms){
            permisos.add(perm);
        }
    }

    public Empleado(String nombre, String nif, String contrasenia, String numSegSocial, String numCuenta, String direccion, ArrayList<Permiso> perms){
        super(nombre, nif, contrasenia);
        this.numSegSocial = numSegSocial;
        this.numCuenta = numCuenta;
        this.direccion = direccion;
        for (Permiso perm: perms){
            permisos.add(perm);
        }
    }

    /**
     * Añade una entrada a la lista de entradas.
     * @param e La entrada a añadir.
     **/
    public void addEntrada(Entrada e){

        this.entradas.add(e);
    }

    /**
     * Añade una serie de permisos a la lista de permisos.
     * @param array Los permisos a añadir.
     **/
    public void aniadirPermisos(Permiso... array){

        for(Permiso p: array){
            if (!permisos.contains(p))
                this.permisos.add(p);
        }
    }

    /**
     * Quita una serie de permisos de la lista de permisos.
     * @param array Los permisos a quitar.
     **/
    public void quitarPermisos(Permiso... array){
        for(Permiso p: array){
            if (permisos.contains(p))
                this.permisos.remove(p);
        }
    }



    /**
     * Este metodo permite a un empleado comprar (vender) entradas
     * 
     * @param exposicion La exposición para la cual se desean comprar las entradas
     * @param array      Un array de objetos Entrada que se desean comprar
     * @throws Exception Se lanza una excepción si no quedan entradas disponibles para esa hora
     **/
    public void comprarEntradas(Exposicion expo, ArrayList<Entrada> array) throws Exception{
        int entradasNoDisponibles = expo.calcularDisponibilidadEntradas(array.get(0).getFechaHora());

            for(Entrada e: array){
                if(entradasNoDisponibles < expo.getNumEntradas()){
                    if(!expo.getEntradasExpo().contains(e)){
                        this.addEntrada(e);
                        expo.setNumeroEntradas(expo.getNumEntradas() - 1);
                        expo.getEstadisticas().setNumEntradasVendidas(e.getDiaSemana(), expo.getEstadisticas().getNumEntradasVendidas() + 1);
                        expo.getEstadisticas().setBeneficios(e.getDiaSemana(), expo.getEstadisticas().getBeneficios() + expo.getPrecioEntradas());
                        entradasNoDisponibles++;
                    } else {
                        throw new Exception("La entrada ya esta comprada para esta exposicion");
                    }

                }
                else{
                    throw new Exception("No quedan entradas para esa hora");
                }
            }
        if(!array.get(0).generarPdf(expo, array.size())) // todas las entradas que se compran a la vez tienen los mismos datos
            System.err.println("Entrada no generada");   
    }


   //Getters

    public String getNumSegSocial(){

        return this.numSegSocial;
    }

    public String getNumCuenta(){
    
        return this.numCuenta;
    }

    public String getDireccion(){
        
        return this.direccion;
    }

    public ArrayList<Permiso> getPermisos(){

        return this.permisos;
    }

    public ArrayList<Entrada> getEntradas(){
        return this.entradas;
    }

    @Override
    public int getId(){
        return id;
    }
    
   //Setters

    public void setNumSegSocial(String numSegSocial){

        this.numSegSocial = numSegSocial;
    }

    public void setNumCuenta(String numCuenta){
    
        this.numCuenta = numCuenta;
    }

    public void setDireccion(String direccion){
        
        this.direccion = direccion;
    }

    public void setPermisos(Permiso... perms){
        for (Permiso perm: perms){
            permisos.add(perm);
        }
    }

    public void setContrasenia(String contrasenia){

        this.contrasenia = contrasenia;
    }
}
