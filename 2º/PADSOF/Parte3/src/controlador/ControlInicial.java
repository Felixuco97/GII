package controlador;

import java.awt.event.*;


import vista.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlInicial implements ActionListener{
    private Ventana frame;
    private Inicial vista;

    public ControlInicial(Ventana frame){
        this.frame = frame;
        this.vista = frame.getVistaInicial();
    }
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){

        //BOTON CONFIRMAR
        if(e.getSource().equals(vista.getControladorBuscar())){
            frame.mostrarPanel("Busqueda");

        }else if(e.getSource().equals(vista.getControladorRegistro())){

            frame.mostrarPanel("Registro");

        } else if(e.getSource().equals(vista.getControladorInicioSesion())){

            frame.mostrarPanel("InicioSesion");

        }
    } 
}
