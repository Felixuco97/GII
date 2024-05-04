package controlador;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import modelo.centroExposiciones.CentroDeExposiciones;
import modelo.centroExposiciones.exposiciones.Exposicion;
import modelo.sistema.Sistema;
import vista.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlFiltros implements ActionListener{

    private Ventana frame;
    private FiltrosBusqueda vista;
    private Busqueda vistaBsq;
    private Sistema sistema;
    private CentroDeExposiciones centro;

    public ControlFiltros(Ventana frame, Sistema sistema){
        this.frame = frame;
        this.vista = frame.getVistaFiltros();
        this.vistaBsq = frame.getVistaBusqueda();
        this.sistema = sistema;
        this.centro = this.sistema.getCentro();
    }
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(vista.getControladorConfirm())){
            ArrayList<Exposicion> expos = centro.buscarExposiciones(null, vista.getFiltro(), vista.getFiltrado());
            vistaBsq.setResultados(expos); 

            JOptionPane.showMessageDialog(vista, "Filtro configurado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            frame.mostrarPanelAnterior();
        }
        else if(e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}