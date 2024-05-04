package controlador.empleado;

import java.awt.event.*;
import vista.*;
import vista.empleado.*;
import modelo.sistema.Sistema;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlInicioEmpleado implements ActionListener{
    private Sistema sistema;
    private Ventana frame;
    private InicioEmpleado vista;

    public ControlInicioEmpleado(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaInicioEmpleado();
    } 
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(vista.getControladorVentaEntradas())){
            frame.mostrarPanel("VentaEntradas");
        }
        else if (e.getSource().equals(vista.getControladorNotificaciones())){
            frame.mostrarPanel("CrearNotis");
        }
        else if (e.getSource().equals(vista.getControladorTemperaturaHumedad())){
            frame.mostrarPanel("ControlAmbiente");
        }
        else if (e.getSource().equals(vista.getControladorCambiarDatos())){
            frame.mostrarPanel("CambiarDatos");
        }
        else if(e.getSource().equals(vista.getControladorCerrarSesion())) {
            sistema.cerrarSesion(frame.getUsuarioActual());
            frame.mostrarPanel("Inicial");
        }
        vista.update();
    }
}