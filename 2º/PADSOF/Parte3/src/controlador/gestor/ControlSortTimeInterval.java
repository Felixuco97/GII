package controlador.gestor;


import java.awt.event.*;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import modelo.centroExposiciones.CentroDeExposiciones;
import modelo.centroExposiciones.exposiciones.Exposicion;
import modelo.sistema.Sistema;
import modelo.centroExposiciones.sorteos.*;
import vista.*;
import vista.gestor.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlSortTimeInterval implements ActionListener{

    private Ventana frame;
    private Sistema sistema;
    private SortTimeInterval vista;
    private CentroDeExposiciones centro;

    public ControlSortTimeInterval(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaSortTimeInterval();
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
            
            if (vista.getFechaFin().equals(null) ||  vista.getFechaIncio().equals(null) || vista.getHoraIncio().equals(null) || vista.getHoraFin().equals(null) || vista.getPenalizacion().equals("") || vista.getFechaFinIntervalo().equals(null) || vista.getFechaIncioIntervalo().equals(null) || vista.getHoraIncioIntervalo().equals(null) || vista.getHoraFinIntervalo().equals(null)){
                JOptionPane.showMessageDialog(vista, "Faltan campos por rellenar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (vista.getFechaFin().isBefore(LocalDate.now()) ||  vista.getFechaIncio().isAfter(vista.getFechaFin()) || vista.getFechaFinIntervalo().isBefore(LocalDate.now()) ||  vista.getFechaIncioIntervalo().isAfter(vista.getFechaFin()) ||  vista.getNumGanadores() < 1 || vista.getNumEntradas() < 1  || vista.getNumEntradas() < vista.getNumGanadores()){
                JOptionPane.showMessageDialog(vista, "Los datos son incompatibles, compruebalos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Exposicion exp = centro.getExpositionByName(vista.getExposicion());
            SorteoIntervalo sort = new SorteoIntervalo(vista.getNumEntradas(), vista.getNumGanadores(), vista.getFechaIncio().atTime(vista.getHoraIncio()), vista.getFechaFin().atTime(vista.getHoraFin()), Integer.parseInt(vista.getPenalizacion()), exp, vista.getFechaIncioIntervalo().atTime(vista.getHoraIncioIntervalo()),vista.getFechaFinIntervalo().atTime(vista.getHoraFinIntervalo()));
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