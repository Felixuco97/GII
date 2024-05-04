package vista.gestor;

import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;
import java.awt.event.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class GestionDeSalas extends JPanel {
    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JButton button1;
    private JButton button2;


    private JPanel panelInferior;
    private JButton atras;

    public GestionDeSalas(Sistema sistema){

        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("GESTIÓN   DE  SALAS");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);

        //PANEL MEDIO
        panelMedio = new JPanel();
        panelMedio.setLayout(new GridLayout(0, 1));

        button1 = new JButton("Informacion salas");
        button2 = new JButton("Dividir salas");


        panelMedio.add(button1);
        panelMedio.add(button2);

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
        atras.addActionListener(c);
    }

    public JButton getControladorInfoSalas(){
        return button1;
    }


    public JButton getControladorDivSalas(){
        return button2;
    }

    public JButton getControladorAtras(){
        return atras;
    }

    public void update(){
        button1.grabFocus();
    }
}
