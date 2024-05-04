package controlador.gestor.gestionSalas;

import java.awt.event.*;
import vista.*;
import vista.gestor.gestionSalas.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlInfo implements ActionListener{

    private Ventana frame;
    private Info vista;

    public ControlInfo(Ventana frame){
        this.frame = frame;
        this.vista = frame.getVistaInfo();
    }  

    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorAtras())){

        frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
