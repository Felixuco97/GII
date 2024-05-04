package controlador.gestor;

import java.awt.event.*;
import vista.*;
import vista.gestor.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class ControlOtrasOpciones implements ActionListener{

    private Ventana frame;
    private OtrasOpciones vista;

    public ControlOtrasOpciones(Ventana frame){
        this.frame = frame;
        this.vista = frame.getVistaOtrasOpciones();
    } 
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorSorteo())){
            frame.mostrarPanel("ConfigSort");
        }
        else if(e.getSource().equals(vista.getControladorDescuento())){
            frame.mostrarPanel("Descuento");
        }
        else {
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
