package controlador.gestor.gestionExpos;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import vista.*;
import vista.gestor.gestionExpos.Mapear;
import modelo.centroExposiciones.exposiciones.Exposicion;
import modelo.centroExposiciones.obras.Obra;
import modelo.centroExposiciones.salas.Exposala;
import modelo.sistema.*;

/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlMapear implements ActionListener{

    private Ventana frame;
    private Mapear vista;
    private Sistema sistema;


    public ControlMapear(Ventana frame, Sistema sistema){
        this.frame = frame;
        this.vista = frame.getVistaMapear();
        this.sistema = sistema;
    }  
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){

        if(e.getSource().equals(vista.getControladorConfirm())){

            /*obtenemos la expo seleccionada */
            String nombreExpo = vista.getExpoSeleccionada();
            Exposicion exposicionSeleccionada = sistema.getCentro().getExpositionByName(nombreExpo);

            /*obtenemos la obra seleccionada que queremos añadir a una sala */
            String nombreObra = vista.getObraSeleccionada();
            Obra obraSeleccionada = null;
            for (Obra obra : sistema.getCentro().getObras()) {
                if (obra.getTitulo().equals(nombreObra)) {
                    obraSeleccionada = obra;
                    break;
                }
            }

            /*obtenemos sala seleccionada perteneciente a esa exposicion */
            Exposala salaSeleccionada = null;
            String nombreSala = vista.getSalaSeleccionada();
            ArrayList<Exposala> salas = exposicionSeleccionada.getAllSalas();

            for(Exposala s : salas){
                if(s.getNombre().equals(nombreSala)){
                    salaSeleccionada = (Exposala) s;
                    break; 
                }
            }

            // Añadir la obra a la sala
            if (obraSeleccionada != null) {
                salaSeleccionada.addObras(obraSeleccionada);
                JOptionPane.showMessageDialog(vista, "Obra mapeada con exito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista,
                "No se pudo mapear la obra seleccionada", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            frame.mostrarPanelAnterior();
        } else if(e.getSource().equals(vista.getControladorAtras())){

            frame.mostrarPanelAnterior();
        }
    }
}
