package vista.gestor.gestionExpos;

import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.event.*;
import java.util.Set;
import java.awt.*;
import vista.utils.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class BajaExpo extends JPanel{
    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JComboBox<String> expo;


    private JPanel panelInferior;
    private JButton confirm;
    private JButton atras; 
    public BajaExpo(Sistema sistema){

        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("BAJA EXPOSICIONES");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);


        /*PANEL MEDIO */
        panelMedio = new JPanel();
        String[] labels = {"Seleccionar exposicion:"};
        panelMedio.setLayout(new SpringLayout());
    
        //seleccionar expo a dar de baja
        Set<String> expoStr = sistema.getCentro().getExpoNames();
        expo = new JComboBox<String>();
        for (String s : expoStr){
            expo.addItem(s);
        }
        expo.setSelectedIndex(0);

        JComponent[] components = {expo};
        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING); // 2º parám= alineación horizontal
            panelMedio.add(l); // Lo añadimos sin ninguna restricción
            l.setLabelFor(components[i]); // El componente que l etiqueta
            panelMedio.add(components[i]); // Lo añadimos sin ninguna restricción
        }


        // Colocar componentes en una cuadrícula compacta
        
        SpringUtilities.makeCompactGrid(panelMedio, 
                                        labels.length, 2,
                                        6, 6, 
                                        6, 6);







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

    public void setControlador(ActionListener c){
        confirm.addActionListener(c);
        atras.addActionListener(c);
    }


    public String getExposicion(){    
        return (String) expo.getSelectedItem();
    }


    public JButton getControladorConfirm(){
        return  confirm;
    }

    public JButton getControladorAtras(){
        return atras;
    }

    public void update(){
        confirm.grabFocus();

    }
}
