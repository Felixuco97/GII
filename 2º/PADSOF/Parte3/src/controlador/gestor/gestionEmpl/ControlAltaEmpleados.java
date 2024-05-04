package controlador.gestor.gestionEmpl;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import vista.*;
import vista.gestor.gestionEmpl.AltaEmpleados;
import modelo.sistema.Sistema;
import modelo.usuarios.Permiso;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class ControlAltaEmpleados implements ActionListener{

    private Ventana frame;
    private AltaEmpleados vista;
    private Sistema sistema;

    public ControlAltaEmpleados(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaAltaEmpleados();
    }  
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){

    if(e.getSource().equals(vista.getControladorConfirm())){

        if(vista.getNif().equals("") || vista.getClave().equals("") || vista.getNombre().equals("") || vista.getNumCuenta().equals("") || vista.getNumSegSocial().equals("") || vista.getDireccion().equals("")){
            JOptionPane.showMessageDialog(vista,
            "Faltan campos por rellenar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //permisos
        ArrayList<Permiso> permisos = new ArrayList<>();
        ArrayList<JRadioButton> radioButtons = vista.getRadioButtons();
        if (radioButtons.get(0).isSelected()) {
            permisos.add(Permiso.VENDER_ENTRADAS);
        }
        if (radioButtons.get(1).isSelected()) {
            permisos.add(Permiso.MODIFICAR_TH);
        }
        if (radioButtons.get(2).isSelected()) {
            permisos.add(Permiso.NOTIFICAR);
        }

        sistema.darDeAltaEmpleado(vista.getNombre(), vista.getNif(), vista.getClave(), vista.getNumSegSocial(), vista.getNumSegSocial(), vista.getDireccion(), permisos);
        JOptionPane.showMessageDialog(vista, "Empleado dado de alta correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        frame.mostrarPanelAnterior();

        } else if (e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
