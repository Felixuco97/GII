package modelo.centroExposiciones.exposiciones;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Map;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

import java.util.HashMap;

public class Estadisticas implements Serializable{

    // private int numEntradasVendidas;
    // private double beneficios;
    private Map<DayOfWeek, Integer> numEntradasVendidas = new HashMap<>();
    private Map<DayOfWeek, Double> beneficios = new HashMap<>();
    private String exposicion;
    protected Estadisticas(){}
    public Estadisticas(String exposicion){
        for (DayOfWeek day : DayOfWeek.values()) {
            this.numEntradasVendidas.put(day, 0);
            this.beneficios.put(day, 0.0);
        }
        // this.numEntradasVendidas = numEntradasVendidas;
        // this.beneficios = beneficios;
        this.exposicion = exposicion;
    }

    //Getters

    public int getNumEntradasVendidas_dia(DayOfWeek d){
        return this.numEntradasVendidas.get(d);
    }

    public double getBeneficios_dia(DayOfWeek d){

        return this.beneficios.get(d);
    }

    public double getBeneficios(){
        double value = 0;
        for (Double beneficio : this.beneficios.values()) {
            
            value += beneficio;
        }
        return value;
    }

    public int getNumEntradasVendidas(){

        int value = 0;
        for (Integer beneficio : this.numEntradasVendidas.values()) {
            
            value += beneficio;
        }
        return value;
    }

    public String getExposicion(){

        return this.exposicion;
    }

    //Setters

    public void setNumEntradasVendidas(DayOfWeek d,int n){

        this.numEntradasVendidas.put(d, n);
    }

    public void setBeneficios(DayOfWeek d ,double n){

        this.beneficios.put(d, n);
    }
}