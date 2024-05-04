package controlador.cliente;

import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import vista.*;
import vista.cliente.*;
import modelo.sistema.Sistema;
import modelo.usuarios.Cliente;
import modelo.centroExposiciones.*;
import modelo.centroExposiciones.exposiciones.Descuento;
import modelo.centroExposiciones.exposiciones.Exposicion;
import java.util.Random;


import javax.swing.JOptionPane;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlCompraEntradas implements ActionListener{

    private Ventana frame;
    private ComprarEntrada vista;
    private Sistema sistema;
    private CentroDeExposiciones centro;
    private static ArrayList<Entrada> entradas = new ArrayList<>();

    public ControlCompraEntradas(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaCompraEntradas();
        this.centro = sistema.getCentro();
    }  
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(vista.getControladorSiguiente())){
           
            /*comprobar hora*/
            if(vista.getHoraSeleccionada().isBefore(centro.getHoraApertura()) || vista.getHoraSeleccionada().isAfter(centro.getHoraCierre())){
                JOptionPane.showMessageDialog(vista, "Introduce una hora compatible con el horario del centro", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (vista.getFecha() == null){
                JOptionPane.showMessageDialog(vista,
                "Introduce una fecha", "Error", JOptionPane.ERROR_MESSAGE);
                return ;
            }
            else {
                if (vista.getFecha().isBefore(LocalDate.now())){
                    JOptionPane.showMessageDialog(vista,
                    "No se pueden comprar entradas para fechas pasadas", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
    
            String nombreExpo = vista.getExpoSeleccionada();
            Exposicion expo = sistema.getCentro().getExpositionByName(nombreExpo);  // no puede ser null, porque es un combobox
            LocalDateTime fechaHora = vista.getFecha().atTime(vista.getHoraSeleccionada());
            int disp =  expo.getNumEntradas() - expo.calcularDisponibilidadEntradas(fechaHora);
            if (disp <= 0){ 
                JOptionPane.showMessageDialog(vista,
                "Solo hay " + disp + " entradas disponibles", "Error", JOptionPane.ERROR_MESSAGE);
            }

            for (int i= 0; i < vista.getNumEntradas(); i++){  
                Descuento desc = expo.getDescuento();
                Random rand = new Random();
                double porc = desc == null ? 0.0 : desc.getPorcentaje();
                this.addEntrada(new Entrada("EventHub", nombreExpo, expo.getNumEntradas(),vista.getFecha().atTime(vista.getHoraSeleccionada()), expo.getPrecioEntradas(),porc, expo.getPrecioEntradas(),  "", Integer.toString(rand.nextInt(100000)), (Cliente)frame.getUsuarioActual()));
            }  

            frame.mostrarPanel("Pago");
            vista.update();
                
        } else if(e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
            vista.update();
        }
            
    }
    /**
     * Agrega una entrada al conjunto de entradas.
     *
     * @param e La entrada a agregar.
     */
    private void addEntrada(Entrada e){
        entradas.add(e);
    }
    /**
     * Obtiene todas las entradas almacenadas.
     *
     * @return Un ArrayList que contiene todas las entradas almacenadas.
     */
    public static ArrayList<Entrada> getEntradas(){
        ArrayList<Entrada> out = entradas;
        return out;
    }
    /**
     * Elimina todas las entradas almacenadas, dejando el conjunto de entradas vacío.
     */
    public static void limpiarEntradas(){
        entradas.clear();
    }
}
