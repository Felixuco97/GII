package vista.gestor.gestionExpos;

import javax.swing.*;

import modelo.centroExposiciones.exposiciones.Exposicion;
import modelo.centroExposiciones.obras.Obra;
import modelo.centroExposiciones.salas.Exposala;
import modelo.centroExposiciones.salas.Sala;
import modelo.sistema.Sistema;

import java.awt.*;
import vista.utils.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Set;

/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class Mapear extends JPanel{

    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JComboBox<String> obras;
    private JComboBox<String> salas;
    private JComboBox<String> expo;

    private JPanel panelInferior;
    private JButton confirm;
    private JButton atras; 

    public Mapear(Sistema sistema){
        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("MAPEAR OBRAS");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);

        /*PANEL MEDIO */

        panelMedio = new JPanel();
        String[] labels = {"Seleccionar exposicion: ", "Seleccionar obra: ", "Seleccionar sala: "};
        panelMedio.setLayout(new SpringLayout());
    

        //seleccion de obras
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
    




        //seleccion de expo
        Set<String> expoStr = sistema.getCentro().getExpoNames();
        expo = new JComboBox<String>();
        for (String e : expoStr){
            expo.addItem(e);
        }
        expo.setSelectedIndex(0);

        //salas
        salas = new JComboBox<>();


        //seleccion de sala
        expo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                /*obtener expo*/
                String exposicionSeleccionada = getExpoSeleccionada();
                Exposicion expos = sistema.getCentro().getExpositionByName(exposicionSeleccionada);
                
                /*obtener salas asignadas a esa expo*/
                ArrayList<Exposala> sls = expos.getAllSalas();
                ArrayList<String> nombreSalas = new ArrayList<>();
                for(Sala s : sls) {
                    nombreSalas.add(s.getNombre());
                }
                
                
                salas.removeAllItems();
                for (String s : nombreSalas){
                    salas.addItem(s);
                }
            }
        });
        

        JComponent[] components = {expo, obras, salas};
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
        // this.add(panelSal);
        this.add(panelInferior);


    }

    public String getExpoSeleccionada() {   
        return (String) expo.getSelectedItem();
    }

    public String getObraSeleccionada() {   
        return (String) obras.getSelectedItem();
    }

    public String getSalaSeleccionada() {   
        return (String) salas.getSelectedItem();
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
        salas.setSelectedIndex(0);
    }

    
}
