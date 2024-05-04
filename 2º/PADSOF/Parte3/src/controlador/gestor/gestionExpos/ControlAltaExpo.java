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

public class ControlAltaExpo implements ActionListener{

    private Ventana frame;
    private AltaExpo vista;
    private Sistema sistema;
    private CentroDeExposiciones centro;
    private Temporal temp;
    private Descuento descuento;
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    /*CONTROLADOR SI SE SELECCIONA EL TIPO TEMPORAL */
    public ControlAltaExpo(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaAltaExpo();
        //cammbiamos al panel Permanente si seleccionamos la opcion Permanente
        this.vista.getTipoExposicion().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoSeleccionado = (String) vista.getTipoExposicion().getSelectedItem();
                if ("Permanente".equals(tipoSeleccionado)) {
                    frame.getVistaAltaExpo().getTipoExposicion().setSelectedIndex(1); 
                    frame.mostrarPanel("AltaExpoPermanente");

                }
            }
        });
    } 

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

        if(vista.getFechaFin().isBefore(LocalDate.now())){
            JOptionPane.showMessageDialog(vista,
                "Introduce una fecha de fin posterior a la fecha actual", "Error", JOptionPane.ERROR_MESSAGE);
                return;

        }

        if(vista.getFechaFin().isBefore(vista.getFechaInicio())){
            JOptionPane.showMessageDialog(vista,
                "Introduce una fecha de fin posterior a la fecha de inicio", "Error", JOptionPane.ERROR_MESSAGE);
                return;

        }

        if(vista.getFechaInicio().isAfter(vista.getFechaFin())){
            JOptionPane.showMessageDialog(vista,
                "Introduce una fecha de fin anterior a la fecha de fin", "Error", JOptionPane.ERROR_MESSAGE);
                return;

        }

        centro = sistema.getCentro();

        /*si la expo es temporal*/
        if ("Temporal".equals((String) vista.getTipoExposicion().getSelectedItem())) {

            /*Descuento*/
            if(vista.getDescuento().equals("Cliente Frecuente")){
                if (vista.getPorcentajeClienteFrecuente().equals("") && vista.getVisitas().equals("") && vista.getNumeroDias().equals("")){
                    descuento = null;
                }
                else{
                    if (vista.getPorcentajeClienteFrecuente().equals("") || vista.getVisitas().equals("") || vista.getNumeroDias().equals("")){
                        JOptionPane.showMessageDialog(vista,
                        "Faltan introducir datos del descuento", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
        
                    }

                    descuento = new DescuentoClienteFrecuente(Double.parseDouble(vista.getPorcentajeClienteFrecuente()), Integer.parseInt(vista.getVisitas()), Integer.parseInt(vista.getNumeroDias())); 

                }
                
            } else if(vista.getDescuento().equals("Compra anticipada")){
                if (vista.getPorcentajeClienteFrecuente().equals("") && vista.getVisitas().equals("") && vista.getDiasAntelacion().equals("")){
                    descuento = null;
                }
                else{
                    if (vista.getPorcentajeClienteFrecuente().equals("") || vista.getDiasAntelacion().equals("")){
                        JOptionPane.showMessageDialog(vista,
                        "Faltan introducir datos del descuento", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
        
                    }

                    descuento = new DescuentoCompraAnticipada(Double.parseDouble(vista.getPorcentajeCompraAnticipada()), Integer.parseInt(vista.getDiasAntelacion()));  

                }

                 
            }

            /*Estado*/
            EstadoExpo tipoEstado = EstadoExpo.fromString(vista.getEstado());


            temp = new Temporal(vista.getTitulo(), vista.getDescripcion(), vista.getFechaInicio(), vista.getNumEntradas(), vista.getFechaFin(), vista.getPrecio(), descuento, tipoEstado, null);


            centro.darDeAltaExposiciones(sistema, temp);


        } 
            JOptionPane.showMessageDialog(vista, "Exposicion Creada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            frame.mostrarPanelAnterior();
        } else if(e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }

}

