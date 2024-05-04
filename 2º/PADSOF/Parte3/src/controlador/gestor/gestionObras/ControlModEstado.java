package controlador.gestor.gestionObras;

import java.awt.event.*;
import javax.swing.*;
import vista.*;
import vista.gestor.gestionObras.*;
import modelo.centroExposiciones.obras.Estado;
import modelo.centroExposiciones.obras.Obra;
import modelo.sistema.Sistema;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class ControlModEstado implements ActionListener{

    private Ventana frame;
    private ModEstado vista;
    private Sistema sistema;

    public ControlModEstado(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaModEstado();
    }  
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){

        if(e.getSource().equals(vista.getControladorConfirm())){

                String obraSeleccionada = (String) vista.getObraSeleccionada();
            for(Obra o: sistema.getCentro().getObras()){
                if(o.getTitulo().equals(obraSeleccionada)){
                    Estado nuevoEstado = Estado.fromString(vista.getEstadoSeleccionado());

                    o.setEstado(nuevoEstado);       //no deberia estar asi    

                    JOptionPane.showMessageDialog(vista, "Estado de obra modificado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
            frame.mostrarPanelAnterior();
        } else if(e.getSource().equals(vista.getControladorAtras())){  
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
