package controlador.gestor.gestionObras;

import java.awt.event.*;

import javax.swing.JOptionPane;

import vista.*;
import vista.gestor.gestionObras.*;
import modelo.sistema.Sistema;
import modelo.centroExposiciones.CentroDeExposiciones;
import modelo.centroExposiciones.obras.*;

/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class ControlAltaMontaje implements ActionListener{

    private Ventana frame;
    private AltaMontaje vista;
    private Sistema sistema;
    private MontajeAudiovisual montaje;
    private CentroDeExposiciones centro; 

    public ControlAltaMontaje(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaAltaMontaje();
    } 
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorConfirm())){

            if(vista.getTitulo().equals("") || vista.getDuenio().equals("") || vista.getDescripcion().equals("") ||  vista.getSeguro().equals(null) || vista.getAutor().equals("")){
                JOptionPane.showMessageDialog(vista,
                "Faltan campos por rellenar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        centro = sistema.getCentro();
        Estado tipoEstado = Estado.fromString(vista.getEstado());
        montaje = new MontajeAudiovisual(vista.getAutor(), vista.getTitulo(), vista.getAnio(), vista.getDescripcion(), vista.getSeguro(), vista.getDuenio(), tipoEstado, vista.getIdioma(), vista.getDuracion());
        centro.darDeAltaObras(montaje);

        } else if (e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
