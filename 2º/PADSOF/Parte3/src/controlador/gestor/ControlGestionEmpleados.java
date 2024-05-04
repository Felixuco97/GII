package controlador.gestor;

import java.awt.event.*;
import vista.*;
import vista.gestor.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class ControlGestionEmpleados implements ActionListener{

    private Ventana frame;
    private GestionDeEmpleados vista;

    public ControlGestionEmpleados(Ventana frame){
        this.frame = frame;
        this.vista = frame.getVistaGestionDeEmpleados();
    }  
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(vista.getControladorAltaEmpleado())){
            frame.mostrarPanel("AltaEmpleados");
        }
        else if (e.getSource().equals(vista.getControladorGestPermisos())){
            frame.mostrarPanel("GestionPermisosEmpl");
        }
        else if (e.getSource().equals(vista.getControladorGestContrasenia())){
            frame.mostrarPanel("GestionContrasenias");
        }
        else {
            frame.mostrarPanelAnterior();
        } 
        vista.update(); 
    }
}
