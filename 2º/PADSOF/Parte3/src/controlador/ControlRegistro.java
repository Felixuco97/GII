package controlador;

import java.awt.event.*;

import javax.swing.JOptionPane;

import vista.*;
import modelo.sistema.Sistema;
import modelo.usuarios.UsuarioRegistrado;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class ControlRegistro implements ActionListener{

    private Ventana frame;
    private Registro vista;
    private Sistema sistema;
    
    public ControlRegistro(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaRegistro(); 
    } 
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorConfirm())){
            if (vista.getNick().equals("") || vista.getNif().equals("") || vista.getContrasenia1().equals("") || vista.getContrasenia2().equals("")){
                JOptionPane.showMessageDialog(vista, "Faltan campos por rellenar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!vista.getContrasenia1().equals(vista.getContrasenia2())){
                JOptionPane.showMessageDialog(vista, "Las contrasenias no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //establecer preferencia de notificaciones
            boolean prefNot= false;
            if(vista.getOpcionRadioButton().equals("Si")){
                prefNot = true;
            }
            else{
                prefNot = false;
            }

            if (sistema.darDeAltaCliente(vista.getNick(), vista.getNif(), vista.getContrasenia1(), prefNot) == false){
                JOptionPane.showMessageDialog(vista, "Usuario no valido", "Error", JOptionPane.ERROR_MESSAGE);
                return;  
            }
            UsuarioRegistrado usr = sistema.iniciarSesion(vista.getNif(), vista.getContrasenia1());
            frame.setUsuarioActual(usr);
            
            frame.mostrarPanel(sistema.identificarUsuario(usr));
        }

        if (e.getSource().equals(vista.getControladorIniciarSesion())){
            frame.mostrarPanel("InicioSesion");
        }

        if (e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
