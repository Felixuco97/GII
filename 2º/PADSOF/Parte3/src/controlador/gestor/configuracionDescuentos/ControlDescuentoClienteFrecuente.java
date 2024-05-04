package controlador.gestor.configuracionDescuentos;

import java.awt.event.*;

import javax.swing.JOptionPane;

import vista.*;
import modelo.centroExposiciones.CentroDeExposiciones;
import modelo.centroExposiciones.exposiciones.DescuentoClienteFrecuente;
import modelo.centroExposiciones.exposiciones.Exposicion;
import modelo.sistema.Sistema;
import vista.gestor.configuracionDescuentos.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class ControlDescuentoClienteFrecuente implements ActionListener{

    private Ventana frame;
    private DescClienteFrecuente vista;
    private Sistema sistema;
    private CentroDeExposiciones centro;

    /*Los valores que toma cada descuento lo especificamos aqui? */
    public ControlDescuentoClienteFrecuente(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaDescuentoClienteFrecuente();
        this.centro = this.sistema.getCentro();
    } 
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(vista.getControladorConfirm())){

            if (vista.getPorcentaje() == -1.0 || vista.getNumVisitas() == -1 || vista.getNumDias() == -1){
            JOptionPane.showMessageDialog(vista,
                "Faltan campos por rellenar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
            }

            Exposicion expo = centro.getExpositionByName(vista.getExposicion());
            DescuentoClienteFrecuente desc = new DescuentoClienteFrecuente(vista.getPorcentaje(), vista.getNumVisitas(), vista.getNumDias()); 
            expo.setDescuento(desc);
            JOptionPane.showMessageDialog(vista, "Descuento configurado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            frame.mostrarPanelAnterior();
        }
        else if(e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
