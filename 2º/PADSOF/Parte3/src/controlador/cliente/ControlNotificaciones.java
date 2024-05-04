package controlador.cliente;

import java.awt.event.*;

import modelo.usuarios.Cliente;
import vista.*;
import vista.cliente.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlNotificaciones implements ActionListener{
    private Ventana frame;
    private Notificaciones vista;

    public ControlNotificaciones(Ventana frame){
        this.frame = frame;
        this.vista = frame.getVistaNotificaciones();
    }  
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        Cliente c = (Cliente)frame.getUsuarioActual();
        vista.setNotificaciones(c.getNotificaciones());
        vista.update();
    }
}
