package controlador.gestor.gestionObras;

import java.awt.event.*;

import javax.swing.JOptionPane;

import vista.*;
import vista.gestor.gestionObras.*;
import modelo.sistema.Sistema;
import modelo.centroExposiciones.CentroDeExposiciones;
import modelo.centroExposiciones.obras.*;
import modelo.centroExposiciones.obras.obrasFisicas.Disenio;
import modelo.centroExposiciones.obras.obrasFisicas.Fotografia;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class ControlAltaFoto implements ActionListener{

    private Ventana frame;
    private AltaFoto vista;
    private Sistema sistema;
    private Fotografia foto;
    private CentroDeExposiciones centro; 

    public ControlAltaFoto(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaAltaFoto();
    } 
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorConfirm())){

            if(vista.getTitulo().equals("") || vista.getDuenio().equals("") || vista.getDescripcion().equals("") ||  vista.getSeguro().equals("")){
                JOptionPane.showMessageDialog(vista,
                "Faltan campos por rellenar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        centro = sistema.getCentro();
        Estado tipoEstado = Estado.fromString(vista.getEstado());

        foto = new Fotografia(vista.getAutor(), vista.getTitulo(), vista.getAnio(), vista.getDescripcion(), vista.getSeguro(), vista.getDimensiones(), vista.getRangoTemperatura(), vista.getRangoHumedad(), vista.getDuenio(), tipoEstado, Disenio.fromString(vista.getDisenio()));

        centro.darDeAltaObras(foto);

        } else if (e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
