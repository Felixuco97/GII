
package vista.gestor.gestionExpos;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import vista.utils.*;
import java.util.Properties;


import java.time.LocalDate;
import java.time.ZonedDateTime;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilCalendarModel;

import modelo.sistema.Sistema;

import java.util.GregorianCalendar;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
/*REVISAR, COLOCAR BIEN LOS COMPONENTES DE DESCUENTO Y VER QUE ALTERNEN LOS PANELES DEPENDIENDO DE LA OPCION SELECCIONADA*/
public class AltaExpo extends JPanel{
    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JTextField titulo;
    private JComboBox<String> tipo;
    private JTextField precio;
    private JDatePanelImpl fechaInicio;
    private JDatePanelImpl fechaFin;
    private SpinnerModel modelTickets;
    private JTextArea descripcion;
    private JComboBox<String> desc;
    private JComboBox<String> estado;
    private JSpinner nEntradas;

    private JPanel panelInferior;
    private JButton confirm;
    private JButton atras; 


    private JTextField porcentajeCompraAnticipada;
    private JTextField diasAntelacion;
    private JTextField porcentajeClienteFrecuente;
    private JTextField visitas;
    private JTextField numeroDias;

    private JPanel panelDescuentos;
    
    public AltaExpo(Sistema sistema){

        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("ALTA EXPOSICIONES");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);


        /*PANEL MEDIO */
        panelMedio = new JPanel();
        String[] labels = {"Titulo de la exposicion:", "Tipo de exposicion:", "Precio entradas: ", "Fecha de inicio: ", "Fecha de fin: ", "Descuento:", "Numero de entradas: ", "Selecciona el estado: ", "Descripcion: ", "Descuentos: "};
        panelMedio.setLayout(new SpringLayout());
    
        //creamos un jscrollpanel
        JScrollPane scrollPane = new JScrollPane(panelMedio);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        //titulo
        titulo = new JTextField();

        //tipo de exposicion
        String tipos[] = {"Temporal", "Permanente"};  
        tipo= new JComboBox<>(tipos);
        tipo.setSelectedIndex(0);

        //precio
        precio = new JTextField();
        //fecha de inicio

        UtilCalendarModel model = new UtilCalendarModel(); //devuelve calendario, en nuestro caso -> Gregoriano
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        fechaInicio = new JDatePanelImpl(model, p);
        

        //fecha de fin
        UtilCalendarModel model2 = new UtilCalendarModel(); //devuelve calendario, en nuestro caso -> Gregoriano
        Properties p2 = new Properties();
        p2.put("text.today", "Today");
        p2.put("text.month", "Month");
        p2.put("text.year", "Year");
        fechaFin = new JDatePanelImpl(model2, p2);

        //descuento
        String descs[] = {"Cliente frecuente", "Compra anticipada"};    //poner CardLayout para que al elegir Temporal o Permanente se muestre el campo pa rellenar la fecha de fin o no?
        desc= new JComboBox<>(descs);
        desc.setSelectedIndex(1);


        //estado expo
        String ests[] = {"Programada", "En almacen"};    
        estado= new JComboBox<>(ests);

        //numero de entradas
        modelTickets = new SpinnerNumberModel(1, 1, 20, 1);
        nEntradas = new JSpinner(modelTickets);
        //descripcion
        descripcion = new JTextArea();

        //SORTEO    



        //PANEL DESCUENTOS
        panelDescuentos = new JPanel(new CardLayout());


        JPanel panelCompraAnticipada = new JPanel();
        JPanel panelClienteFrecuente = new JPanel();

        porcentajeCompraAnticipada = new JTextField(10);
        diasAntelacion = new JTextField(10);
        porcentajeClienteFrecuente = new JTextField(10);
        visitas = new JTextField(10);
        numeroDias = new JTextField(10);

    
        panelCompraAnticipada.add(new JLabel("Porcentaje:"));
        panelCompraAnticipada.add(porcentajeCompraAnticipada);
        panelCompraAnticipada.add(new JLabel("Dias de antelacion:"));
        panelCompraAnticipada.add(diasAntelacion);

        panelClienteFrecuente.add(new JLabel("Porcentaje:"));
        panelClienteFrecuente.add(porcentajeClienteFrecuente);
        panelClienteFrecuente.add(new JLabel("Visitas:"));
        panelClienteFrecuente.add(visitas);
        panelClienteFrecuente.add(new JLabel("Numero de dias:"));
        panelClienteFrecuente.add(numeroDias);

        //se aniaade los paneles al panel de descuentos
        panelDescuentos.add(panelCompraAnticipada, "Compra anticipada");
        panelDescuentos.add(panelClienteFrecuente, "Cliente frecuente");

        //actionlistener al combobox de descuentoss
        desc.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                CardLayout cl= (CardLayout)(panelDescuentos.getLayout());
                cl.show(panelDescuentos, (String)desc.getSelectedItem());
                repaint();
            }
        });



        JComponent[] components = {titulo, tipo, precio, fechaInicio, fechaFin, desc, nEntradas,estado, descripcion, panelDescuentos};
        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING); // 2º parám= alineación horizontal
            panelMedio.add(l); // Lo añadimos sin ninguna restricción
            l.setLabelFor(components[i]); // El componente que l etiqueta
            panelMedio.add(components[i]); // Lo añadimos sin ninguna restricción
        }


        // Colocar componentes en una cuadrícula compacta
        
        SpringUtilities.makeCompactGrid(panelMedio, 
                                        labels.length, 2,
                                        6, 6, 
                                        6, 6);





        // panelMedio.add(panelDescuentos);
        

        //PANEL INFERIOR
        panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confirm = new JButton("Confirmar");
        atras = new JButton("Atras");

        panelInferior.add(confirm);
        panelInferior.add(atras);


        

        /*AÑADIR LOS SUBPANELES */
        this.add(panelSuperior);
        // this.add(panelMedio);
        this.add(scrollPane);
        // this.add(panelDescuentos);
        this.add(panelInferior);
    }

    //getters
    public String getTitulo() {
        return titulo.getText();
    }    

    public JComboBox<String> getTipoExposicion(){    
        return tipo;
    }

    public String getEstado(){    
        return (String) estado.getSelectedItem();
    }


    public double getPrecio(){ 
        return Double.parseDouble(precio.getText());
    }


    public LocalDate getFechaInicio() {   
        GregorianCalendar gregorianCalendar = (GregorianCalendar)fechaInicio.getModel().getValue();
        ZonedDateTime zonedDateTime = gregorianCalendar.toZonedDateTime();
        LocalDate localDate = zonedDateTime.toLocalDate();
        
        return localDate;
    }

    public LocalDate getFechaFin() {   
        GregorianCalendar gregorianCalendar = (GregorianCalendar)fechaFin.getModel().getValue();
        ZonedDateTime zonedDateTime2 = gregorianCalendar.toZonedDateTime();
        LocalDate localDate2 = zonedDateTime2.toLocalDate();
        
        return localDate2;
    }


    public String getDescuento(){    
        return (String)desc.getSelectedItem();
    }


    public int getNumEntradas() {
        return (int) nEntradas.getValue();
    }

    //getters de los descuentos
    

    public String getDiasAntelacion() {
        return diasAntelacion.getText();
    }


    public String getPorcentajeClienteFrecuente(){ 
        return porcentajeClienteFrecuente.getText();
    }

    public  String getPorcentajeCompraAnticipada(){ 
        return porcentajeCompraAnticipada.getText();
    }

    public String getVisitas() {
        return visitas.getText();
    }

    public String getNumeroDias() {
        return numeroDias.getText();
    }


    public void setControlador(ActionListener c){
        confirm.addActionListener(c);
        atras.addActionListener(c);
    }

    public String getDescripcion(){
        return descripcion.getText();
    }




    public JButton getControladorConfirm(){
        return  confirm;
    }

    public JButton getControladorAtras(){        
        return atras;
    }

    public void update(){
        confirm.grabFocus();
        titulo.setText("");
        tipo.setSelectedIndex(0);

        precio.setText("");
        desc.setSelectedIndex(0);
        descripcion.setText("");
    }
}
