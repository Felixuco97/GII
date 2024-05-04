package vista.gestor.gestionSalas;

import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;
import vista.utils.*;
import java.awt.event.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class AltaSala extends JPanel{
    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JTextField nombre;
    private JSpinner aforo;
    private JSpinner tomas;
    private JSlider temperatura;
    private JSlider humedad;



    private JPanel panelInferior;
    private JButton confirm;
    private JButton atras; 
    
    public AltaSala(Sistema sistema){

        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("ALTA SALA");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);


        /*PANEL MEDIO */
        panelMedio = new JPanel();
        String[] labels = {"Nombre de la obra:", "Aforo:", "Nº tomas corriente: ", "Temperatura: ", "Humedad: "};
        panelMedio.setLayout(new SpringLayout());

        //nombre sala
        nombre = new JTextField();

        //aforo
            aforo = new JSpinner();
            aforo.setPreferredSize(new Dimension(100,20));

        //tomas corriente
        tomas = new JSpinner();
        tomas.setPreferredSize(new Dimension(100,20));

        //temperatura
        temperatura = new JSlider(-30, 40, 0);
        humedad = new JSlider(0, 100, 50);
        temperatura.setMinorTickSpacing(2);  
        temperatura.setMajorTickSpacing(10);  
        humedad.setMinorTickSpacing(2);  
        humedad.setMajorTickSpacing(10);  
        temperatura.setPaintTicks(true);
        humedad.setPaintTicks(true);
        temperatura.setPaintLabels(true);
        humedad.setPaintLabels(true);


        JComponent[] components = {nombre, aforo, tomas, temperatura, humedad};
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

    public JButton getControladorConfirm(){
        return  confirm;
    }

    public JButton getControladorAtras(){
        return atras;
    }

    public String getNombre(){
        return nombre.getText();
    }

    public int getAforo(){
        return (int)aforo.getValue();
    }

    public int getTomas(){
        return (int)tomas.getValue();
    }

    public double getTemperatura(){
        return (double) temperatura.getValue();
    }

    public double getHumedad(){
        return (double) humedad.getValue();
    }

    public void update(){
        nombre.setText("");
        confirm.grabFocus();

    }
}
