package controlador.gestor.gestionObras;

import java.awt.event.*;

import vista.*;
import vista.gestor.gestionObras.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlAltaObra implements ActionListener{

    private Ventana frame;
    private AltaObra vista;

    public ControlAltaObra(Ventana frame){
        this.frame = frame;
        this.vista = frame.getVistaAltaObra();
    } 
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorCuadro())){

            frame.mostrarPanel("AltaCuadro");
        } else if (e.getSource().equals(vista.getControladorEscultura())){

            frame.mostrarPanel("AltaEscultura");
        } else if(e.getSource().equals(vista.getControladorFoto())){

            frame.mostrarPanel("AltaFoto");
        } else if (e.getSource().equals(vista.getControladorMontajeAudioVisual())){

            frame.mostrarPanel("AltaMontaje");
        } else {

            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
