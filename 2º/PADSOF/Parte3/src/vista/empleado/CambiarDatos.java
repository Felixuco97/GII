package vista.empleado;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import modelo.sistema.Sistema;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class CambiarDatos extends JPanel{

    private JLabel title, newAccountNum, newSecNum, newDireccion;
    private JTextField accountNum, secNum, direccion;
    private JButton atras, guardar;
    private JPanel intermedio, inferior;
    
    public CambiarDatos(Sistema sistema){

        setLayout(new BorderLayout());

        title = new JLabel("CAMBIAR DATOS", SwingConstants.CENTER);
        newAccountNum = new JLabel("Nuevo número de cuenta:");
        newSecNum = new JLabel("Nuevo número de seg. social:");
        newDireccion = new JLabel("Nueva direccion:");
        accountNum = new JTextField();
        secNum = new JTextField();
        direccion = new JTextField();
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 30); 
        title.setFont(font);
        atras = new JButton("Atras");
        guardar = new JButton("Guardar");
        intermedio = new JPanel();
        inferior = new JPanel();
        Box caja = Box.createVerticalBox();

        caja.add(Box.createVerticalStrut(25));
        caja.add(newAccountNum);
        caja.add(Box.createVerticalStrut(25));
        caja.add(accountNum);
        caja.add(Box.createVerticalStrut(25));
        caja.add(newSecNum);
        caja.add(Box.createVerticalStrut(25));
        caja.add(secNum);
        caja.add(Box.createVerticalStrut(25));
        caja.add(newDireccion);
        caja.add(Box.createVerticalStrut(25));
        caja.add(direccion);
        intermedio.add(caja);

        inferior.add(atras, BorderLayout.WEST);
        inferior.add(guardar, BorderLayout.EAST);

        add(title, BorderLayout.NORTH); add(intermedio, BorderLayout.CENTER); add(inferior, BorderLayout.SOUTH);
    }

    public void setControlador(ActionListener c){

        atras.addActionListener(c);
        guardar.addActionListener(c);
    }

    public String getAccountNumber() {    
        return accountNum.getText();
    }

    public String getSecNumber() {    
        return secNum.getText();
    }

    
    public String getDireccion() {    
        return direccion.getText();
    }


    public JButton getControladorGuardar(){
        return  guardar;
    }

    public JButton getControladorAtras(){
        return atras;
    }

    public void update(){
      accountNum.setText("");
      secNum.setText("");
      direccion.setText(""); 
   } 
}
