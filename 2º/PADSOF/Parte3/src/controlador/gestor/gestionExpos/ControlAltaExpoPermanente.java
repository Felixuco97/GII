
package controlador.gestor.gestionExpos;

import java.awt.event.*;
import vista.*;
import vista.gestor.gestionExpos.*;
import modelo.sistema.Sistema;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import modelo.centroExposiciones.*;
import modelo.centroExposiciones.exposiciones.*;

/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

/*REVISARRR */
public class ControlAltaExpoPermanente implements ActionListener{

    private Ventana frame;
    private AltaExpoPermanente vista;
    private Sistema sistema;
    private CentroDeExposiciones centro;
    private Permanente perm;
    private Descuento descuento;

    public ControlAltaExpoPermanente(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaAltaExpoPermanente();
        this.vista.getTipoExposicion().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoSeleccionado = (String) vista.getTipoExposicion().getSelectedItem();
                if ("Temporal".equals(tipoSeleccionado)) {
                    frame.getVistaAltaExpo().getTipoExposicion().setSelectedIndex(0); 
                    frame.mostrarPanel("AltaExpo");


                }
            }
        });
    } 
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorConfirm())){
            if(vista.getDescuento().equals("") || vista.getDescripcion().equals("")){
                JOptionPane.showMessageDialog(vista,
                "Faltan campos por rellenar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
        if(vista.getFechaInicio().isBefore(LocalDate.now())){
            JOptionPane.showMessageDialog(vista,
                "Introduce una fecha de inicio posterior a la fecha actual", "Error", JOptionPane.ERROR_MESSAGE);
                return;

        }


        centro = sistema.getCentro();

        if ("Permanente".equals((String) vista.getTipoExposicion().getSelectedItem())){

            /*Descuento*/
            if(vista.getDescuento().equals("Cliente Frecuente")){
                
                descuento = new DescuentoClienteFrecuente(vista.getPorcentajeClienteFrecuente(), vista.getVisitas(), vista.getNumeroDias()); 

            } else if(vista.getDescuento().equals("Compra anticipada")){

                descuento = new DescuentoCompraAnticipada(vista.getPorcentajeCompraAnticipada(), vista.getDiasAntelacion());  
            }

            /*Estado*/
            EstadoExpo tipoEstado = EstadoExpo.fromString(vista.getEstado());

            perm = new Permanente(vista.getTitulo(), vista.getDescripcion(), vista.getFechaInicio(), vista.getNumEntradas(), vista.getPrecio(), descuento, tipoEstado, null);
            centro.darDeAltaExposiciones(sistema, perm);

        }
            JOptionPane.showMessageDialog(vista, "Exposicion Creada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            frame.mostrarPanel("GestionDeExposiciones");
        } else if(e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    } 
}

