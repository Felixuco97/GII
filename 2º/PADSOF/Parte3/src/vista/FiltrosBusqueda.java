package vista;

import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilCalendarModel;

import java.awt.event.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.awt.*;

/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */
public class FiltrosBusqueda extends JPanel {
    private JLabel titulo;
    private JPanel Superior;
    private JPanel Medio;
    private JPanel PanelFiltros;
    private JPanel Inferior;
    private JButton confirm, atras;
    private JComboBox<String> filtros;
    private JPanel PorFecha, PorTipo, PorObra;
    private JDatePanelImpl calendario;
    private JComboBox<String> exposciones;
    private JComboBox<String> obras;
    /**
     * Constructor de la clase FiltrosBusqueda.
     */
    public FiltrosBusqueda(){
        LayoutManager layout = new BorderLayout();
        this.setLayout(layout);

        Superior = new JPanel();
        titulo = new JLabel("Filtros");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        titulo.setFont(font);
        Superior.add(titulo);
        Dimension dim = new Dimension(200, 25);

        String str[] = {"Por Tipo de Obra", "Por Tipo de Exposicion", "Por Fecha"};
        filtros = new JComboBox<>(str);
        filtros.setPreferredSize(dim);
        filtros.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)(PanelFiltros.getLayout());
                cl.show(PanelFiltros, (String)filtros.getSelectedItem());
            }
        });

        

        Medio = new JPanel();
        Medio.setLayout(new BoxLayout(Medio, BoxLayout.Y_AXIS));
        PanelFiltros =  new JPanel(new CardLayout());
        PorFecha = new JPanel();

        PorObra = new JPanel();
        String[] tiposObra = {"Cuadro", "Escultura", "Foto", "Montaje"};
        obras = new JComboBox<>(tiposObra);
        obras.setPreferredSize(dim); 
        PorObra.add(obras);
        PanelFiltros.add(PorObra, "Por Tipo de Obra");
        
        UtilCalendarModel model = new UtilCalendarModel(); //devuelve calendario, en nuestro caso -> Gregoriano
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        calendario = new JDatePanelImpl(model, p);
        PorFecha.add(calendario);
        PanelFiltros.add(PorFecha, "Por Fecha");

        PorTipo = new JPanel();
        String[] tiposExposicion = {"Permanente", "Temporal"};
        exposciones = new JComboBox<>(tiposExposicion);
        exposciones.setPreferredSize(dim);
        PorTipo.add(exposciones);
        PanelFiltros.add(PorTipo, "Por Tipo de Exposicion");


        Inferior = new JPanel();
        confirm = new JButton("Confirmar");
        atras = new JButton("Atras");
        Inferior.add(atras);
        Inferior.add(confirm);

        Medio.add(filtros);
        Medio.add(PanelFiltros);

        this.add(Superior, BorderLayout.NORTH);
        this.add(Medio, BorderLayout.CENTER);
        this.add(Inferior, BorderLayout.SOUTH);
    }

    public String getFiltro(){
        return (String) filtros.getSelectedItem();
    }

    public String getFiltrado(){ 
        if (filtros.getSelectedItem().equals("Por Tipo de Obra")){
            return (String) obras.getSelectedItem();
        }
        else if (filtros.getSelectedItem().equals("Por Tipo de Exposicion")){
            return (String) exposciones.getSelectedItem();
        }
        else{
            GregorianCalendar gregorianCalendar = (GregorianCalendar)calendario.getModel().getValue();
            ZonedDateTime zonedDateTime = gregorianCalendar.toZonedDateTime();
            LocalDate localDate = zonedDateTime.toLocalDate();

            return localDate.toString();
        }
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
        obras.setSelectedIndex(0);
        exposciones.setSelectedIndex(0);
        filtros.setSelectedIndex(0);
    }
}

    
    