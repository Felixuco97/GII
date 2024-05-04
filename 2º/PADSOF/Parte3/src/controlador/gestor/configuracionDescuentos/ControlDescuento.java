package controlador.gestor.configuracionDescuentos;

import java.awt.event.*;
import vista.*;
import vista.gestor.configuracionDescuentos.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlDescuento implements ActionListener{

    private Ventana frame;
    private Desc vista;

    /*Los valores que toma cada descuento lo especificamos aqui? */
    public ControlDescuento(Ventana frame){;
        this.frame = frame;
        this.vista = frame.getVistaDescuento();
    } 
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(vista.getControladorDescuentoAnticipada())){

            frame.mostrarPanel("DescuentoAnticipada");
        }
        else if(e.getSource().equals(vista.getControladorDescuentoFrecuente())){
        frame.mostrarPanel("DescuentoFrecuente");
        }
        else if(e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
