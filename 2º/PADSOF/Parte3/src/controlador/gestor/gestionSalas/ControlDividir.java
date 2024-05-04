package controlador.gestor.gestionSalas;

import java.awt.event.*;
import javax.swing.*;
import vista.*;
import vista.gestor.gestionSalas.*;
import modelo.sistema.Sistema;
import modelo.centroExposiciones.salas.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
/*REVISARLO, ahora me da errores pq el modelo actual no esta actualizado con los cambios que hemos realizado */
public class ControlDividir implements ActionListener{

    private Ventana frame;
    private Dividir vista;
    private Sistema sistema;

    public ControlDividir(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaDividir();
    } 

    public void actionPerformed(ActionEvent e){



        if(e.getSource().equals(vista.getControladorConfirm())){


            if(vista.getAncho().equals("")){
    
            JOptionPane.showMessageDialog(vista, "Falta especificar el ancho", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            String salaSeleccionada =vista.getSalaSeleccionada();
            int anchoNuevaSala =Integer.parseInt(vista.getAncho());


            for (Sala sala : sistema.getCentro().getAllSalas()){
                if (sala.getNombre().equals(salaSeleccionada) && sala instanceof Exposala){
                    try{
                        //dividir (expo)sala
                        ((Exposala) sala).dividirSala(anchoNuevaSala);
                        JOptionPane.showMessageDialog(vista, "La sala ha sido dividida", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }catch (Exception ex) {
                        JOptionPane.showMessageDialog(vista, "No se ha podido dividir la sala", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } 
            }

            frame.mostrarPanelAnterior();

        }

        if(e.getSource().equals(vista.getControladorAtras())){

            frame.mostrarPanelAnterior();
        }
        vista.update();

    }
}
