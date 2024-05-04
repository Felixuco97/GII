package vista.empleado;

import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;

import vista.utils.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.Set;


/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class VentaEntradas extends JPanel {

    private JPanel panelSuperior;
    private JLabel label1;
    private JPanel panelMedio;
    private SpinnerListModel spinnerModel;
    private JSpinner hourSpinner;
    private JSpinner sel1;
    private JComboBox<String> expo;
    // private JSpinner sel2;
    private JPanel panelInferior; 
    private JButton comprar;
    private JButton atras;

    public VentaEntradas(Sistema sistema){

    /*Establecemos layout de este panel */
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    /*PANEL SUPERIOR */
    panelSuperior = new JPanel();
    label1 = new JLabel("V E N D E R   E N T R A D A S", SwingConstants.CENTER);
    Font font = new Font(Font.MONOSPACED, Font.BOLD, 24);
    label1.setFont(font);
    panelSuperior.add(label1);



    /*PANEL MEDIO */
    panelMedio = new JPanel();
    String[] labels = {"Hora: ", "Selecciona el numero de entradas:", "Exposicion:"};
    panelMedio.setLayout(new SpringLayout());



    //HORA
    // JLabel hora= new JLabel("Hora: ");
    String[] horas = new String[24];
    for (int i = 0; i < 24; i++) {
        horas[i] = String.format("%02d:00", i+1); 
    }
    spinnerModel = new SpinnerListModel(horas);

    hourSpinner = new JSpinner(spinnerModel);
    


    //Numero de ENTRADAS
    // JLabel entradas= new JLabel("Selecciona el numero de entradas:");
    sel1 = new JSpinner();
    sel1.setPreferredSize(new Dimension(100,20));


    //EXPOSICION
    Set<String> expoStr = sistema.getCentro().getExpoNames();
    expo = new JComboBox<>();
    for (String s : expoStr){
        expo.addItem(s);
    }
    expo.setSelectedIndex(0);


    //PRECIO
    // JLabel precio= new JLabel("Precio (€):");
    // modelPrice = new SpinnerNumberModel(0.50, 0.50, 10, 0.10);
    // sel2 = new JSpinner(modelPrice);
    // sel2.setPreferredSize(new Dimension(100,20));

    JComponent[] components = {hourSpinner, sel1, expo};
    for (int i = 0; i < labels.length; i++) {
        JLabel l = new JLabel(labels[i], JLabel.TRAILING); // 2º parám= alineación horizontal
        panelMedio.add(l); // Lo añadimos sin ninguna restricción
        l.setLabelFor(components[i]); // El componente que l etiqueta
        panelMedio.add(components[i]); // Lo añadimos sin ninguna restricción
    }


    // Colocar componentes en una cuadrícula compacta
    SpringUtilities.makeCompactGrid(panelMedio, labels.length, 2,6, 6, 6, 6);



    //PANEL INFERIOR
    panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    comprar = new JButton("Comprar");
    atras = new JButton("Atras");

    panelInferior.add(comprar);
    panelInferior.add(atras);

    /*AÑADIMOS SUBPANELES */
    this.add(panelSuperior);
    this.add(panelMedio);
    this.add(panelInferior);
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


    public LocalTime getHoraSeleccionada() {
        return LocalTime.parse((String)hourSpinner.getValue());
    }

    public int getNumEntradas() {
        return (int) sel1.getValue();
    }
    
    public String getExpoSeleccionada() {   
        return (String) expo.getSelectedItem();
    }


    /*FIN AÑADIR */
    public void update(){
        comprar.grabFocus();
        sel1.setValue(1);
        // sel2.setValue(0.5);
        spinnerModel.setValue("00:00");
        expo.setSelectedIndex(0);

    }
}

