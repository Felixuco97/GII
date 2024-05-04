package controlador.gestor;

import java.awt.event.*;
import vista.*;
import vista.gestor.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlGestionObras implements ActionListener{

    private Ventana frame;
    private GestionDeObras vista;

    public ControlGestionObras(Ventana frame){
        this.frame = frame;
        this.vista = frame.getVistaGestionDeObras();
    } 
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(vista.getControladorAltaObra())){
            frame.mostrarPanel("AltaObra");
        }
        else if (e.getSource().equals(vista.getControladorBajaObra())){
            frame.mostrarPanel("BajaObra");
        }
        else if (e.getSource().equals(vista.getControladorObraCambEstd())){
            frame.mostrarPanel("ModEstado");
        }
        else {
            frame.mostrarPanelAnterior();
        }
        vista.update();
    } 
}
