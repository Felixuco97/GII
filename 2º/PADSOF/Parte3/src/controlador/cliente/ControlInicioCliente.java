package controlador.cliente;

import java.awt.event.*;
import vista.*;
import vista.cliente.*;
import modelo.sistema.Sistema;
import modelo.usuarios.Cliente;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlInicioCliente implements ActionListener{
    private Sistema sistema;
    private Ventana frame;
    private InicioCliente vista;

    public ControlInicioCliente(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaInicioCliente();
    } 
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){

        if(e.getSource().equals(vista.getControladorComprar())){
            frame.mostrarPanel("ComprarEntrada");
        }
        else if(e.getSource().equals(vista.getControladorConsultarNotificaciones())){
            Cliente c = (Cliente)frame.getUsuarioActual();
            frame.getVistaNotificaciones().setNotificaciones(c.getNotificaciones());
            frame.mostrarPanel("Notificaciones");

        } else if(e.getSource().equals(vista.getControladorCerrarSesion())){
            sistema.cerrarSesion(frame.getUsuarioActual());
            frame.mostrarPanel("Inicial");  
        }
        else if(e.getSource().equals(vista.getControladorBuscar())){
            frame.mostrarPanel("Busqueda");  
        }
        vista.update();
    }
}
