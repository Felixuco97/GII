package controlador.empleado;

import java.awt.event.*;
import javax.swing.JOptionPane;

import vista.Ventana;
import vista.empleado.CambiarDatos;
import modelo.usuarios.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlCambiarDatos implements ActionListener{

    private Ventana frame;
    private CambiarDatos vista;

    public ControlCambiarDatos(Ventana frame){
        this.frame = frame;
        this.vista = frame.getVistaCambiarDatos();
    } 
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(vista.getControladorGuardar())){

            if(vista.getAccountNumber().equals("") && vista.getSecNumber().equals("") && vista.getDireccion().equals("")){
                JOptionPane.showMessageDialog(vista,"No hay ningun campo rellenado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //empleado
            Empleado empleado= (Empleado)frame.getUsuarioActual();
            //Actualizar datos
            if (!vista.getAccountNumber().equals("")){
                empleado.setNumCuenta(vista.getAccountNumber());
            }
            if (!vista.getSecNumber().equals("")){
                empleado.setNumSegSocial(vista.getSecNumber());
               
            }
            if (!vista.getDireccion().equals("")){
                empleado.setDireccion(vista.getDireccion());
            }
            
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(vista, "Datos actualizados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            frame.mostrarPanelAnterior();

        } else if(e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
    
}
