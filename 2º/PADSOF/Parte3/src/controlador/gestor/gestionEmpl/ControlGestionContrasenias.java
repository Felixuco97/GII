package controlador.gestor.gestionEmpl;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import modelo.sistema.Sistema;
import modelo.usuarios.Empleado;
import vista.Ventana;
import vista.gestor.gestionEmpl.GestionContrasenias;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
/*REVISARLO   */
public class ControlGestionContrasenias implements ActionListener{
    
    private Ventana frame;
    private GestionContrasenias vista;
    private Sistema sistema;

    public ControlGestionContrasenias(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaGestionContrasenias();
    }  
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){


        if(e.getSource().equals(vista.getControladorGuardar())){

            if(vista.getContraseniaNueva().equals("")){
                JOptionPane.showMessageDialog(vista, "Hay que poner una contraseña", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
           ArrayList<Empleado> empleados = sistema.getEmpleadosRegistrados();

            //modificar las contraseñas de los empleados seleccionados
            for (Empleado emp : empleados){
                sistema.modificarContrasenias(vista.getContraseniaNueva(), emp);
            }
            
            JOptionPane.showMessageDialog(vista, "Contrasenia modificada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            frame.mostrarPanelAnterior();


        } else if(e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
