package vista.gestor;

import java.awt.event.*;
import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;

/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class GestionDeExposiciones extends JPanel {
    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;


    private JPanel panelInferior;
    private JButton atras;

    public GestionDeExposiciones(Sistema sistema){

        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("GESTIÓN   DE  EXPOSICIONES");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);

        //PANEL MEDIO
        panelMedio = new JPanel();
        panelMedio.setLayout(new GridLayout(0, 1));

        button1 = new JButton("Dar de alta exposicion");
        button2 = new JButton("Dar de baja exposicion");
        button3 = new JButton("Cancelacion exposicion");
        button4 = new JButton("Mapear Obras");


        panelMedio.add(button1);
        panelMedio.add(button2);
        panelMedio.add(button3);
        panelMedio.add(button4);

        //PANEL INFERIOR
        panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        atras = new JButton("Atras");

        panelInferior.add(atras);

        //AÑADIMOS SUBPANELES
        this.add(panelSuperior);
        this.add(panelMedio);
        this.add(panelInferior);
    }
    
    public void setControlador(ActionListener c){
        button1.addActionListener(c);
        button2.addActionListener(c);
        button3.addActionListener(c);
        button4.addActionListener(c);
        atras.addActionListener(c);
    }

    public JButton getControladorAltaExpo(){
        return button1;
    }

    public JButton getControladorBajaExpo(){
        return button2;
    }
    
    public JButton getControladorCancelarExpo(){
        return button3;
    }

    public JButton getControladorMapearObra(){
        return button4;
    }

    public JButton getControladorAtras(){
        return atras;
    }

    public void update(){
        button1.grabFocus();
    }
}
