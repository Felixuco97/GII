package modelo.centroExposiciones;
import java.io.Serializable;
import java.time.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import es.uam.eps.padsof.tickets.ITicketInfo;
import es.uam.eps.padsof.tickets.NonExistentFileException;
import es.uam.eps.padsof.tickets.TicketSystem;
import es.uam.eps.padsof.tickets.UnsupportedImageTypeException;
import modelo.centroExposiciones.exposiciones.*;
import modelo.usuarios.Cliente;
/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */

public class Entrada implements Serializable, ITicketInfo{
    private Cliente cliente;
    private String exhibitionCenterName;
    private String exhibitionName;
    private int numberOfTickets;
    private static int global_id = 0;
    private int id;
    private LocalDateTime ticketDateTime;
    private double price;
    private double discount;
    private double payedPrice;
    private String picture;


    public Entrada(String exhibitionCenterName, String exhibitionName, int numberOfTickets, LocalDateTime ticketDateTime, double price, double discount, double payedPrice, String picture, String id, Cliente cl){
        this.exhibitionCenterName = exhibitionCenterName;
        this.exhibitionName = exhibitionName;
        this.numberOfTickets = numberOfTickets;
        this.ticketDateTime = ticketDateTime;
        this.price = price;
        this.discount = discount;
        this.payedPrice = payedPrice;
        this.picture = picture;
        if (id.equals("")){
            this.id = global_id;
            global_id++;
        }
        else
            this.id = Integer.parseInt(id);
        this.cliente = cl;
    }


    /**
     * Genera un PDF para una exposición.
     * @param exp La exposición para la que se va a generar el PDF.
     * @return Verdadero si el PDF se generó correctamente, falso en caso contrario.
     **/
    public boolean generarPdf(Exposicion exp, int numTick){
        double precio = exp.getPrecioEntradas();
        Descuento desc = exp.getDescuento();
        double descuento = 0;
        double payedPrice = precio;
        if (desc != null && desc.validoParaDescuento(cliente, this)){
            descuento = exp.getDescuento().getPorcentaje();
            payedPrice= precio * (1 - descuento);
        }

        ITicketInfo infoTicket = new Entrada("EventHub", this.exhibitionName, numTick, this.getFechaHora(), precio*numTick, descuento, payedPrice*numTick, null, ""+id, cliente);
        try{
            TicketSystem.createTicket(infoTicket, "src/tickets");
        } catch (NonExistentFileException | UnsupportedImageTypeException ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * Genera un código aleatorio de 7 dígitos.
     * 
     * @return El código aleatorio generado.
     */
    public int generarCodigo (){
        Random random = new Random();

        //rango para el código
        int min = 1000000;
        int max = 9999999;

        int codigoAleatorio = random.nextInt(max - min + 1) + min;
        return codigoAleatorio;
    }
    
    //Getters

    public Cliente getCliente() {
        return this.cliente;
    }

    public LocalDateTime getFechaHora(){
        return this.ticketDateTime;
    }

    public DayOfWeek getDiaSemana(){
        return this.ticketDateTime.getDayOfWeek();
    }

    public int getIdTicket(){
        return this.id;
    }

    public String getExhibitionCenterName(){
        return this.exhibitionCenterName;
    }

    public String getExhibitionName(){
        return this.exhibitionName;
    }

    public int getNumberOfTickets(){
        return this.numberOfTickets;
    }

    public String getTicketDateTime(){
        DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String out = this.ticketDateTime.format(d);
        return out;
    }

    public double getPrice(){
        return this.price;
    }

    public double getDiscount(){
        return this.discount;
    }

    public double getPayedPrice(){
        return this.payedPrice;
    }

    public String getPicture(){
        return this.picture;
    }

/* 
    public boolean getVendida(){

        return this.vendida;
    }
/*
    public DayOfWeek getDiaSemana(){

        return this.diaSemana;
    }
*/
    //Setters

    public void setCliente(Cliente cl) {
        this.cliente = cl;
    }

    public void setTicketDateTime(LocalDateTime fch){
        this.ticketDateTime = fch;
    }

    public void setIdTicket(int id){
        this.id = id;
    }

    public void setExhibitionCenterName(String name){
        this.exhibitionCenterName = name;
    }

    public void setExhibitionName(String name){
        this.exhibitionName = name;
    }

    public void setNumberOfTickets(int num){
        this.numberOfTickets = num;
    }

    public void setPrice(double p){
        this.price = p;
    }

    public void setDiscount(double d){
        this.discount = d;
    }

    public void getPayedPrice(double py){
        this.payedPrice = py;
    }

    public void getPicture(String pict){
        this.picture = pict;
    }
}


