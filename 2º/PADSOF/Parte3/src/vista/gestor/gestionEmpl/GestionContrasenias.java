package vista.gestor.gestionEmpl;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import vista.utils.*;
import modelo.sistema.Sistema;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class GestionContrasenias extends JPanel{

    private JTextField newOne;

    private JButton atras, guardar;

    private JPanel panelSuperior;
    private JLabel titulo;

    private JPanel panelMedio;

    private JPanel panelInferior;




    public GestionContrasenias(Sistema sistema){

    /*Establecemos layout de este panel */
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    /*PANEL SUPERIOR */
    panelSuperior = new JPanel();
    titulo = new JLabel("Gestion Contraseñas", SwingConstants.CENTER);
    Font font = new Font(Font.MONOSPACED, Font.BOLD, 24);
    titulo.setFont(font);
    panelSuperior.add(titulo);


    /*PANEL MEDIO*/
    panelMedio = new JPanel();
    String[] labels = {"Nueva contraseña:"};
    panelMedio.setLayout(new SpringLayout());


    newOne = new JTextField();

    JComponent[] components = {newOne};
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
    atras = new JButton("Atras");
    guardar = new JButton("Guardar");
    panelInferior.add(atras);
    panelInferior.add(guardar);



    this.add(panelSuperior);
    this.add(panelMedio);
    this.add(panelInferior);
    }
    
    public void setControlador(ActionListener c){

        atras.addActionListener(c);
        guardar.addActionListener(c);
    }

    public JButton getControladorAtras(){
        return  atras;
    }

    public JButton getControladorGuardar(){
        return  guardar;
    }
    // public String getContraseniaAnterior(){

    //     return currentOne.getText();
    // }

    public String getContraseniaNueva(){

        return newOne.getText();
    }


    public void update(){
      guardar.grabFocus();
    //   currentOne.setText("");
      newOne.setText("");
   } 
}
