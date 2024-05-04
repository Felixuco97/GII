package vista.cliente;
import javax.swing.*;
import java.awt.*;

import vista.utils.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilCalendarModel;

import modelo.sistema.Sistema;

import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Set;
import java.time.LocalTime;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ComprarEntrada extends JPanel {
 
private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JSpinner sel1;
    private JDatePanelImpl fecha;
    private SpinnerListModel spinnerModel;
    private JSpinner hourSpinner;
    private JComboBox<String> expo;
    private JTextField codigo;
    private SpinnerNumberModel modelTickets;

    private JPanel panelInferior;
    private JButton siguiente;
    private JButton atras;

    public ComprarEntrada(Sistema sistema){

    /*Establecemos layout de este panel */
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


    /*PANEL SUPERIOR */
    panelSuperior = new JPanel();
    label1 = new JLabel("C O M P R A R    E N T R A D A S");
    Font font = new Font(Font.MONOSPACED, Font.BOLD, 24);
    label1.setFont(font);
    panelSuperior.add(label1);

    /*PANEL MEDIO */
    panelMedio = new JPanel();
    String[] labels = {"Seleccione el numero de entradas:", "Seleccione la fecha: ", "Seleccione la hora:", "Seleccione la exposicion: ", "¿Tiene un codigo de sorteo?:"};
    panelMedio.setLayout(new SpringLayout());
    

    //Numero de ENTRADAS
    // JLabel entradas= new JLabel("Selecciona el numero de entradas:");
    modelTickets = new SpinnerNumberModel(1,1,20,1);
    sel1 = new JSpinner(modelTickets);
    sel1.setPreferredSize(new Dimension(100,20));



    //FECHA?? ---> HAY QUE PONERLO DE OTRA FORMA (otro tipo de componente)
    UtilCalendarModel model = new UtilCalendarModel(); //devuelve calendario, en nuestro caso -> Gregoriano
    Properties p = new Properties();
    p.put("text.today", "Today");
    p.put("text.month", "Month");
    p.put("text.year", "Year");
    fecha = new JDatePanelImpl(model, p);
    
    // fecha.setPreferredSize(new Dimension(100,20));



    //HORA
    // JLabel hora= new JLabel("Hora: ");
    String[] horas = new String[24];
    for (int i = 0; i < 24; i++) {
        horas[i] = String.format("%02d:00", i); 
    }
    spinnerModel = new SpinnerListModel(horas);

    hourSpinner = new JSpinner(spinnerModel);

    // JLabel expo= new JLabel("Selecciona la exposicion:");
    

    Set<String> expoStr = sistema.getCentro().getExpoNames();

    expo = new JComboBox<String>();
    
    for (String s : expoStr){
        expo.addItem(s);
    }
    expo.setSelectedIndex(0);

    codigo = new JTextField();

    JComponent[] components = {sel1, fecha, hourSpinner, expo, codigo};
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
        siguiente = new JButton("Siguiente");
        atras = new JButton("Atras");

        panelInferior.add(atras);

        panelInferior.add(siguiente);
        

        /*AÑADIR LOS SUBPANELES */
        this.add(panelSuperior);
        this.add(panelMedio);
        this.add(panelInferior);

    }


    public void setControlador(ActionListener c){
        siguiente.addActionListener(c);
        atras.addActionListener(c);
    }

    //getters de valores
    public int getNumEntradas() {
        return (int) sel1.getValue();
    }

    public LocalDate getFecha() {    
        GregorianCalendar gregorianCalendar = (GregorianCalendar)fecha.getModel().getValue();
        if (gregorianCalendar == null)
            return null;
        ZonedDateTime zonedDateTime = gregorianCalendar.toZonedDateTime();
        LocalDate localDate = zonedDateTime.toLocalDate();
        
        return localDate;
    }

    public LocalTime getHoraSeleccionada() {
        return LocalTime.parse((String)hourSpinner.getValue());
    }

    
    public String getExpoSeleccionada() {   
        return (String) expo.getSelectedItem();
    }

    public String getCodigo() {    
         return codigo.getText();
    }

    public JButton getControladorSiguiente(){
        return  siguiente;
    }

    public JButton getControladorAtras(){
        return atras;
    }

    public void setSelectedItemExpo(String s){
        this.expo.setSelectedItem(s);
    }

    public void update(){
        siguiente.grabFocus();
        sel1.setValue(1);
        spinnerModel.setValue("00:00");
        expo.setSelectedIndex(0);
        codigo.setText("");

    }
}
