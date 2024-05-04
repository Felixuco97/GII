package controlador.gestor;

import java.awt.event.*;


import vista.*;
import vista.gestor.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class ControlConfigSort implements ActionListener{

    private Ventana frame;
    private ConfigSort vista;


    /*FALTA, AHORA TENER EN CUENTA QUE LA EXPO NO TIENE SORTEO */
    public ControlConfigSort(Ventana frame){
        this.frame = frame;
        this.vista = frame.getVistaConfigSort();
    }   
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(vista.getControladorSorteoFechaFija())){
            frame.mostrarPanel("SortFechaFija");
        }
        else if(e.getSource().equals(vista.getControladorSorteoFechaLibre())){
            frame.mostrarPanel("SortFreeDate");
        }
        else if(e.getSource().equals(vista.getControladorSorteoTimeInter())){
            frame.mostrarPanel("SortTimeInterval");
        }
        else if(e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
