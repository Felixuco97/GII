package controlador.gestor.gestionExpos;

import java.awt.event.*;

import javax.swing.JOptionPane;

import vista.*;
import vista.gestor.gestionExpos.*;
import modelo.centroExposiciones.CentroDeExposiciones;
import modelo.centroExposiciones.exposiciones.EstadoExpo;
import modelo.centroExposiciones.exposiciones.Exposicion;
import modelo.sistema.Sistema;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class ControlCancelar implements ActionListener{

    private Ventana frame;
    private Cancelar vista;
    private Sistema sistema;

    public ControlCancelar(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaCancelar();
    }
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){

        boolean flag = false;
         if(e.getSource().equals(vista.getControladorConfirm())){
            CentroDeExposiciones centroExpo = sistema.getCentro();
            for(Exposicion expo : centroExpo.getExposiciones()){
                if(expo.getNombre().equals(vista.getExposicion())){
                    expo.setEstado(EstadoExpo.CANCELADA);
                    flag = true;
                }
            }
            if(flag == false) {
                JOptionPane.showMessageDialog(vista,
                "No se encontro la exposicion", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(vista, "Exposicion cancelada con exito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            frame.mostrarPanelAnterior();

        } else if (e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();

        }
        vista.update();        
    }
}
