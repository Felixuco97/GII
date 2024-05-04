package vista.gestor.gestionEmpl;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import modelo.sistema.Sistema;
import vista.utils.SpringUtilities;
import modelo.usuarios.*;

/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
/*Arreglar, desordenado */
public class GestionPermisosEmpl extends JPanel{

    private JPanel panelSuperior;
    private JLabel titulo;
    private JComboBox<String> combo;
    private JPanel panelMedio;

    private JPanel panelInferior;
    private JButton atras;
    private JButton guardar;
    private JComboBox<String> accion;

    private JPanel panelBotones;
    private JRadioButton op1;
    private JRadioButton op2;
    private JRadioButton op3;

    /*Añadir accion */
    public GestionPermisosEmpl(Sistema sistema){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    /*PANEL SUPERIOR */
    panelSuperior = new JPanel();
    titulo = new JLabel("Gestion permisos");
    Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
    titulo.setFont(font);
    panelSuperior.add(titulo);

    /*PANEL MEDIO */
        panelMedio = new JPanel();
        panelMedio.setLayout(new SpringLayout());


        /*Combo empleados */
        ArrayList<Empleado> empleados = sistema.getEmpleadosRegistrados();
        combo = new JComboBox<>();

        for(Empleado emp : empleados){
            combo.addItem((emp.getNombre()));
        }

        /*Accion Empleado*/
        String acc[] = {"Añadir", "Quitar"};



        String[] labels = {"Selecciona empleado:", "Accion :", "Permiso: "};


        accion = new JComboBox<>(acc);

        panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

        op1 = new JRadioButton("Vender entradas");
        op2 = new JRadioButton("Modificar_th");
        op3 = new JRadioButton("Notificaciones");
        op1.setSelected(true);
        panelBotones.add(op1);
        panelBotones.add(op2);
        panelBotones.add(op3);



        JComponent[] components = {combo, accion, panelBotones};
        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING); // 2º parám= alineación horizontal
            panelMedio.add(l); // Lo añadimos sin ninguna restricción
            l.setLabelFor(components[i]); // El componente que l etiqueta
            panelMedio.add(components[i]); // Lo añadimos sin ninguna restricción
        }


        // Colocar componentes en una cuadrícula compacta
        
        SpringUtilities.makeCompactGrid(panelMedio, labels.length, 2,6, 6, 6, 6);




    /*PANEL INFERIOR */
    panelInferior = new JPanel();
        atras = new JButton("Atras");
        guardar =new JButton("Guardar");
        panelInferior.add(atras);
        panelInferior.add(guardar);



        this.add(panelSuperior);
        this.add(panelMedio);
        this.add(panelInferior);


    }

    public String getEmpleadoSeleccionado() {   
        return (String) combo.getSelectedItem();
    }
    
    public String getAccion() {   
        return (String) accion.getSelectedItem();
    }


    public ArrayList<JRadioButton> getRadioButtons(){

        ArrayList<JRadioButton> lista = new ArrayList<>();

        lista.add(op1); 
        lista.add(op2); 
        lista.add(op3);

        return lista;
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
