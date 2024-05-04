package controlador.gestor.gestionObras;

import java.awt.event.*;
import javax.swing.*;
import vista.*;
import vista.gestor.gestionObras.*;
import modelo.centroExposiciones.obras.Obra;
import modelo.sistema.Sistema;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
/*FALTA */
public class ControlBajaObra implements ActionListener{

    private Ventana frame;
    private BajaObra vista;
    private Sistema sistema;

    public ControlBajaObra(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaBajaObra();
    }  
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){

        if(e.getSource().equals(vista.getControladorConfirm())){
            
            String obraSeleccionada = (String) vista.getObraSeleccionada();
            for(Obra o: sistema.getCentro().getObras()){ //se asume que el sistema contine la obra a eliminar debido a la implementacion de vista
                if(o.getTitulo().equals(obraSeleccionada)){
                    // o.setEstado(Estado.EN_ALMACEN);
                    o.moverToAlmacen();
                    JOptionPane.showMessageDialog(vista, "Obra dada de baja y movida al almacen correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }

            frame.mostrarPanelAnterior();

        } else if(e.getSource().equals(vista.getControladorAtras())){

            frame.mostrarPanelAnterior();
        }
        vista.update();       
    }
}
