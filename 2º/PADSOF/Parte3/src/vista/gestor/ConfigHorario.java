package vista.gestor;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;

import javax.swing.*;

import modelo.sistema.Sistema;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ConfigHorario extends JPanel{

    private JLabel titulo, apertura, cierre;
    private SpinnerListModel spinnerModel, spinnerModel2;
    private JSpinner open, close;
    private JButton atras, guardar;

    private JPanel intermedio;
    private JPanel inferior;

    public ConfigHorario(Sistema sistema){

        //Inicializar variables

        setLayout(new BorderLayout());

        titulo = new JLabel("CONFIGURACIÓN DE HORARIO", SwingConstants.CENTER);
        apertura = new JLabel("Apertura");
        cierre = new JLabel("Cierre");
        String[] horas = new String[24];
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 24);
        titulo.setFont(font);
        intermedio = new JPanel();
        inferior = new JPanel();

        for (int i = 0; i < 24; i++) {
            horas[i] = String.format("%02d:00", i); 
        }

        spinnerModel = new SpinnerListModel(horas);
        spinnerModel2 = new SpinnerListModel(horas);
        open = new JSpinner(spinnerModel);
        close = new JSpinner(spinnerModel2);

        atras = new JButton("Atras");
        guardar = new JButton("Guardar cambios");

        Box caja = Box.createVerticalBox();

        caja.add(Box.createVerticalStrut(25));
        caja.add(apertura);
        caja.add(Box.createVerticalStrut(25));
        caja.add(open);
        caja.add(Box.createVerticalStrut(25));
        caja.add(cierre);
        caja.add(Box.createVerticalStrut(25));
        caja.add(close);
        intermedio.add(caja);

        inferior.add(atras, BorderLayout.WEST);
        inferior.add(guardar, BorderLayout.EAST);

        add(titulo, BorderLayout.NORTH); add(intermedio, BorderLayout.CENTER); add(inferior, BorderLayout.SOUTH);

    }

    public LocalTime getHoraApertura() {
        return LocalTime.parse((String)open.getValue());
    }

    public LocalTime getHoraCierre() {
        return LocalTime.parse((String)close.getValue());
    }

    

    
    public void setControlador(ActionListener c){

        atras.addActionListener(c);
        guardar.addActionListener(c);
    }

    public JButton getControladorAtras(){
        return  atras;
    }

    public JButton getControladorGuardar(){
        return  guardar;
    }

    public void update(){
        guardar.grabFocus();
    } 
}
