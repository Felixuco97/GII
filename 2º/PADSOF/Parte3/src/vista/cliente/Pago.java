package vista.cliente;

import javax.swing.*;

import modelo.sistema.Sistema;
import vista.utils.*;
import java.awt.*;
import java.awt.event.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class Pago extends JPanel{

    private JPanel panelSuperior;
    private JLabel lab1;

    private JPanel panelMedio;
    private JTextField titular;
    private JTextField numero;
    private JTextField cvv;

    private JPanel panelInferior;
    private JButton atras;
    private JButton comprar;

    public Pago(Sistema sistema){
        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        lab1 = new JLabel("Introduce los datos de pago: ");
        panelSuperior.add(lab1);


        /*PANEL MEDIO */
        panelMedio = new JPanel();
        String[] labels = {"Nombre del titular:", "Numero de tarjeta: ", "CVV:"};
        panelMedio.setLayout(new SpringLayout());

        titular = new JTextField();
        titular.setPreferredSize(new Dimension(100,20));
        
        numero = new JTextField();
        numero.setPreferredSize(new Dimension(100,20));

        cvv = new JTextField();   
        cvv.setPreferredSize(new Dimension(100,20));
 


        JComponent[] components = {titular, numero, cvv};
        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING); // 2º parám= alineación horizontal
            panelMedio.add(l); // Lo añadimos sin ninguna restricción
            l.setLabelFor(components[i]); // El componente que l etiqueta
            panelMedio.add(components[i]); // Lo añadimos sin ninguna restricción
        }
    
    
            // Colocar componentes en una cuadrícula compacta
        SpringUtilities.makeCompactGrid(panelMedio, labels.length, 2,6, 6, 6, 6);
    

        /*PANEL INFERIOR */
        panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        comprar = new JButton("Pagar");
        atras = new JButton("Atras");

        panelInferior.add(comprar);
        panelInferior.add(atras);



        /*Añadimos subpaneles al panel */
        this.add(panelSuperior);
        this.add(panelMedio);
        this.add(panelInferior);
    }


    //getters
    public String getTitular() {
        return titular.getText();
    }

    public String getNumero() {
        return numero.getText();
    }

    public String getCvv() {
        return cvv.getText();
    }


    public void setControlador(ActionListener c){
        comprar.addActionListener(c);
        atras.addActionListener(c);
    }

    public JButton getControladorComprar(){
        return  comprar;
    }

    public JButton getControladorAtras(){
        return atras;
    }

    public void update(){
        comprar.grabFocus();

    }
}
