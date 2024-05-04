package vista;

import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */
public class Inicial extends JPanel{

    private JPanel panelSuperior;
    private JLabel titulo;
    // private JLabel imagen;


    private JPanel panelMedio;
    private JButton busqueda;
    private JButton registro;
    private JButton inicioSesion;

    /**
     * Constructor de la clase Inicial.
     * @param sistema Sistema que se utiliza en la interfaz inicial.
     */
    public Inicial(Sistema sistema) {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        titulo = new JLabel("E V E N T H U B");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        titulo.setFont(font);
        panelSuperior.add(titulo);

        // imagen = new JLabel(new ImageIcon("ruta/a/imagen.jpg")); // Cambia "ruta/a/imagen.jpg" por la ruta de tu imagen


        panelMedio = new JPanel(new GridLayout(0, 1));
        
        
        registro = new JButton("Registrarse");
        busqueda = new JButton("Busqueda");
        inicioSesion = new JButton("Iniciar Sesion");
        
        panelMedio.add(busqueda);
        panelMedio.add(registro);
        panelMedio.add(inicioSesion);

        this.add(panelSuperior);
        this.add(panelMedio);

        
    }

    public void setControlador(ActionListener c){
        busqueda.addActionListener(c);
        registro.addActionListener(c);
        inicioSesion.addActionListener(c);

    }

    public JButton getControladorBuscar(){
        return  busqueda;
    }

    public JButton getControladorRegistro(){
        return registro;
    }

    public JButton getControladorInicioSesion(){
        return inicioSesion;
    }
}
