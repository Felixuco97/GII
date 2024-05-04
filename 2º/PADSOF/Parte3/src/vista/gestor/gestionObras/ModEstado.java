package vista.gestor.gestionObras;

import javax.swing.*;

import modelo.centroExposiciones.obras.Obra;
import modelo.sistema.Sistema;

import java.awt.*;
import vista.utils.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ModEstado extends JPanel{
    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JComboBox<String> obras;
    private JComboBox<String> estado;

    private JPanel panelInferior;
    private JButton confirm;
    private JButton atras; 

    public ModEstado(Sistema sistema) {

        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("MODIFICAR ESTADO OBRAS");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);

        /*PANEL MEDIO */

        panelMedio = new JPanel();
        String[] labels = {"Seleccionar obra: ", "Estado: "};
        panelMedio.setLayout(new SpringLayout());
    

        //seleccionar obra
        ArrayList<Obra> obrs= sistema.getCentro().getObras();
        ArrayList<String> name = new ArrayList<>();
        
        for(Obra o : obrs) {
            name.add(o.getTitulo());
        }

        obras = new JComboBox<String>();
        for (String s : name){
            obras.addItem(s);
        }
        
        obras.setSelectedIndex(0);

        //Estado
        String estados[] = {"Almacen", "Reparacion", "Exposicion", "Fuera del Centro"};   
        estado= new JComboBox<>(estados);




        JComponent[] components = {obras, estado};
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



    public String getObraSeleccionada() {   
        return (String) obras.getSelectedItem();
    }

    public String getEstadoSeleccionado() {   
        return (String) estado.getSelectedItem();
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
        obras.setSelectedIndex(0);
        estado.setSelectedIndex(0);
    }
    

}
