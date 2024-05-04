package controlador.gestor;


import java.awt.event.*;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import modelo.sistema.Sistema;
import modelo.centroExposiciones.CentroDeExposiciones;
import modelo.centroExposiciones.exposiciones.Exposicion;
import modelo.centroExposiciones.sorteos.*;
import vista.*;
import vista.gestor.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlSortFreeDate implements ActionListener{

    private Ventana frame;
    private Sistema sistema;
    private SortFreeDate vista;
    private CentroDeExposiciones centro;

    public ControlSortFreeDate(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaSortFreeDate();
        this.centro = this.sistema.getCentro();
    }
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(vista.getControladorConfirm())){
            if (vista.getFechaFin().equals(null) ||  vista.getFechaIncio().equals(null) || vista.getHoraIncio().equals(null) || vista.getHoraFin().equals(null) || vista.getPenalizacion().equals("")){
                JOptionPane.showMessageDialog(vista, "Faltan campos por rellenar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (vista.getFechaFin().isBefore(LocalDate.now()) ||  vista.getFechaIncio().isAfter(vista.getFechaFin()) ||  vista.getNumGanadores() < 1 || vista.getNumEntradas() < 1  || vista.getNumEntradas() < vista.getNumGanadores()){
                JOptionPane.showMessageDialog(vista, "Los datos son incompatibles, compruebalos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Exposicion exp = centro.getExpositionByName(vista.getExposicion());
            SorteoFechaLibre sort = new SorteoFechaLibre(vista.getNumEntradas(), vista.getNumGanadores(), vista.getFechaIncio().atTime(vista.getHoraIncio()), vista.getFechaFin().atTime(vista.getHoraFin()), Integer.parseInt(vista.getPenalizacion()), exp);
            centro.darDeAltaSorteo(sort);
            JOptionPane.showMessageDialog(vista, "El sorteo se creo con exito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            frame.mostrarPanelAnterior();
        }
        else if(e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}