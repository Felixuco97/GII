package vista.gestor.configuracionDescuentos;

import java.awt.event.*;

import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class Desc extends JPanel{
    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JButton botonDescAnticip;
    private JButton botonDescFrec;


    private JPanel panelInferior;
    private JButton atras;




    public Desc(Sistema sistema){
        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("Descuento a configurar");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);


        /*PANEL CENTRAL */
        panelMedio = new JPanel(new GridLayout(0, 1));
        botonDescAnticip= new JButton("Descuento Compra Anticipada");
        botonDescFrec= new JButton("Descuento Cliente Frecuente");
        panelMedio.add(botonDescAnticip);
        panelMedio.add(botonDescFrec);


        /*PANEL INFERIOR */
        panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        atras = new JButton("Atras");

        panelInferior.add(atras);

        /*AÑADIR SUBPANELES */
        this.add(panelSuperior);
        this.add(panelMedio);
        this.add(panelInferior);
    }
    
    public void setControlador(ActionListener c){
        botonDescFrec.addActionListener(c);
        botonDescAnticip.addActionListener(c);
        atras.addActionListener(c);
    }


    public JButton getControladorAtras(){
        return atras;
    }

    public JButton getControladorDescuentoAnticipada(){
    return botonDescAnticip;
    }


    public JButton getControladorDescuentoFrecuente(){
        return botonDescFrec;
    }



    public void update(){
        atras.grabFocus();
    }
}
