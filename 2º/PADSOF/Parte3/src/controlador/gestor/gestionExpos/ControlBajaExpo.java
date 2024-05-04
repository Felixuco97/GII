package controlador.gestor.gestionExpos;

import java.awt.event.*;

import javax.swing.JOptionPane;

import vista.*;
import vista.gestor.gestionExpos.*;
import modelo.sistema.Sistema;
import modelo.centroExposiciones.exposiciones.*;
import modelo.centroExposiciones.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlBajaExpo implements ActionListener{
    
    private Ventana frame;
    private BajaExpo vista;
    private Sistema sistema;
    private CentroDeExposiciones centro;

    public ControlBajaExpo(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaBajaExpo();
        this.centro = this.sistema.getCentro();
    } 

    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorConfirm())){
            
            boolean bool = false;
            for(Temporal expo : centro.getExposTemporales()){ 
                if(expo.getNombre().equals(vista.getExposicion())){
                    try{
                        centro.eliminarExposiciones(expo);//dar de baja == eliminar 
                    } catch (Exception excp){
                        excp.printStackTrace();
                    }
                    bool = true;
                }
            }

            if(bool == false) {
                JOptionPane.showMessageDialog(vista, "No se encontro la exposicion", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(vista, "Exposicion eliminada con exito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            frame.mostrarPanelAnterior();

        } else if (e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
