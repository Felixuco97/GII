package vista.gestor;
import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;
import java.awt.event.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class InicioGestor extends JPanel{
    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;

    private JPanel panelInferior;
    //private JButton atras;



    public InicioGestor(Sistema sistema){
        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("¡ B I E N V E N I D O !");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);

        /*PANEL INFERIOR */
        panelMedio = new JPanel();
        panelMedio.setLayout(new GridLayout(0, 2));


        button1 = new JButton("Configuracion horario");

        button2 = new JButton("Gestion de exposiciones");

        button3 = new JButton("Estadisticas");

        button4 = new JButton("Gestion Empleados");

        button5 = new JButton("Gestion Obras");

        button6 = new JButton("Gestion salas");

        button7 = new JButton("Otras acciones");

        button8 = new JButton("Cerrar sesion");


        panelMedio.add(button1);
        panelMedio.add(button2);
        panelMedio.add(button3);
        panelMedio.add(button4);
        panelMedio.add(button5);
        panelMedio.add(button6);
        panelMedio.add(button7);
        panelMedio.add(button8);


        //PANEL INFERIOR
        panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        //atras = new JButton("Atras");

        //panelInferior.add(atras);

        

        /*Añadimos paneles al principal */
        this.add(panelSuperior);
        this.add(panelMedio);
        this.add(panelInferior);
    }

    public void setControlador(ActionListener c){
        button1.addActionListener(c);
        button2.addActionListener(c);
        button3.addActionListener(c);
        button4.addActionListener(c);
        button5.addActionListener(c);
        button6.addActionListener(c);
        button7.addActionListener(c);
        button8.addActionListener(c);
    }


    public JButton getControladorConfigHorario(){
        return button1; //CAMBIO;;FALTA POR HACER
    }

    public JButton getControladorGestExpo(){
        return button2;
    }

    public JButton getControladorEstadistica(){
        return button3; //FALTA
    }

    public JButton getControladorGestEmpleado(){
        return button4;
    }

    public JButton getControladorGestObra(){
        return button5;
    }

    public JButton getControladorGestSala(){
        return button6;
    }

    public JButton getControladorOtrasOpc(){
        return button7;
    }

    public JButton getControladorCerrarSesion(){
        return button8;
    }

    public void update(){
        button1.grabFocus();
    }
}
