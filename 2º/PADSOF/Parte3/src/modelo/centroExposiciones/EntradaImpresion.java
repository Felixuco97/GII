// package modelo.centroExposiciones;
// import java.time.*;
// import java.time.format.DateTimeFormatter;
// import es.uam.eps.padsof.tickets.ITicketInfo;
// /**
//  * @author Felix López, Fernando Sanchez y Andrés M. Alonso
//  */

// public class EntradaImpresion implements ITicketInfo{
//     private String exhibitionCenterName;
//     private String exhibitionName;
//     private int numberOfTickets;
//     private int id;
//     private LocalDateTime ticketDateTime;
//     private double price;
//     private double discount;
//     private double payedPrice;
//     private String picture;

//     public EntradaImpresion(String exhibitionCenterName, String exhibitionName, int numberOfTickets, LocalDateTime ticketDateTime, double price, double discount, double payedPrice, String picture, String id){

//         this.exhibitionCenterName = exhibitionCenterName;
//         this.exhibitionName = exhibitionName;
//         this.numberOfTickets = numberOfTickets;
//         this.ticketDateTime = ticketDateTime;
//         this.price = price;
//         this.discount = discount;
//         this.payedPrice = payedPrice;
//         this.picture = picture;
//         this.id = Integer.parseInt(id);
//     }

//     //Getters

//     public int getIdTicket(){
//         return this.id;
//     }

//     public String getExhibitionCenterName(){
//         return this.exhibitionCenterName;
//     }

//     public String getExhibitionName(){
//         return this.exhibitionName;
//     }

//     public int getNumberOfTickets(){
//         return this.numberOfTickets;
//     }

//     public String getTicketDateTime(){
//         DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//         String out = this.ticketDateTime.format(d);
//         return out;
//     }

//     public double getPrice(){
//         return this.price;
//     }

//     public double getDiscount(){
//         return this.discount;
//     }

//     public double getPayedPrice(){
//         return this.payedPrice;
//     }

//     public String getPicture(){
//         return this.picture;
//     }
// }
