package vista.gestor.gestionObras;

import javax.swing.*;

import modelo.sistema.Sistema;

import java.awt.*;
import java.awt.event.*;

/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class AltaObra extends JPanel{

    private JPanel panelSuperior;
    private JLabel label1;

    private JPanel panelMedio;
    private JButton cuadro, foto, escultura, montajeAudioVisual;

    private JPanel panelInferior;
    private JButton atras;

    /*EN ESTA CLASE HAY QUE USAR CARD LAYOUT PARA QUE SE MUESTREN CIERTOS CAMPOS A RELLENAR DEPENDIENDO DEL TIPO DE OBRA: MONTAJE, FOTO.... 
    YA QUE NO TODOS TIENEN LOS MISMOS ATRIBUTOS PARA RELLENAR (rango temperatura/ humedad, duracion, dimension) */
    public AltaObra(Sistema sistema){

        /*Establecemos layout de este panel */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*PANEL SUPERIOR */
        panelSuperior = new JPanel();
        label1 = new JLabel("GESTIÓN   DE  OBRAS");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
        label1.setFont(font);
        panelSuperior.add(label1);

        //PANEL MEDIO
        panelMedio = new JPanel();
        panelMedio.setLayout(new GridLayout(2, 2));

        cuadro = new JButton("CUADRO");
        foto = new JButton("FOTO");
        escultura = new JButton("ESCULTURA");
        montajeAudioVisual = new JButton("MONTAJE AUDIOVISUAL");

        panelMedio.add(cuadro);
        panelMedio.add(foto);
        panelMedio.add(escultura);
        panelMedio.add(montajeAudioVisual);

        //PANEL INFERIOR
        panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        atras = new JButton("Atras");

        panelInferior.add(atras);

        //AÑADIMOS SUBPANELES
        this.add(panelSuperior);
        this.add(panelMedio);
        this.add(panelInferior);
    }

    public void setControlador(ActionListener c){
        cuadro.addActionListener(c);
        foto.addActionListener(c);
        escultura.addActionListener(c);
        montajeAudioVisual.addActionListener(c);
        atras.addActionListener(c);
    }

    public JButton getControladorCuadro(){

        return cuadro;
    }

    public JButton getControladorFoto(){

        return foto;
    }

    public JButton getControladorEscultura(){

        return escultura;
    }

    public JButton getControladorMontajeAudioVisual(){

        return montajeAudioVisual;
    }

    public JButton getControladorAtras(){
        return atras;
    }
    public void update(){
        atras.grabFocus();
    }
}
