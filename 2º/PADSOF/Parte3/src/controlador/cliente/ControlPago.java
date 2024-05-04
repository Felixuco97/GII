package controlador.cliente;

import java.awt.event.*;
import java.util.ArrayList;

import vista.*;
import vista.cliente.*;
import modelo.sistema.Sistema;
import modelo.centroExposiciones.exposiciones.*;
import javax.swing.JOptionPane;
import modelo.centroExposiciones.Entrada;
import modelo.usuarios.Cliente;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
/*FALTAN ALGUNAS COSAS */
public class ControlPago implements ActionListener{

    private Ventana frame;
    private Pago vista;
    private Sistema sistema;
    private Exposicion expo;

    public ControlPago(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaPago();
    }  
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorComprar())){
            if(vista.getTitular().equals("") || vista.getNumero().equals("") || vista.getCvv().equals("")){
                JOptionPane.showMessageDialog(vista,"Faltan campos por rellenar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            /* ENTRADAS */            
            ArrayList<Entrada> entradas = ControlCompraEntradas.getEntradas();
            String codigoSorteo =  frame.getVistaCompraEntradas().getCodigo();


            Entrada[] entradasArray = new Entrada[entradas.size()];
            entradasArray = entradas.toArray(entradasArray);
            expo = sistema.getCentro().getExpositionByName(entradas.get(0).getExhibitionName());
            /*realizar el pago*/
            try{
                ((Cliente)frame.getUsuarioActual()).comprarEntradas(expo, vista.getNumero(), codigoSorteo, entradasArray[0].getFechaHora() ,entradasArray);
            }catch (Exception ex){
                JOptionPane.showMessageDialog(vista,
                "Error al realizar la compra de entradas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ControlCompraEntradas.limpiarEntradas();
            JOptionPane.showMessageDialog(vista, "Pago realizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            frame.mostrarPanel("InicioCliente");
        }

        else if(e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        vista.update();
    }
}
