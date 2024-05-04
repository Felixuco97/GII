package vista.cliente;

import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;
import java.awt.event.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class InicioCliente extends JPanel {

    private JLabel titulo;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton buscar;

    public InicioCliente(Sistema sistema){

        this.setLayout(new GridLayout(0, 1));

        /*le ponemos una fuente y tamaño al label */
        titulo = new JLabel("     ¡ B I E N V E N I D O !", SwingConstants.CENTER);
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 24);
        titulo.setFont(font);

        button1 = new JButton("Comprar Entradas");
        button2 = new JButton("Consultar Notificaciones");
        button3 = new JButton("Cerrar Sesion");
        buscar = new JButton("Buscar Exposicion");

        this.add(titulo);
        this.add(button1);
        this.add(buscar);  
        this.add(button2);   
        this.add(button3);

    }

    public void setControlador(ActionListener c){
        button1.addActionListener(c);
        button2.addActionListener(c);
        button3.addActionListener(c);
        buscar.addActionListener(c);
    }

    public JButton getControladorComprar(){
        return  button1;
    }

    public JButton getControladorBuscar(){
        return  buscar;
    }

    public JButton getControladorConsultarNotificaciones(){
        return button2;
    }

    public JButton getControladorCerrarSesion(){
        return button3;
    }



    public void update(){
        button1.grabFocus();
    }
}
