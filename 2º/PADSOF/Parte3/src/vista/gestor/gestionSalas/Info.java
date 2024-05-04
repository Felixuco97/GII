package vista.gestor.gestionSalas;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import java.awt.Font;
import javax.swing.*;

import modelo.centroExposiciones.salas.Sala;
import modelo.sistema.Sistema;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
/*VER COMO IMPRIMIR LA INFO DE CADA SALA SELECCIONADA (HACER GETTERS) */
public class Info extends JPanel{

    private JPanel panelSuperior;
    private JButton atras;
    private JLabel titulo;
    private JPanel panelMedio;
    private JComboBox<String> salas;
    private JPanel panelInferior;
    private JPanel panelInfo;

    
    public Info(Sistema sistema) {

        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        titulo = new JLabel("Info Sala");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        titulo.setFont(font);
        panelSuperior.add(titulo);

        /*PANEL MEDIO*/
        ArrayList<Sala> sls= sistema.getCentro().getAllSalas();
        ArrayList<String> name = new ArrayList<>();
        
        for(Sala s : sls) {
            name.add(s.getNombre());
        }

        salas = new JComboBox<String>();
        for (String s : name){
            salas.addItem(s);
        }

        panelMedio = new JPanel();
        
        salas.setSelectedIndex(0);

        panelMedio.add(salas);



        /*PANEL Info */
        panelInfo = new JPanel();

        /*IMPRIMIR INFO DE LA SALA SELECCIONADA */
        salas.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String salaSeleccionada = (String) salas.getSelectedItem();
            
            /*obtener la lista de todas las salas*/
            ArrayList<Sala> salas = sistema.getCentro().getAllSalas();
            
            /*buscar la sala seleccionada en la lista de salas*/
            Sala sala = null;
            for (Sala s : salas) {
                if (s.getNombre().equals(salaSeleccionada)) {
                    sala = s;
                    break;
                }
            }
            
            if (sala != null){
                panelInfo.removeAll(); 
                panelInfo.setLayout(new GridLayout(6, 2)); 

                // Crear JLabels con la información de la sala y agregarlos al panel
                panelInfo.add(new JLabel("Nombre:"));
                panelInfo.add(new JLabel(sala.getNombre()));
                panelInfo.add(new JLabel("Dimensiones:"));
                panelInfo.add(new JLabel(sala.getDimension().toString())); 
                panelInfo.add(new JLabel("Aforo:"));
                panelInfo.add(new JLabel(Integer.toString(sala.getAforo())));
                panelInfo.add(new JLabel("Tomas de Corriente:"));
                panelInfo.add(new JLabel(Integer.toString(sala.getTomasCorriente())));
                panelInfo.add(new JLabel("Temperatura:"));
                panelInfo.add(new JLabel(Double.toString(sala.getTemperatura())));
                panelInfo.add(new JLabel("Humedad:"));
                panelInfo.add(new JLabel(Double.toString(sala.getHumedad())));

                panelInfo.revalidate();
                panelInfo.repaint();
            }
        }
    });



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



    public String getSalaSeleccionada() {   
        return (String) salas.getSelectedItem();
    }


    public void setControlador(ActionListener c){
        atras.addActionListener(c);
    }

    public JButton getControladorAtras(){
        return atras;
    }

    public void update(){
        atras.grabFocus();
    }
}
