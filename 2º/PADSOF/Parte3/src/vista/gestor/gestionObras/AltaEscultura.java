package vista.gestor.gestionObras;

import javax.swing.*;

import modelo.centroExposiciones.obras.Seguro;
import modelo.centroExposiciones.obras.obrasFisicas.Dimension;
import modelo.centroExposiciones.obras.obrasFisicas.Rango;
import modelo.sistema.Sistema;
import java.awt.*;
import vista.utils.*;
import java.awt.event.*;

/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class AltaEscultura extends JPanel{
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
    private SpinnerModel modelRandom, modelRandom2, modelRandom3, modelRandom4, modelRandom5, modelRandom6, modelRandom7, modelRandom8;

    private JSpinner tempMin;
    private JSpinner tempMax;
    private JSpinner humMin;
    private JSpinner humMax;

    private JSpinner ancho;
    private JSpinner largo;
    private JSpinner alto;

    private JTextField material;

    private JPanel panelInferior;
    private JButton confirm;
    private JButton atras; 

    /*EN ESTA CLASE HAY QUE USAR CARD LAYOUT PARA QUE SE MUESTREN CIERTOS CAMPOS A RELLENAR DEPENDIENDO DEL TIPO DE OBRA: MONTAJE, FOTO.... 
    YA QUE NO TODOS TIENEN LOS MISMOS ATRIBUTOS PARA RELLENAR (rango temperatura/ humedad, duracion, dimension) */
    public AltaEscultura(Sistema sistema){

        
        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("ALTA ESCULTURAS");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);


        /*PANEL MEDIO */
        panelMedio = new JPanel();
        String[] labels = {"Titulo de la obra:", "Autor: ", "Dueño: ", "Año: ", "Numero Seguro:", "Precio Seguro: ", "Estado: ", "Descripcion: ", "Temp. mínima", "Temp. máxima", "Hum. mínima", "Hum. máxima", "Ancho", "Largo", "Alto", "Material"};
        panelMedio.setLayout(new SpringLayout());
    
        //titulo
        titulo = new JTextField();

        //autor
        autor = new JTextField();

        //dueño
        duenio = new JTextField();

        //fecha
        modelRandom = new SpinnerNumberModel(1, 1, 50, 1);
        anio = new JSpinner(modelRandom);
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
        modelRandom2 = new SpinnerNumberModel(1, 1, 50, 0.1);
        tempMin = new JSpinner(modelRandom2);
        modelRandom3 = new SpinnerNumberModel(1, 1, 50, 0.1);
        tempMax = new JSpinner(modelRandom3);
        //rango humedad
        modelRandom4 = new SpinnerNumberModel(1, 1, 50, 0.1);
        humMin = new JSpinner(modelRandom4);
        modelRandom5 = new SpinnerNumberModel(1, 1, 50, 0.1);
        humMax = new JSpinner(modelRandom5);
        //dimensiones
        modelRandom6 = new SpinnerNumberModel(1, 1, 50, 0.1);
        ancho = new JSpinner(modelRandom6);
        modelRandom7 = new SpinnerNumberModel(1, 1, 50, 0.1);
        largo = new JSpinner(modelRandom7);
        modelRandom8 = new SpinnerNumberModel(1, 1, 50, 0.1);
        alto = new JSpinner(modelRandom8);
        //tecnica
        material = new JTextField();
        //dimension

        JComponent[] components = {titulo, autor, duenio, anio, seguro, precioSeguro, estado, descripcion, tempMin, tempMax, humMin, humMax, ancho, largo, alto, material};
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

    public String getEstado(){    
        return (String) estado.getSelectedItem();
    }

    public String getDescripcion() {
        return descripcion.getText();
    }    

    public String getMaterial(){

        return material.getText();
    }

    public Rango getRangoTemperatura(){

        return new Rango((double)tempMin.getValue(), (double)tempMax.getValue());
    }

    public Rango getRangoHumedad(){

        return new Rango((double)humMin.getValue(), (double)humMax.getValue());
    }

    public Dimension getDimensiones(){

        return new Dimension((double)ancho.getValue(), (double)largo.getValue(), (double)alto.getValue());
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

        //seguro
        seguro.setText("");

        //Estado
        estado.setSelectedIndex(0);

        //descripcion
        descripcion.setText("");

    }
}
