package vista.empleado;


import java.awt.Color;
import java.awt.Font;
import javax.swing.border.*;

import modelo.sistema.Sistema;

import java.awt.event.*;
import javax.swing.*;
import vista.utils.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class CrearNotis extends JPanel{
    private JPanel panelSuperior;
    private JLabel titulo;

    private JPanel panelMedio;
    private JTextArea cuerpo;
    private JComboBox<String> tipoNoti;
    private JTextField asunto;

    private JPanel panelInferior;
    private JButton descartar, enviar;

    

    public CrearNotis(Sistema sistema){
        
        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */

        panelSuperior = new JPanel();
        titulo = new JLabel("E N V I A R  N O T I F I C A C I O N", SwingConstants.CENTER);
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 24);
        titulo.setFont(font);
        panelSuperior.add(titulo);


        /*PANEL CENTRAL */
        panelMedio = new JPanel();
        String[] labels = {"Escribe el asunto:", "Selecciona el tipo de notificacion: ", "Escribe el cuerpo:"};
        panelMedio.setLayout(new SpringLayout());

        cuerpo = new JTextArea();
        cuerpo.setBorder(new LineBorder(Color.BLACK));
        asunto = new JTextField();

        String tipos[] = {"Sorteo", "Descuento", "Nueva Exposicion"};
        tipoNoti = new JComboBox<>(tipos);


        

        JComponent[] components = {asunto, tipoNoti, cuerpo};
        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING); // 2º parám= alineación horizontal
            panelMedio.add(l); // Lo añadimos sin ninguna restricción
            l.setLabelFor(components[i]); // El componente que l etiqueta
            panelMedio.add(components[i]); // Lo añadimos sin ninguna restricción
        }

        // Colocar componentes en una cuadrícula compacta
        SpringUtilities.makeCompactGrid(panelMedio, labels.length, 2, 6, 6, 6, 6);

        /*PANEL INFERIOR*/
        panelInferior = new JPanel();
        descartar = new JButton("Descartar");
        enviar = new JButton("Enviar");
        panelInferior.add(descartar);
        panelInferior.add(enviar);
        


        this.add(panelSuperior);
        this.add(panelMedio);
        this.add(panelInferior);
    }

    //getters
    public String getCuerpo() {    
        return cuerpo.getText();
    }
    
    public String getAsunto() {    
        return asunto.getText();
    }

    public String getTipoNoti() {
        return (String)tipoNoti.getSelectedItem();
    } 

    public void setControlador(ActionListener c){
        descartar.addActionListener(c);
        enviar.addActionListener(c);
    }

    public JButton getControladorDescartar(){
        return  descartar;
    }

    public JButton getControladorEnviar(){
        return enviar;
    }

    public void update(){
        enviar.grabFocus();
        tipoNoti.setSelectedIndex(0);
        cuerpo.setText("");
    }
}