package vista.gestor;

import java.awt.event.*;
import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ConfigSort extends JPanel{
    

    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JButton sortFechaFija;
    private JButton sortFreeDate;
    private JButton sortTimeInterval;

    private JPanel panelInferior;
    private JButton atras;

    public ConfigSort(Sistema sistema){

        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("OTRAS  OPCIONES");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);

        //PANEL MEDIO
        panelMedio = new JPanel();
        panelMedio.setLayout(new GridLayout(3, 1));

        sortFechaFija = new JButton("Sorteo de fecha fija");
        sortFreeDate = new JButton("Sorteo de fecha libre");
        sortTimeInterval = new JButton("Sorteo de intervalo libre");

        panelMedio.add(sortFechaFija);
        panelMedio.add(sortFreeDate);
        panelMedio.add(sortTimeInterval);

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
        sortFechaFija.addActionListener(c);
        sortFreeDate.addActionListener(c);
        sortTimeInterval.addActionListener(c);
        atras.addActionListener(c);
    }

    public JButton getControladorSorteoFechaFija(){
        return sortFechaFija;
    }

    public JButton getControladorSorteoFechaLibre(){
        return sortFreeDate;
    }

    public JButton getControladorSorteoTimeInter(){

        return sortTimeInterval;
    }
  
     public JButton getControladorAtras(){

        return atras;
    }
    public void update(){
        sortFechaFija.grabFocus();
    }
}

