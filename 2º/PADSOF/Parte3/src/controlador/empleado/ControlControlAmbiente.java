package controlador.empleado;

import java.awt.event.*;

import javax.swing.JOptionPane;

import vista.*;
import vista.empleado.*;
import modelo.sistema.Sistema;
import modelo.centroExposiciones.salas.*;
import modelo.centroExposiciones.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
/*SE ACTUALIZA CORRECTAMENTE ??? */
public class ControlControlAmbiente implements ActionListener{

    private Ventana frame;
    private ControlAmbiente vista;
    private Sistema sistema;

    public ControlControlAmbiente(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaControlAmbiente();
    } 
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorConfirm())){
            
            Sala sala= null;
            CentroDeExposiciones centroExpo = sistema.getCentro();

            if (centroExpo != null){
                /*comprobamos cual es la sala que hemos seleccionado con las que hay en el centro de expo */
                for (Sala s : centroExpo.getSalas()){
                    if (s.getNombre().equals(vista.getSalaSeleccionada())){
                        sala= s;
                        break;
                    }
                }
            
            /*establecemos temperatura y humedad */
            if (sala != null){
                sala.setTemperatura(vista.getTemperatura());
                sala.setHumedad(vista.getHumedad());
                JOptionPane.showMessageDialog(vista, "Temperatura y humedad modificadas correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            }

            frame.mostrarPanelAnterior();

        }
        else if(e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    } 
}
}