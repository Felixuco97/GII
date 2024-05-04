package vista.empleado;

import modelo.centroExposiciones.salas.Sala;
import modelo.sistema.Sistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
/*Rehacer tiene que estar conectado a las salas */
public class ControlAmbiente extends JPanel{
    private JLabel titulo;
    private JLabel lTemp;
    private JLabel lHumd;
    private JSlider temperatura;
    private JSlider humedad;
    private JButton atras;
    private JButton confirm;
    private JComboBox<String> salas;
    

    public ControlAmbiente(Sistema sistema){

        titulo = new JLabel("Control Ambiente", SwingConstants.CENTER);
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 24);
        titulo.setFont(font);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        temperatura = new JSlider(-30, 40, 0);
        lTemp = new JLabel("Temperatura:", SwingConstants.CENTER);
        humedad = new JSlider(0, 100, 50);
        lHumd = new JLabel("Humedad:", SwingConstants.CENTER);
        atras = new JButton("Atras");
        confirm = new JButton("Confirmar cambios");
        temperatura.setMinorTickSpacing(2);  
        temperatura.setMajorTickSpacing(10);  
        humedad.setMinorTickSpacing(2);  
        humedad.setMajorTickSpacing(10);  
        temperatura.setPaintTicks(true);
        humedad.setPaintTicks(true);
        temperatura.setPaintLabels(true);
        humedad.setPaintLabels(true);



        ArrayList<Sala> sls= sistema.getCentro().getAllSalas();
        ArrayList<String> name = new ArrayList<>();
        
        for(Sala s : sls) {
            name.add(s.getNombre());
        }


        salas = new JComboBox<String>();
        for (String s : name){
            salas.addItem(s);
        }
        
        salas.setSelectedIndex(0);


        // Crear un panel para centrar el botón
        JPanel panelBoton = new JPanel(new BorderLayout());
        panelBoton.add(atras, BorderLayout.WEST);
        panelBoton.add(confirm, BorderLayout.EAST);
        this.add(titulo);
        this.add(salas);
        this.add(lTemp);
        this.add(temperatura);
        this.add(lHumd);
        this.add(humedad);
        this.add(panelBoton); 
    }

    //getters
    public double getTemperatura(){
        return (double) temperatura.getValue();
    }

    public double getHumedad(){
        return (double) humedad.getValue();
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

    }
}