package controlador;

import java.awt.event.*;

import javax.swing.JOptionPane;

import modelo.sistema.Sistema;
import modelo.usuarios.UsuarioRegistrado;
import vista.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlInicioSesion implements ActionListener{

    private Ventana frame;
    private InicioSesion vista;
    private Sistema sistema;

    public ControlInicioSesion(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaInicioSesion();
    }
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){

        //BOTON CONFIRMAR
        if(e.getSource().equals(vista.getControladorConfirm())){
            if(vista.getUsuario().equals("") || vista.getContrasenia().equals("")){
                JOptionPane.showMessageDialog(vista,
                "Faltan campos por rellenar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            UsuarioRegistrado usr = sistema.iniciarSesion(vista.getUsuario(), vista.getContrasenia());
            if (usr == null){
                JOptionPane.showMessageDialog(vista,
                "El usuario o la contrasenia, son incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            frame.setUsuarioActual(usr);
        //modificar modelo


        //cambiar vista con cardLayout
            frame.mostrarPanel(sistema.identificarUsuario(usr));



        //BOTON ATRAS
        }else if(e.getSource().equals(vista.getControladorAtras())){

            frame.mostrarPanel("Inicial");

        }
        vista.update();
    } 
}
