package controlador.gestor;

import java.awt.event.*;

import vista.Ventana;
import vista.gestor.Estadistica;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlEstadistica implements ActionListener{
    
    private Ventana frame;
    private Estadistica vista;

    public ControlEstadistica(Ventana frame){
        this.frame = frame;
        this.vista = frame.getVistaEstadistica();
    }  
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorAtras()));
        frame.mostrarPanelAnterior();
        vista.update();
    }
    
}
