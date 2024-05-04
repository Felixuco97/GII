package vista.gestor.configuracionDescuentos;

import java.awt.event.*;
import java.util.Set;

import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;
import vista.utils.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class DescClienteFrecuente extends JPanel{
    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JTextField porcentaje;
    private JTextField numVis;
    private JTextField numDias;
    private JComboBox<String> expo;

    private JPanel panelInferior;
    private JButton confirm;
    private JButton atras;



    //public DescuentoClienteFrecuente(double porcentaje, int visitas, int ndias)

    public DescClienteFrecuente(Sistema sistema){
        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("Descuento cliente frecuente");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);


        /*PANEL CENTRAL */
        panelMedio = new JPanel();
        panelMedio.setLayout(new SpringLayout());
        String[] labels = {"Selecciona exposicion: ", "Establece el porcentaje: ", "Numero de visitas minima: ", "Numero dias: "};


        /*Seleccionar exposicion */
        Set<String> expoStr = sistema.getCentro().getExpoNames();

        expo = new JComboBox<String>();
        
        for (String s : expoStr){
            expo.addItem(s);
        }
        expo.setSelectedIndex(0);



        /*Porcentaje*/
        porcentaje = new JTextField();

        /*Numero visitas */

        numVis = new JTextField();

        /*Numero de dias*/
        numDias = new JTextField();


        JComponent[] components = {expo, porcentaje, numVis, numDias};
        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING); // 2º parám= alineación horizontal
            panelMedio.add(l); // Lo añadimos sin ninguna restricción
            l.setLabelFor(components[i]); // El componente que l etiqueta
            panelMedio.add(components[i]); // Lo añadimos sin ninguna restricción
        }

        // Colocar componentes en una cuadrícula compacta
        SpringUtilities.makeCompactGrid(panelMedio, labels.length, 2, 6, 6, 6, 6);

        

        /*PANEL INFERIOR */
        panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confirm = new JButton("Confirmar");
        atras = new JButton("Atras");

        panelInferior.add(confirm);
        panelInferior.add(atras);

        /*AÑADIR SUBPANELES */
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

    /*AÑADIDO*/

    public String getExposicion(){
        return (String) expo.getSelectedItem();
    }

    public double getPorcentaje(){ 
        return Double.parseDouble(porcentaje.getText());
    }

    public int getNumVisitas() {
        return Integer.parseInt(numVis.getText());
    }

    public int getNumDias() {
        return Integer.parseInt(numDias.getText());
    }

    /*FIN AÑADIDO*/

    public void update(){
        confirm.grabFocus();
    
    }
}
