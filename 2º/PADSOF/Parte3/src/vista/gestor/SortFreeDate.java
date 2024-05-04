package vista.gestor;

import vista.utils.*;
import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class SortFreeDate extends JPanel{
    private JLabel titulo;
    private JTextField fechaInitInscripcion;
    private JTextField fechaFinInscripcion;
    private JTextField horaInicioInsc;
    private JTextField horaFinInscrip;
    private JTextField penalizacion;
    private JSpinner numGanadores;
    private JSpinner numEntradas;
    private SpinnerNumberModel modelWinners;
    private SpinnerNumberModel modelEntrs;
    private JComboBox<String> expos;
    private JButton atras, confirm;
    private JPanel panelBotones;
    //entradas FALTA

    public SortFreeDate(Sistema sistema){
        this.setLayout(new BorderLayout());

        titulo = new JLabel("SORTEO - FECHA LIBRE", SwingConstants.CENTER);
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 24);
        titulo.setFont(font);
        fechaInitInscripcion = new JTextField(10);
        fechaFinInscripcion = new JTextField(10);
        horaInicioInsc = new JTextField(10);
        horaFinInscrip = new JTextField(10);
        penalizacion = new JTextField(10);

        modelWinners = new SpinnerNumberModel(1, 1, 20, 1);
        numGanadores = new JSpinner(modelWinners);  
        modelEntrs = new SpinnerNumberModel(1, 1, 20, 1);
        numEntradas = new JSpinner(modelEntrs);      
        String temp[] = {"Expo1", "Expo2", "Expo3", "Expo4"}; //Temporal, tendrá que ser un getExpos
        expos = new JComboBox<>(temp);

        panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        atras = new JButton("Atras");
        confirm = new JButton("Confirmar");
        panelBotones.add(atras, BorderLayout.WEST);
        panelBotones.add(confirm, BorderLayout.EAST);

        JPanel panelMedio = new JPanel(new SpringLayout());

        String labels[] = {"Fecha de inicio de la inscripcion:", "Hora de inicion de la inscripcion:", "Fecha del sorteo:", "Hora de finalizacion del sorteo:", "Numero de ganadores del sorteo:", "Sobre que exposicion será el sorteo:", "Numero de entradas", "Penalizacion"};
        JComponent[] components = {fechaInitInscripcion, horaInicioInsc, fechaFinInscripcion, horaFinInscrip, numGanadores, expos, numEntradas, penalizacion};
        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            panelMedio.add(l);
            l.setLabelFor(components[i]);
            panelMedio.add(components[i]); 
        }
        SpringUtilities.makeCompactGrid(panelMedio, labels.length, 2, 7 ,7, 7, 7);
        
        this.add(titulo, BorderLayout.NORTH);
        this.add(panelMedio, BorderLayout.CENTER);
        this.add(panelBotones, BorderLayout.SOUTH);
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
    public LocalDate getFechaIncio(){
        return LocalDate.parse(fechaInitInscripcion.getText());
    }

    public LocalDate getFechaFin(){
        return LocalDate.parse(fechaFinInscripcion.getText());
    }

    public LocalTime getHoraIncio(){
        return LocalTime.parse(horaInicioInsc.getText());
    }

    public LocalTime getHoraFin(){
        return LocalTime.parse(horaFinInscrip.getText());
    }

    public Integer getNumGanadores(){
        return (Integer) numGanadores.getValue();
    }

    public Integer getNumEntradas(){
        return (Integer) numEntradas.getValue();
    }

    public String getExposicion(){
        return (String)expos.getSelectedItem();
    }

    public String getPenalizacion(){
        return penalizacion.getText();
    }
    /*FIN AÑADIDO*/

    public void update(){
        expos.setSelectedIndex(0);
        numGanadores.setValue(1);
        fechaInitInscripcion.setText("");
        fechaInitInscripcion.grabFocus();
        fechaFinInscripcion.setText("");
        horaInicioInsc.setText("");
        horaFinInscrip.setText("");
    }
}
