package vista.empleado;

import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;
import java.awt.event.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class InicioEmpleado extends JPanel{
    private JLabel label1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;

    public InicioEmpleado(Sistema sistema){

        this.setLayout(new GridLayout(0, 1));

        /*le ponemos una fuente y tamaño al label */
        label1 = new JLabel("     ¡ B I E N V E N I D O !", SwingConstants.CENTER);
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 24);
        label1.setFont(font);

        button1 = new JButton("Venta de entradas");
        button2 = new JButton("Crear notificaciones");
        button3 = new JButton("Control temperatura y humedad");
        button4 = new JButton("Cambiar datos");
        button5 = new JButton("Cerrar Sesion");

        this.add(label1);
        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.add(button4);
        this.add(button5);
    }
    


    public void setControlador(ActionListener c){
        button1.addActionListener(c);
        button2.addActionListener(c);
        button3.addActionListener(c);
        button4.addActionListener(c);
        button5.addActionListener(c);

    }

    public JButton getControladorVentaEntradas(){
        return button1;
    }

    public JButton getControladorNotificaciones(){
        return button2;
    }

    public JButton getControladorTemperaturaHumedad(){
        return button3;
    }

    public JButton getControladorCambiarDatos(){
        return button4;
    }

    public JButton getControladorCerrarSesion(){
        return button5;
    }

    public void update(){
        button1.grabFocus();

    }
}
