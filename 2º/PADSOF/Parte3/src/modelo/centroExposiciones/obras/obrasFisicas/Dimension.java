package modelo.centroExposiciones.obras.obrasFisicas;

import java.io.Serializable;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class Dimension implements Serializable{
    private double x, y, z;
    protected Dimension(){}
    public Dimension(double x, double y, double z){ //Preguntar si es enumerado
        this.x= x;
        this.y= y;
        this.z= z;
    }

    //Getters

    public double getX(){

        return this.x;
    }

    public double getY(){

        return this.y;
    }

    public double getZ(){

        return this.z;
    }

    //Setters
    public void setX(double x){

        this.x = x;
    }

    public void setY(double y){

        this.y = y;
    }

    public void setZ(double z){

        this.z = z;
    }

    @Override 
    public String toString(){
        return "X: "+ this.x + "Y: "+ this.x +  "Z: "+ this.x;
    }

}