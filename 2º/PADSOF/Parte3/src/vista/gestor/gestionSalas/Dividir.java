package vista.gestor.gestionSalas;

import javax.swing.*;

import modelo.centroExposiciones.salas.Sala;
import modelo.sistema.Sistema;

import java.awt.*;
import vista.utils.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class Dividir extends JPanel{
    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JComboBox<String> salas;
    private JTextField ancho;


    private JPanel panelInferior;
    private JButton confirm;
    private JButton atras; 

    public Dividir(Sistema sistema){
 
        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("DIVISION SALAS");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);


        //PANEL MEDIO
        panelMedio = new JPanel();
        String[] labels = {"Seleccionar sala a dividir:", "Especifica el ancho: "};
        panelMedio.setLayout(new SpringLayout());
    

        //tipo de exposicion


        //seleccion de sala a dividir
        ArrayList<Sala> sls= sistema.getCentro().getAllSalas();
        ArrayList<String> name = new ArrayList<>();
        
        for(Sala s : sls) {
            name.add(s.getNombre());
        }


        salas = new JComboBox<String>();
        for (String s : name){
            salas.addItem(s);
        }
        
        salas.setSelectedIndex(0);


        //ancho
        ancho = new JTextField();


        JComponent[] components = {salas, ancho};
        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING); // 2º parám= alineación horizontal
            panelMedio.add(l); // Lo añadimos sin ninguna restricción
            l.setLabelFor(components[i]); // El componente que l etiqueta
            panelMedio.add(components[i]); // Lo añadimos sin ninguna restricción
        }


        // Colocar componentes en una cuadrícula compacta
        
        SpringUtilities.makeCompactGrid(panelMedio, labels.length, 2, 6, 6, 6, 6);

        //PANEL INFERIOR
        panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confirm = new JButton("Confirmar");
        atras = new JButton("Atras");

        panelInferior.add(confirm);
        panelInferior.add(atras);

        

        /*AÑADIR LOS SUBPANELES */
        this.add(panelSuperior);
        this.add(panelMedio);
        this.add(panelInferior);
    }
    
    public String getSalaSeleccionada() {   
        return (String) salas.getSelectedItem();
    }


    public String getAncho() {
        return ancho.getText();
    }

    public void setControlador(ActionListener c){
        confirm.addActionListener(c);
        atras.addActionListener(c);
    }

    public JButton getControladorConfirm(){
        return  confirm;
    }

    public JButton getControladorAtras(){
        return atras;
    }

    public void update(){
        confirm.grabFocus();
        salas.setSelectedIndex(0);
        ancho.setText("");
    }
}
