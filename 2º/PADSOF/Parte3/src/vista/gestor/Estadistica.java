package vista.gestor;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import modelo.sistema.Sistema;
import modelo.centroExposiciones.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class Estadistica extends JPanel{

    private JPanel panelSuperior;
    private JButton atras;
    private JLabel titulo;
    private JPanel panelMedio;
    private JComboBox<String> combo;
    private JPanel panelInferior;
    private JPanel panelInfo;

    /*Al igual que en info, tendremos que mostrar las estadisticas de cada expo (haciendo getters) */
    public Estadistica(Sistema sistema){

        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        titulo = new JLabel("Estadisticas");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        titulo.setFont(font);
        panelSuperior.add(titulo);


        /*PANEL CENTRAL */
        panelMedio = new JPanel();
        panelMedio.setLayout(new BoxLayout(panelMedio, BoxLayout.Y_AXIS));

        // JLabel est= new JLabel("Selecciona como deseas desglosar las estadisticas: ");
        String str[] = {"Por exposición", "Por día"};

        combo = new JComboBox<>(str);

        /*PANEL INFO*/
        panelInfo = new JPanel();

        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) combo.getSelectedItem();
                CentroDeExposiciones centro = sistema.getCentro();
                ArrayList<String> estadisticas = new ArrayList<>();

                /* si seleccionamos "por exposicion" en el combo box */
                if (seleccion.equals("Por exposición")) {
                    estadisticas = centro.desglosarEstadisticasPorExposicion();

                    /* si seleccionamos "por dia" en el combo box */
                } else if (seleccion.equals("Por día")) {
                    estadisticas = centro.desglosarEstadisticasPorDia();
                }

                /* limpiar panelInfo */
                panelInfo.removeAll();

                /* mostrar nuevas estadísticas en panelInfo */
                for (String est : estadisticas) {
                    JLabel label = new JLabel(est);
                    panelInfo.add(label);
                }

                /* actualizar panelInfo */
                panelInfo.revalidate();
                panelInfo.repaint();
            }
        });

        // panelMedio.add(est);
        panelMedio.add(combo);

        /*PANEL INFERIOR */
        panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        atras = new JButton("Atras");
        

        panelInferior.add(atras);

        /*AÑADIR SUBPANELES */
        this.add(panelSuperior);
        this.add(panelMedio);
        this.add(panelInfo);
        this.add(panelInferior);
    }



    public String getSelectedFormat(){
        return (String)combo.getSelectedItem();
    }
    
    public JButton getControladorAtras(){
    return atras;
    }
    
    public void setControlador(ActionListener c){
        atras.addActionListener(c);
    }
    
    public void update(){
        atras.grabFocus();
        combo.setSelectedIndex(0);
    }
}
