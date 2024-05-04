package vista.gestor.gestionEmpl;

import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;
import vista.utils.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class AltaEmpleados extends JPanel{
    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JTextField nif;
    private JTextField clave;
    private JTextField nombre;
    private JTextField numSeg;
    private JTextField numCuenta;
    private JTextField direccion;

    private JPanel radioButton;
    private JRadioButton rb1;
    private JRadioButton rb2;
    private JRadioButton rb3;

    private JPanel panelInferior;
    private JButton confirm;
    private JButton atras;
    



    public AltaEmpleados(Sistema sistema){
        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("ALTA DE EMPLEADOS");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);

        /*PANEL MEDIO*/
        panelMedio = new JPanel();
        String[] labels = {"Nombre del empleado:", "NIF del empleado:", "Asignar clave:", "Nº Seguridad Social: ","Numero de cuenta: ","Direccion", "Asignación de permisos: "};
        panelMedio.setLayout(new SpringLayout());
    
        //nombre
        nombre = new JTextField();

        //nif
        nif = new JTextField(); 

        //clave
        clave = new JTextField();
        
        //clave
        numSeg = new JTextField();

        //clave
        numCuenta = new JTextField();

        //direccion
        direccion = new JTextField();

        //radio button (permisos)
        radioButton = new JPanel();
        radioButton.setLayout(new BoxLayout(radioButton, BoxLayout.Y_AXIS));


            rb1 = new JRadioButton("Venta entradas"); 
            rb2 = new JRadioButton("Control temperatura/humedad"); 
            rb3 = new JRadioButton("Envío notificaciones"); 
            radioButton.add(rb1);
            radioButton.add(rb2);
            radioButton.add(rb3);




        JComponent[] components = {nombre, nif, clave, numSeg, numCuenta, direccion, radioButton};
        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING); // 2º parám= alineación horizontal
            panelMedio.add(l); // Lo añadimos sin ninguna restricción
            l.setLabelFor(components[i]); // El componente que l etiqueta
            panelMedio.add(components[i]); // Lo añadimos sin ninguna restricción
        }


        // Colocar componentes en una cuadrícula compacta
        
        SpringUtilities.makeCompactGrid(panelMedio, labels.length, 2,6, 6, 6, 6);


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

    //Getters


    public String getNif() {    
        return nif.getText();
   }

    public String getClave(){

        return clave.getText();
    }

    public String getNombre(){

        return nombre.getText();
    }

    public String getNumSegSocial(){

        return numSeg.getText();
    }

    public String getNumCuenta(){

        return numCuenta.getText();
    }

    public String getDireccion(){

        return direccion.getText();
    }

    public ArrayList<JRadioButton> getRadioButtons(){

        ArrayList<JRadioButton> lista = new ArrayList<>();

        lista.add(rb1); lista.add(rb2); lista.add(rb3);

        return lista;
    }



    public JButton getControladorConfirm(){
        return  confirm;
    }

    public JButton getControladorAtras(){
        return atras;
    }

    public void update(){
        confirm.grabFocus();
        nombre.setText("");
        nif.setText("");
        clave.setText("");
        numSeg.setText("");
        numCuenta.setText("");
        direccion.setText("");
    }
}
