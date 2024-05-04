package vista;

import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;
import java.awt.event.ActionListener;

import vista.utils.*;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */
public class InicioSesion extends JPanel {
    private static final Font TITLE_FONT = new Font(Font.MONOSPACED, Font.BOLD, 24);
    private static final String[] LABELS = {"Introduce tu usuario:", "Introduce tu contraseña:"};

    private JLabel titulo;
    private JTextField jtf;
    private JPasswordField jpf;
    private JButton confirm, atras;

    /**
     * Constructor de la clase InicioSesion.
     * @param sistema Sistema que se utiliza para el inicio de sesión.
     */
    public InicioSesion(Sistema sistema) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        createTitle();
        createInputFields();
        createButtons();
    }

    private void createTitle() {
        titulo = new JLabel("INICIO DE SESION", SwingConstants.CENTER);
        titulo.setFont(TITLE_FONT);
        add(titulo, BorderLayout.NORTH);
    }

    private void createInputFields() {
        JPanel panelMedio = new JPanel(new SpringLayout());

        jtf = new JTextField(20);
        jpf = new JPasswordField(20);

        JComponent[] components = {jtf, jpf};
        for (int i = 0; i < LABELS.length; i++) {
            JLabel l = new JLabel(LABELS[i], JLabel.TRAILING);
            panelMedio.add(l);
            l.setLabelFor(components[i]); 
            panelMedio.add(components[i]); 
        }

        jtf.setPreferredSize(new Dimension(200, 50));
        jpf.setPreferredSize(new Dimension(200, 50));

        SpringUtilities.makeCompactGrid(panelMedio, LABELS.length, 2, 6, 6, 6, 6);
        add(panelMedio, BorderLayout.CENTER);
    }

    private void createButtons() {
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        confirm = new JButton("Confirmar");
        atras = new JButton("Atras");
        panelBotones.add(atras);
        panelBotones.add(confirm);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public String getUsuario() {
        return jtf.getText();
    }

    public String getContrasenia() {
        return new String(jpf.getPassword());
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

    /**
     * Método para actualizar los campos de texto y contraseñas.
     */
    public void update(){
        jtf.setText("");
        jtf.grabFocus();
        jpf.setText("");
    }
}