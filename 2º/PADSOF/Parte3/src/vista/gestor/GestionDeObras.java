package vista.gestor;

import java.awt.event.*;
import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class GestionDeObras extends JPanel {
    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JButton button1;
    private JButton button2;
    private JButton button3;


    private JPanel panelInferior;
    private JButton atras;
    
    public GestionDeObras(Sistema sistema){

        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("GESTIÓN   DE  OBRAS");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);

        //PANEL MEDIO
        panelMedio = new JPanel();
        panelMedio.setLayout(new GridLayout(0, 1));

        button1 = new JButton("Dar de alta obra");
        button2 = new JButton("Dar de baja obra");
        button3 = new JButton("Cambiar estado de una obra");

        panelMedio.add(button1);
        panelMedio.add(button2);
        panelMedio.add(button3);

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
        atras.addActionListener(c);
    }


    public JButton getControladorAltaObra(){
        return button1;
    }

    public JButton getControladorBajaObra(){
        return button2;
    }

    public JButton getControladorObraCambEstd(){
        return button3;
    }

    public JButton getControladorAtras(){
        return atras;
    }

    public void update(){
        button1.grabFocus();
    }
    
}
