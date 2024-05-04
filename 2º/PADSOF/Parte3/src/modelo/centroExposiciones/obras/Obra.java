package modelo.centroExposiciones.obras;

import java.io.Serializable;
import modelo.centroExposiciones.exposiciones.*;
import modelo.centroExposiciones.obras.obrasFisicas.Dimension;
import modelo.centroExposiciones.salas.Exposala;
import modelo.utils.IBusqueda;

import java.util.*;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public abstract class Obra implements Serializable, IBusqueda{
    private String autor;
    private Seguro seguro;
    private String duenio;
    private String descripcion;
    private String titulo;
    private int anio;
    private Estado estado; 
    
    protected Obra(){}
    public Obra(String autor, String titulo, int anio, String  descripcion, Seguro seguro, String duenio, Estado estado){

      this.autor = autor;
      this.descripcion = descripcion;
      this.seguro = seguro;
      this.duenio = duenio;
      this.estado = estado;
      this.titulo = titulo;
      this.anio = anio;
    }


    /**
     * Este método repara una obra de arte.
     * @param o La obra de arte a reparar.
     * @throws Exception Si la obra de arte no pertenece al centro.
     **/
    public void reparar(Obra o) throws Exception{
        if (o.getDuenio().equalsIgnoreCase("CENTRO")){
          o.setEstado(Estado.EN_REPARACION);
        }
        else{
          throw new Exception("La obra no pertenece al CENTRO");
        }
      }
  
      
    /**
     * Este método presta una obra de arte.
     * 
     * @throws Exception Si la obra de arte no pertenece al centro.
     **/
    public void prestar() throws Exception{
        if (duenio.equalsIgnoreCase("CENTRO")){
            estado = Estado.FUERA_DE_CENTRO;
        }
        else{
            throw new Exception("La obra no pertenece al CENTRO");
        }
    }


  
    /**
     * Este método devuelve una obra de arte a una exposición.
     * @param o La obra de arte a devolver.
     * @param exposicion La exposición a la que se va a devolver la obra de arte.
     * @return Verdadero si la obra de arte se pudo devolver a la exposición, falso en caso contrario.
     **/
    public boolean devolverToExpo(Obra o, Exposicion exposicion){
      //Falta ctrl de errores, hay que recordar que no se pueden añadir obras
      //a expos empezadas, por lo que si no estaba en una expo tiene que fallar

      ArrayList<Obra> obrasExposicion = exposicion.getObras();


      /*comprobar que la obra pertenezca a la expo a la que queremos devolverla */
      if(obrasExposicion.contains(o)){
        System.out.println(("La obra pertenece a la exposicion a la que queremos devolverla"));
        return false;
      }

      /*comprobar si la exposicion esta en curso */
      if(exposicion.getEstado() == EstadoExpo.EN_CURSO){
        System.out.println("No se puede devolver la obra a la exposicion porque esta en curso");
        return false;
      }
      
      o.setEstado(Estado.EN_EXPOSICION);
      exposicion.aniadirObras(o);
      return true;
    }
    /**
     * Este método mueve una obra de arte a una exposición.
     **/
    public void moverToExpo(){ //COMPROBAR igual falta control de errores
      this.estado = Estado.EN_EXPOSICION;
    }

    /**
     * Este método mueve una obra de arte al almacén.
     **/
    public void moverToAlmacen(){
      //Esta creo que no necesita ctrl de errores ya que todas tipo de obras
      //pueden ir al Almacen
      this.estado = Estado.EN_ALMACEN;
    }

    /**
     * Este método verifica si una obra es compatible con la sala.
     * @param sl La exposala donde se vaya a añadir
     * @return Verdadero si la obra es compatible con la sala, falso en caso contrario.
     **/
    public abstract boolean compatibilidadObraSala(Exposala sl);


    //Setters

    public void setAutor(String autor){
      this.autor = autor;
    }

    public void setDescripcion(String descripcion){
      this.descripcion = descripcion;
    }

    public void setTitulo(String titulo){
      this.titulo = titulo;
    }

    public void setAnio(int anio){
      this.anio = anio;
    }

    public void setSeguro (Seguro seguro){
      this.seguro = seguro;
    }

    public void setDuenio (String duenio){
      this.duenio = duenio;
    }

    public void setEstado (Estado estado){ //ERROR
      this.estado = estado;
    }

    //Getters

    public Dimension getDimension() {
      return new Dimension(0, 0, 0); //las obras que no seon fisicas no tienen dimensiones
    }

    public String getAutor(){
      return this.autor;
    }

    public String getDescripcion(){
      return this.descripcion;
    }

    public String getTitulo(){
      return this.titulo;
    }

    public int getAnio(){
      return this.anio;
    }

    public Seguro getSeguro (){
      return this.seguro;
    }

    public String getDuenio (){
      return this.duenio;
    }

    public Estado getEstado (){
      return this.estado;
    }
    
}
