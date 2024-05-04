package vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import modelo.sistema.Sistema;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class Registro extends JPanel{
   private JLabel titulo;
   private JLabel nick, nif, contrasenia, contrasenia2, notis;
   private JTextField txtNick, txtNif;
   private JPasswordField contr1, contr2;
   private JButton confirm, atras, incioSesion;
   private JRadioButton si, no;

    /**
    * Constructor de la clase Registro.
    * @param sistema Sistema que se utiliza para el registro.
    */
   public Registro(Sistema sistema){
      this.setLayout(new BorderLayout());
      titulo = new JLabel("REGISTRO  DE  USUARIO", SwingConstants.CENTER);
      Font font = new Font(Font.MONOSPACED, Font.BOLD, 24);
      titulo.setFont(font);
      nick = new JLabel("Introduce un nick:");
      nif = new JLabel("Introduce tu NIF:");
      contrasenia = new JLabel("Introduce una contrasenia:");
      contrasenia2 = new JLabel("Vuelve a introducir la misma contrasenia:");
      notis = new JLabel("Deseo recibir notificaciones adicionales:");
      txtNick = new JTextField(10);
      txtNif = new JTextField(10);
      contr1 = new JPasswordField(10);
      contr2 = new JPasswordField(10);
      si = new JRadioButton("SI");
      no = new JRadioButton("NO");
      ButtonGroup buttonGroup = new ButtonGroup();
      buttonGroup.add(si);
      buttonGroup.add(no);
      confirm = new JButton("Confirmar");
      atras = new JButton("Atras");
      incioSesion = new JButton("Ya tienes cuenta");



      JPanel campos = new JPanel();
      campos.setLayout(new GridLayout(6,1));
      campos.add(txtNick);
      campos.add(txtNif);
      campos.add(contr1);
      campos.add(contr2);
      campos.add(si);
      campos.add(no);

      JPanel etiquetas = new JPanel();
      etiquetas.setLayout(new GridLayout(6,1));
      etiquetas.add(nick);
      etiquetas.add(nif);
      etiquetas.add(contrasenia);
      etiquetas.add(contrasenia2);
      etiquetas.add(notis);

      JPanel panelBotones = new JPanel();
      panelBotones.setLayout(new BorderLayout());
      panelBotones.add(confirm, BorderLayout.EAST);
      panelBotones.add(atras, BorderLayout.WEST);
      panelBotones.add(incioSesion, BorderLayout.CENTER);

      this.add(titulo, BorderLayout.NORTH);
      this.add(etiquetas, BorderLayout.WEST);
      this.add(campos, BorderLayout.EAST);
      this.add(panelBotones, BorderLayout.SOUTH); 
   }

   public void setControlador(ActionListener c){
      confirm.addActionListener(c);
      atras.addActionListener(c);
      incioSesion.addActionListener(c);
   }

   public JButton getControladorIniciarSesion(){
      return  incioSesion;
   }

   public JButton getControladorConfirm(){
      return  confirm;
   }

   public JButton getControladorAtras(){
      return atras;
   }

   public String getNick(){
      return txtNick.getText();
   }
   public String getNif(){
      return txtNif.getText();
   }

   public String getContrasenia1() {
        return new String(contr1.getPassword());
   }

   public String getContrasenia2() {
        return new String(contr2.getPassword());
   }

   public String getOpcionRadioButton() {
      if (si.isSelected()) {
         return "Si";
      } else if (no.isSelected()) {
         return "No";
      } else {
         return ""; 
      }
   }

   /**
    * Método para actualizar los campos de texto y contraseñas.
    */
   public void update(){
      txtNick.setText("");
      txtNick.grabFocus();
      txtNif.setText("");
      contr1.setText(""); 
      contr2.setText("");
   } 
}

