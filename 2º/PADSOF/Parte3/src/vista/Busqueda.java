package vista;

import javax.swing.*;

import modelo.centroExposiciones.CentroDeExposiciones;
import modelo.centroExposiciones.exposiciones.Exposicion;
import modelo.sistema.Sistema;

import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;

/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
*/
public class Busqueda extends JPanel{
    
    private JTextField busqueda; 
    private JButton filtrar;
    private JPanel resultados;
    private JScrollPane scrollPane;
    private JScrollBar scrollBar;
    private JButton buscar, atras, registrarse;
    private JButton comprar;
    private JPanel inferior;
    private Ventana frame;

    /**
     * Constructor de la clase Busqueda.
     * @param sistema Sistema que se utiliza en la interfaz de búsqueda.
     * @param frame Ventana principal de la aplicación.
     */
    public Busqueda(Sistema sistema, Ventana frame){
        LayoutManager layout = new BorderLayout();
        this.setLayout(layout);

        // Cambio: Usar BorderLayout para el panel superior
        JPanel top = new JPanel(new BorderLayout()); 
        busqueda = new JTextField();
        busqueda.setMinimumSize(new Dimension(200, 20));
        busqueda.setPreferredSize(new Dimension(200, 20)); 
        top.add(busqueda, BorderLayout.CENTER);
        busqueda.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        filtrar = new JButton("Filtrar");
        top.add(filtrar, BorderLayout.WEST);
        buscar = new JButton("Buscar");
        top.add(buscar, BorderLayout.EAST);
        buscar.grabFocus();

        atras = new JButton("Atras");
        registrarse = new JButton("Registrarse");

        resultados = new JPanel();
        resultados.setLayout(new BoxLayout(resultados, BoxLayout.Y_AXIS));

        CentroDeExposiciones centro = sistema.getCentro();

        Busqueda vista = this;

        for (Exposicion e : centro.getExposiciones()) 
        {
            comprar = new JButton("Comprar"); 
            comprar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    if (frame.getUsuarioActual() != null){
                        frame.getVistaCompraEntradas().setSelectedItemExpo(e.getNombre());
                        frame.mostrarPanel("ComprarEntrada");
                    }
                    else {
                        JOptionPane.showMessageDialog(vista, "Se tiene que estar registrado para poder comprar", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            JPanel res = new JPanel();
            res.add(new JLabel(e.toString()));
            res.add(comprar);
            resultados.add(res);
        }

        scrollBar = new JScrollBar();
        scrollPane = new JScrollPane(resultados);
        scrollPane.setVerticalScrollBar(scrollBar);

        // Cambio: Usar FlowLayout para el panel inferior
        inferior = new JPanel(new FlowLayout()); // Cambiado aquí
        if (frame.getUsuarioActual() == null){
            inferior.add(registrarse);
        }
        inferior.add(atras);

        this.add(top, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(inferior, BorderLayout.SOUTH);
    }

    public void setControlador(ActionListener c){
        buscar.addActionListener(c);
        atras.addActionListener(c);
        filtrar.addActionListener(c);
        registrarse.addActionListener(c);
    }

    public JButton getControladorBuscar(){
        return  buscar;
    }

    public JButton getControladorRegistrarse(){
        return  registrarse;
    }

    public JButton getControladorAtras(){
        return atras;
    }

    public JButton getControladorFiltrar(){
        return filtrar;
    }

    public String getBusqueda(){
        return busqueda.getText();
    }

    public void setResultados(ArrayList<Exposicion> expos){
        resultados.removeAll(); //limpiamos
        for (Exposicion e: expos){
            JButton comprar = new JButton("Comprar");
            comprar.addActionListener(new ActionListener() { //Al ser un boton tan pequeño no creamos un control
                public void actionPerformed(ActionEvent ae) {
                        frame.getVistaCompraEntradas().setSelectedItemExpo(e.getNombre());
                        frame.mostrarPanel("ComprarEntrada");
                } 
            });
            JPanel res = new JPanel();
            res.add(new JLabel(e.toString()));
            res.add(comprar);
            resultados.add(res);
        }
        resultados.revalidate();
        repaint();
    }
    
    /**
     * Método para actualizar los campos de texto y contraseñas.
     */
    public void update(){
        busqueda.setText("");
        buscar.grabFocus();
        repaint();
    }
}
    