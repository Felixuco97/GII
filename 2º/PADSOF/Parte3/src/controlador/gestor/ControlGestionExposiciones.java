package controlador.gestor;

import java.awt.event.*;
import vista.*;
import vista.gestor.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class ControlGestionExposiciones implements ActionListener{

    private Ventana frame;
    private GestionDeExposiciones vista;

    public ControlGestionExposiciones(Ventana frame){
        this.frame = frame;
        this.vista = frame.getVistaGestionDeExposiciones();
    }  
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(vista.getControladorAltaExpo())){
            frame.mostrarPanel("AltaExpo");
        }
        else if (e.getSource().equals(vista.getControladorBajaExpo())){
            frame.mostrarPanel("BajaExpo");
        }
        else if (e.getSource().equals(vista.getControladorCancelarExpo())){
            frame.mostrarPanel("Cancelar");
        }
        else if (e.getSource().equals(vista.getControladorMapearObra())){
            frame.mostrarPanel("Mapear");
        }
        else {
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
