package controlador;

import java.awt.event.*;
import java.util.ArrayList;


import modelo.centroExposiciones.CentroDeExposiciones;
import modelo.centroExposiciones.exposiciones.Exposicion;
import modelo.sistema.Sistema;
import vista.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlBusqueda implements ActionListener{

    private Ventana frame;
    private Busqueda vista;
    private Sistema sistema;
    private CentroDeExposiciones centro;

    public ControlBusqueda(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaBusqueda();
        this.centro = this.sistema.getCentro();
    }
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(vista.getControladorBuscar())){
            if (vista.getBusqueda().equals("")){
               vista.setResultados(centro.getExposiciones());
            }
            else {
                ArrayList<Exposicion> expos = centro.buscarExposiciones(vista.getBusqueda(), null, null);
                vista.setResultados(expos);
            }
        }
        else if(e.getSource().equals(vista.getControladorRegistrarse())){
            frame.mostrarPanel("Registro");
        }
        else if(e.getSource().equals(vista.getControladorAtras())){
            frame.mostrarPanelAnterior();
        }
        else if (e.getSource().equals(vista.getControladorFiltrar())){
            frame.mostrarPanel("Filtros");
        }
        vista.update();
    }

    //FALTA
}
