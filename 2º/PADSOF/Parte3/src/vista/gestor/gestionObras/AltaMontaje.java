package vista.gestor.gestionObras;

import javax.swing.*;

import modelo.centroExposiciones.obras.Seguro;
import modelo.sistema.Sistema;
import java.awt.*;
import vista.utils.*;
import java.awt.event.*;
import java.time.LocalTime;

/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class AltaMontaje extends JPanel{
    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JTextField titulo;
    private JTextField autor;
    private JTextField duenio;
    private JSpinner anio;
    private JTextField seguro;
    private JTextField precioSeguro;   //no se si se deberia pedir el precio del seguro pero es para la clase Seguro que pide al dar de alta una nueva obra
    private JTextArea descripcion;
    private JComboBox<String> estado;
    private SpinnerModel modelTickets;

    private JComboBox<String> idiomas;
    private JSpinner duracion;

    private JPanel panelInferior;
    private JButton confirm;
    private JButton atras; 

    /*EN ESTA CLASE HAY QUE USAR CARD LAYOUT PARA QUE SE MUESTREN CIERTOS CAMPOS A RELLENAR DEPENDIENDO DEL TIPO DE OBRA: MONTAJE, FOTO.... 
    YA QUE NO TODOS TIENEN LOS MISMOS ATRIBUTOS PARA RELLENAR (rango temperatura/ humedad, duracion, dimension) */
    public AltaMontaje(Sistema sistema){

        
        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("ALTA OBRAS");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);


        /*PANEL MEDIO */
        panelMedio = new JPanel();
        String[] labels = {"Titulo de la obra:", "Autor: ", "Dueño: ", "Año: ", "Numero Seguro:", "Precio Seguro: ", "Estado: ", "Idioma: ", "Duracion: ", "Descripcion: "};
        panelMedio.setLayout(new SpringLayout());
    
        //titulo
        titulo = new JTextField();

        //autor
        autor = new JTextField();

        //dueño
        duenio = new JTextField();

        //fecha
        modelTickets = new SpinnerNumberModel(1, 1, 200, 1);
        anio = new JSpinner(modelTickets);
        //seguro
        seguro = new JTextField();  

        //precio seguro
        precioSeguro = new JTextField();  

 
        //Estado
        String estados[] = {"Almacen", "Reparacion", "Exposicion", "Fuera del Centro"};   
        estado= new JComboBox<>(estados);

        //descripcion
        descripcion = new JTextArea();

        //rango temperatura

        //rango humedad

        //duracion (para montajes audiovisuales) //usar card layout para ocultarlo dependiendo del tipo de obra
        duracion = new JSpinner(modelTickets);


        //idioma (para montajes audiovisuales)
        String idioms[] = {"Español", "English", "Français", "Português", "Italiano"};   
        idiomas= new JComboBox<>(idioms);

        //dimension
        //rango
        //temp
        //humedad
        //disenio


        //material

        JComponent[] components = {titulo, autor, duenio, anio, seguro, precioSeguro, estado, idiomas, duracion, descripcion};
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

    //getters
    public String getTitulo() {
        return titulo.getText();
    }    

    public String getDuenio() {
        return duenio.getText();
    }    

    public String getAutor() {
        return autor.getText();
    }    


    public int getAnio() {   //Igual no conviene poner el año con un JSpinner
        return (int) anio.getValue();
    }

    public Seguro getSeguro() {

        int numero = Integer.parseInt(seguro.getText());
        double precio = Double.parseDouble(precioSeguro.getText());
        
        return new Seguro(numero, precio);
    }


    public String getIdioma(){    
        return (String) idiomas.getSelectedItem();
    }
/*
    public int getDuracion() {   //Igual no conviene poner la duracion con un JSpinner
        return (int) duracion.getValue();
    }
*/
    public String getEstado(){    
        return (String) estado.getSelectedItem();
    }

    public String getDescripcion() {
        return descripcion.getText();
    }    

    public LocalTime getDuracion(){

        return (LocalTime)duracion.getValue();
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

    public void update(){
        confirm.grabFocus();
        
        //titulo
        titulo.setText("");

        //autor
        autor.setText("");

        //dueño
        duenio.setText("");;

        //fecha
       // anio.setText("");

        //seguro
        seguro.setText("");

        //Estado
        estado.setSelectedIndex(0);

        //descripcion
        descripcion.setText("");

    }
}
