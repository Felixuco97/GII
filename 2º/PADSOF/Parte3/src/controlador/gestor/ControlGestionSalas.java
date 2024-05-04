package controlador.gestor;

import java.awt.event.*;


import vista.*;
import vista.gestor.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class ControlGestionSalas implements ActionListener{

    private Ventana frame;
    private GestionDeSalas vista;

    public ControlGestionSalas(Ventana frame){
        this.frame = frame;
        this.vista = frame.getVistaGestionDeSalas();
    }  
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(vista.getControladorInfoSalas())){
            frame.mostrarPanel("Info");
        }
        else if (e.getSource().equals(vista.getControladorDivSalas())){
            frame.mostrarPanel("Dividir");
        }
        else {
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
