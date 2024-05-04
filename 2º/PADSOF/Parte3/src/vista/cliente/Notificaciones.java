package vista.cliente;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import modelo.sistema.Sistema;
import modelo.usuarios.Notificacion;
import vista.Ventana;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class Notificaciones extends JPanel{
    private JLabel titulo;
    private JScrollBar scrollBar;
    private JScrollPane scrollPane;
    private JButton atras;
    private JPanel panelNoti;

    
    public Notificaciones(Sistema sistema, Ventana frame){
        this.setLayout(new BorderLayout());
        titulo = new JLabel("G E S T I O N  D E  N O T I F I C A C I O N E S", SwingConstants.CENTER);
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 24);
        titulo.setFont(font);
        scrollBar = new JScrollBar(JScrollBar.VERTICAL);
        atras = new JButton("Atras");
        panelNoti = new JPanel();
        scrollPane = new JScrollPane(panelNoti);
        scrollPane.setVerticalScrollBar(scrollBar);

        panelNoti.setLayout(new BoxLayout(panelNoti, BoxLayout.Y_AXIS));

        /*Para leer cada notificacion hay que poner un boton que nos debe redirigir a otro panel donde se muestre la notificacion, al lado de cada notificacion  */
        
        //Implementar logica
        /*Aqui habrá que comprobar si hay notificaciones, sino avisar de que
         * todavía no hay notificaciones. Si las hay, entonces en vez de este bucle
         * habrá que hacer un get para conseguir las notificaciones y sus fechas
         */
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BorderLayout());
        panelBotones.add(atras,BorderLayout.WEST);
        

         // Añadir los componentes al panel principal
        this.add(panelBotones, BorderLayout.SOUTH);
        this.add(titulo, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void setControlador(ActionListener c){
        atras.addActionListener(c);
    }

    public JButton getControladorAtras(){
        return atras;
    }

    public void setNotificaciones(ArrayList<Notificacion> notificaciones){
        panelNoti.removeAll(); //limpiamos
        for (Notificacion n :  notificaciones) {
            JPanel noti = new JPanel();
            noti.add(new JLabel(n.getContenido()));
            noti.add(new JLabel("Asunto " + n.getAsunto()));
            panelNoti.add(noti);
        }
        panelNoti.revalidate();
        repaint();
    }

    public void update(){
        atras.grabFocus();
        panelNoti.removeAll();
        repaint();
    }
}
