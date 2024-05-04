package modelo.centroExposiciones.salas;

import java.util.*;

import modelo.centroExposiciones.obras.*;
import modelo.centroExposiciones.obras.obrasFisicas.Dimension;
import modelo.centroExposiciones.obras.obrasFisicas.ObraFisica;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class Exposala extends Sala{

    private HashSet<Obra> obras = new HashSet<>();
    private ArrayList<Exposala> subsalas = new ArrayList<>();
    protected Exposala(){}

    public  Exposala(String nombre, Dimension dim, int aforo, int tomasCorriente, double temp, double humd){
        super(nombre, dim, aforo, tomasCorriente, temp, humd);
    }
    
    /**
     * Este método añade obras a la exposición.
     *
     * @param array Un array de objetos Obra que se añadirán a la exposición.
     **/
    public boolean addObras(Obra... array) {
        boolean flag = true;
        for(Obra o: array){
            if (o.getEstado() != Estado.EN_EXPOSICION){ //si esta en otra exposicion falla
                o.moverToExpo();
                if (o.compatibilidadObraSala(this)){
                    this.obras.add(o);
                }
                else {
                    System.err.println("La obra "+ o.getTitulo() + " no es compatible con la sala");
                    flag = false;
                }

            }
            else{
                System.err.println("La obra esta dentro de una exposicion");
                flag = false;
            }   
        }
        return flag;
    }

    public void addSubsala(Exposala sl){
        subsalas.add(sl);
    }


    /**
     * Este método elimina obras de la exposición.
     *
     * @param obras Un array de objetos Obra que se eliminarán de la exposición.
     * @throws Exception Se lanza una excepción si la obra está en otra exposición.
     **/
    public void removeObras(Obra... obras) throws Exception{

        for(Obra o: obras){
            if (o.getEstado() != Estado.EN_EXPOSICION){
                this.removeObras(o);
                this.setTomasCorriente(getTomasCorriente() + 1);
            }
            else
                throw new Exception("La obra esta dentro de una exposicion");
        }
    }

    /**
     * Este método elimina obras de la exposición.
     *
     * @param obras Un array de objetos Obra que se eliminarán de la exposición.
     * @throws Exception Se lanza una excepción si la obra está en otra exposición.
     **/
    public void removeObras(ObraFisica... obras) throws Exception{

        for(Obra o: obras){
            if (o.getEstado() != Estado.EN_EXPOSICION)
                this.removeObras(o);
            else
                throw new Exception("La obra esta dentro de una exposicion");
        }
    }


    /**
     * Este método divide una sala en varias subsalas.
     * @param array Un array de objetos Sala que representan las subsalas.
     * @throws Exception Si la sala ya está dividida en subsalas o si el área total de las subsalas no es igual al área de la sala.
     **/
    public void dividirSala(int anchoNuevaSala/*Sala... array*/) throws Exception{
        double anchoSalaOrig = this.getDimension().getX();

        if (anchoSalaOrig <= anchoNuevaSala){ //No quieres dividir y quedarte con la misma sala
            throw new Exception("La sala hija tiene que ser de menor anchura de la original");
        }
        
        this.getDimension().setX(anchoSalaOrig - anchoNuevaSala);
        int nuevoAforo = (int)(anchoNuevaSala / anchoSalaOrig *  this.getAforo()); //CAMBIAR
        this.setAforo(this.getAforo() - nuevoAforo);
        Dimension nuevaDim = new Dimension(anchoNuevaSala, this.getDimension().getY(), this.getDimension().getZ());
        int nuevaToma = 0;
        int tomasOrig = this.getTomasCorriente();
        if (this.getTomasCorriente() > 1){
            tomasOrig --;
            nuevaToma++;
        }
        this.setTomasCorriente(tomasOrig);
        String nuevoNom = this.getNombre() + "-" + (subsalas.size() + 1);
        this.subsalas.add(new Exposala(nuevoNom, nuevaDim, nuevoAforo, nuevaToma, this.getTemperatura(), this.getHumedad()));
    }

    /**
     * Este método unfica una subsalas en la sala padre
     * 
     * @return false si no hay salas por unificar y true si las hay
     **/
    public boolean unificarSala() {
        if (subsalas.size() == 0){
           return false;  //No se puede unficar ya que no hay subsalas
        }

        double x = getDimension().getX();
        int aforo = this.getAforo();
        int tomasCorriente = this.getTomasCorriente();
        for (Exposala s: subsalas){
            x += s.getDimension().getX();
            aforo += s.getAforo();
            tomasCorriente += s.getTomasCorriente(); 
            subsalas.remove(s);
            s = null;
        }
        this.getDimension().setX(x);
        this.setAforo(aforo);
        this.setTomasCorriente(tomasCorriente);
        return true;
    }


    //Getters

    public ArrayList<Obra> getObras(){
        ArrayList<Obra> obras_mostrar = new ArrayList<>();
        obras_mostrar.addAll(obras);
        for (Exposala sl : subsalas){
            obras_mostrar.addAll(sl.getObras());
        }
        return obras_mostrar;
    }

    public ArrayList<Obra> getObrasSalaActual(){
        ArrayList<Obra> obras_mostrar = new ArrayList<>();
        obras_mostrar.addAll(obras);
        return obras_mostrar;
    }

    public ArrayList<Exposala> getSubSalas(){
        return subsalas;
    }


    public boolean setTemperatura (double temperatura){
        double tempAnterior = this.getTemperatura();
        super.setTemperatura(temperatura);
        for (Obra o : obras){
            if(!o.compatibilidadObraSala(this)){
                this.setTemperatura(tempAnterior);
                return false;
            }
        }
        return true;
    }

    public boolean setHumedad (double humd){
        double humdAnterior = this.getTemperatura();
        super.setHumedad(humd);
        for (Obra o : obras){
            if(!o.compatibilidadObraSala(this)){
                this.setTemperatura(humdAnterior);
                return false;
            }
        }
        return true;
    }
}

