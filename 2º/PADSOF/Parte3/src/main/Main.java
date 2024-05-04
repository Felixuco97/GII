package main;

import controlador.*;
import modelo.sistema.*;
import vista.*;

public class Main {

    public static void main(String[] args){

        try{
            Sistema modelo = Sistema.iniciarSistema("resources/sistema.objectData");
            Ventana frame = new Ventana(modelo);
            Controlador controlador = new Controlador(frame, modelo);
            frame.setControlador(controlador);
            frame.mostrarPanel("Inicial");
            frame.setVisible(true);
        } catch (Exception e){

            e.printStackTrace();
        }
    }
}
