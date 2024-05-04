package controlador.gestor.gestionObras;

import java.awt.event.*;

import javax.swing.JOptionPane;

import vista.*;
import vista.gestor.gestionObras.*;
import modelo.sistema.Sistema;
import modelo.centroExposiciones.CentroDeExposiciones;
import modelo.centroExposiciones.obras.*;
import modelo.centroExposiciones.obras.obrasFisicas.Cuadro;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class ControlAltaCuadro implements ActionListener{

    private Ventana frame;
    private AltaCuadro vista;
    private Sistema sistema;
    private Cuadro cuadro;

    private CentroDeExposiciones centro; 

    public ControlAltaCuadro(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaAltaCuadro();
    } 
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorConfirm())){

            if(vista.getTitulo().equals("") || vista.getDuenio().equals("") || vista.getDescripcion().equals("") ||  vista.getSeguro().equals(null) || vista.getDuenio().equals("") || vista.getRangoTemperatura().equals(null) || vista.getRangoHumedad().equals(null) || vista.getDimensiones().equals(null) || vista.getTecnica().equals("")){
                JOptionPane.showMessageDialog(vista, "Faltan campos por rellenar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            centro = sistema.getCentro();

            Estado tipoEstado = Estado.fromString(vista.getEstado());

            cuadro = new Cuadro(vista.getAutor(), vista.getTitulo(), vista.getAnio(), vista.getDescripcion(), vista.getSeguro(), vista.getDimensiones(), vista.getRangoTemperatura(), vista.getRangoHumedad(), vista.getDuenio(), tipoEstado, vista.getTecnica());

            centro.darDeAltaObras(cuadro);

        } else if (e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
