package controlador.gestor;

import java.awt.event.*;


import vista.*;
import vista.gestor.*;
import modelo.sistema.Sistema;

/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlInicioGestor implements ActionListener{

    private Ventana frame;
    private InicioGestor vista;
    private Sistema sistema;

    public ControlInicioGestor(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaInicioGestor();
    } 
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        /*Sorteo sorteo = Gestor.getFlagSorteo();
        if (sorteo != null){
            JButton realizarSorteoButton = new JButton("Realizar sorteo");
            realizarSorteoButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    sorteo.realizarSorteo(sorteo.getParticipaciones(), sorteo.getNumeroEntradas(), sistema.getClientes());
                }
            });

            Object[] options = {realizarSorteoButton};
            JOptionPane pane = new JOptionPane("¿Deseas realizar el sorteo?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options, options[0]);
            JDialog dialog = pane.createDialog("Sorteo");
            dialog.setVisible(true);
            Gestor.setFlagSorteo(null);
        }*/
        if (e.getSource().equals(vista.getControladorConfigHorario())){
            frame.mostrarPanel("ConfigHorario");
        }
        else if (e.getSource().equals(vista.getControladorGestExpo())){
            frame.mostrarPanel("GestionDeExposiciones");
        }
        else if (e.getSource().equals(vista.getControladorEstadistica())){
            frame.mostrarPanel("Estadistica");
        }
        else if (e.getSource().equals(vista.getControladorGestEmpleado())){
            frame.mostrarPanel("GestionDeEmpleados");
        }
        else if (e.getSource().equals(vista.getControladorGestObra())){
            frame.mostrarPanel("GestionDeObras");
        }
        else if (e.getSource().equals(vista.getControladorGestSala())){
            frame.mostrarPanel("GestionDeSalas");
        }
        else if (e.getSource().equals(vista.getControladorOtrasOpc())){
            frame.mostrarPanel("OtrasOpciones");
        }
        else if(e.getSource().equals(vista.getControladorCerrarSesion())){
            sistema.cerrarSesion(frame.getUsuarioActual());
            frame.mostrarPanel("Inicial");
        }
        vista.update();
    }
}
