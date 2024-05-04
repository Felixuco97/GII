package controlador.gestor;

import java.awt.event.*;
import javax.swing.JOptionPane;

import modelo.sistema.Sistema;
import vista.Ventana;
import vista.gestor.ConfigHorario;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlConfigHorario implements ActionListener{
    
    private Ventana frame;
    private ConfigHorario vista;
    private Sistema sistema;

    public ControlConfigHorario(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaConfigHorario();
    }  
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorGuardar())){

            if(vista.getHoraApertura().isAfter(vista.getHoraCierre())){
                JOptionPane.showMessageDialog(vista,
                "La hora de apertura debe ser anterior a la hora de cierre", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(vista.getHoraCierre().isBefore(vista.getHoraApertura())){
                JOptionPane.showMessageDialog(vista,
                "La hora de cierre debe ser posterior a la hora de apertura", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            sistema.getCentro().configurarHorario(vista.getHoraApertura(), vista.getHoraCierre());
            JOptionPane.showMessageDialog(vista, "Horario configurado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            frame.mostrarPanelAnterior();


        }else if(e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
