package controlador.empleado;

import java.awt.event.*;
import vista.*;
import vista.empleado.*;
import javax.swing.JOptionPane;
import modelo.sistema.Sistema;
import modelo.usuarios.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class ControlCrearNotis implements ActionListener{
    
    private Ventana frame;
    private CrearNotis vista;
    private Sistema sistema;

    public ControlCrearNotis(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaCrearNotis();
    }  
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorEnviar())){
            if(vista.getCuerpo().equals("")){
                JOptionPane.showMessageDialog(vista,
                "Faltan campos por rellenar", "Error", JOptionPane.ERROR_MESSAGE);
                
                return;
            }

        /*Eviar notificacion a todos los clientes */
        TiposNotificacion tipo = TiposNotificacion.fromString(vista.getTipoNoti());
        Notificacion n = new Notificacion(tipo, vista.getCuerpo(), null, vista.getAsunto());
        sistema.enviarNotificaciones((Empleado)frame.getUsuarioActual(), n);

        JOptionPane.showMessageDialog(vista, "Mensaje enviado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        frame.mostrarPanelAnterior();


        } else if(e.getSource().equals(vista.getControladorDescartar())){
            /*se pregunta si está seguro de querer descartar el mensaje */
            int opcion = JOptionPane.showConfirmDialog(vista, "¿Estás seguro de descartar el mensaje?", "Confirmar descarte", JOptionPane.YES_NO_OPTION);
                        
            if (opcion == JOptionPane.YES_OPTION){
                // frame.mostrarPanel("InicioEmpleado");
                frame.mostrarPanelAnterior();

            } else {
                frame.mostrarPanel("CrearNotis");
            }
        }
        vista.update();
    }

}

