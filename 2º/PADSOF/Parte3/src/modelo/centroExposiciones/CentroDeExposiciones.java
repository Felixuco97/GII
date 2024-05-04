package modelo.centroExposiciones;

import java.util.*;

import modelo.centroExposiciones.exposiciones.*;
import modelo.centroExposiciones.obras.*;
import modelo.centroExposiciones.obras.obrasFisicas.*;
import modelo.centroExposiciones.salas.*;
import modelo.centroExposiciones.sorteos.*;
import modelo.usuarios.*;
import modelo.sistema.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.time.*;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */

public class CentroDeExposiciones implements Serializable{
   
    private LocalTime horaApertura;
    private LocalTime horaCierre;
    private HashMap<String, Exposicion> exposiciones = new HashMap<>();
    private HashMap<String, Exposicion> exposicionesConstruccion = new HashMap<>();
    private ArrayList<Obra> obras = new ArrayList<>(); 
    private ArrayList<Sala> salas = new ArrayList<>();
    private ArrayList<Sorteo> sorteos = new ArrayList<>();


    protected CentroDeExposiciones(){}
    public CentroDeExposiciones(LocalTime horaApertura, LocalTime horaCierre){

        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }

    

    /**
    * Este método busca exposiciones basándose en un filtro y un valor de filtrado proporcionados.
    * @param str El término de búsqueda para el nombre o la descripción de la exposición.
    * @param filtro El filtro a aplicar. Puede ser "Fecha", "Obra" o "Exposicion".
    * @param filtrado El valor específico para el filtro. Para "Fecha", es una fecha específica. Para "Obra", puede ser "Cuadro", "Fotografía", "Escultura" o "Montaje Audiovisual". Para "Exposicion", puede ser "Temporal" o "Permanente".
    * @return Un ArrayList de objetos Exposicion que coinciden con los criterios de búsqueda.
    **/
    public ArrayList<Exposicion> buscarExposiciones(String str, String filtro, String filtrado){
        ArrayList<Exposicion> encontradas = new ArrayList<>();
        for(Exposicion e: this.exposiciones.values()){
           
            if(filtro != null){
                if(filtro.equalsIgnoreCase("Por Fecha")){
                    if(e.getFechaInicio().equals(LocalDate.parse(filtrado)))
                        encontradas.add(e); 
                }

                else if(filtro.equalsIgnoreCase("Por Tipo de Obra")){  
                    for (Obra obras : e.getObras()){
                        if(obras.comparar("Obra", filtrado)){
                            encontradas.add(e);
                            break;
                        }
                    }
                }
                else if(filtro.equals("Por Tipo de Exposicion")){
                    if (e.comparar("Exposicion", filtrado))
                        encontradas.add(e);
                }

            } else {
                if(e.getNombre().toLowerCase().contains(str.toLowerCase()) || e.getDescripcion().toLowerCase().contains(str.toLowerCase()))
                    encontradas.add(e);
            }
        }

        return encontradas;
    }

    public Exposicion getExpositionByName(String str){
        return exposiciones.get(str);
    }

    public Set<String> getExpoNames(){
        return exposiciones.keySet();
    }

    /**
    * Este método configura el horario de apertura y cierre.
    * @param nuevaHorAper La nueva hora de apertura.
    * @param nuevaHorCier La nueva hora de cierre.
    **/
    public void configurarHorario(LocalTime nuevaHorAper, LocalTime nuevaHorCier){

        if(nuevaHorAper != null){

            setHoraApertura(nuevaHorAper);
        }

        if(nuevaHorCier != null){

            setHoraCierre(nuevaHorCier);
        }
    }

    /**
    * Este método elimina exposiciones temporales que están en almacén.
    * @param exp Exposicion a eliminar
    **/
    public void eliminarExposiciones(Temporal exp) throws Exception{
        if (exp.getFechaFin().isAfter(LocalDate.now())){
            for (Exposala sl :exp.getSalas()){
                sl.unificarSala();
                ArrayList<Obra> obs = sl.getObras();
                for (Obra o : obs){
                    o.moverToAlmacen();
                    obras.add(o);
                }
                ArrayList<Obra> listaObras = new ArrayList<>();
                Obra[] arrayObras = new Obra[listaObras.size()];
                arrayObras = listaObras.toArray(arrayObras);
                try{
                    sl.removeObras(arrayObras);
                } catch (Exception e){
                    e.printStackTrace();
                }
                Sala sala = new Sala(sl.getNombre(), sl.getDimension(), sl.getAforo(), sl.getTomasCorriente(), sl.getTemperatura(), sl.getHumedad());
                salas.add(sala);
                sl = null;
            }
            this.exposiciones.remove(exp.getNombre());
            exp = null;
        }
        else
            throw new Exception("Todavia no ha llegado la fecha de fin");
    }

    /**
    * Este método calcula la duración de la jornada en horas, minutos y segundos.
    * @return La duración de la jornada en formato "horas:minutos:segundos".
    **/
    public String calcularJornada(){

        Duration dif;
        String dur = "";

        dif = Duration.between(this.horaApertura, this.horaCierre);
    
        dur += dif.toHours()+":"+dif.toMinutes()%60+":";

        if((dif.toSeconds()%60) < 10){
            dur += "0";
        }

        dur += dif.toSeconds()%60;

        return dur;
    }


    /**
    * Este método añade exposiciones al array exposicionesConstruccion
    * @param array Un array de objetos Exposicion a poner EnConstruccion
    **/
    public void construccionExposicion(Exposicion... expos){
        for (Exposicion e : expos){             //
            String nombre = e.getNombre();
            if (e.getEstado() == EstadoExpo.EN_CONSTRUCCION){
                if(!exposicionesConstruccion.containsKey(nombre)){
                    this.exposicionesConstruccion.put(nombre, e);
                } else {
                    System.err.println("No puede haber dos exposiciones con el mismo nombre");
                }
            }
        }
    }

    
    /**
    * Este método da de alta nuevas exposiciones.
    * @param array Un array de objetos Exposicion a dar de alta.
    **/
    public void darDeAltaExposiciones(Sistema sistema, Exposicion... array){
        for (Exposicion e: array){
            String nombre = e.getNombre();
            if (!exposiciones.containsKey(nombre))
                this.exposiciones.put(nombre, e);
            else{
                System.err.println("No puede haber dos exposiciones con el mismo nombre");
            }
            
        Notificacion notificacion = new Notificacion(TiposNotificacion.NUEVA_EXPOSICION, "Se ha dado de alta una nueva exposicion: " + e.getNombre(), LocalDate.now(), "Nueva exposición!!");
        for(Cliente c: sistema.getClientes()){
          c.actualizarBandejaNotificaciones(notificacion);
        }
        }
    }

    /**
    * Este método da de alta nuevas obras.
    * @param array Un array de objetos Obra a dar de alta.
    **/
    public void darDeAltaObras(Obra... array){

        for(Obra o: array){
            
            this.obras.add(o);
        }
    }

    /**
    * Este método da de alta nuevas salas.
    * @param sorteos Un array de objetos Sorteo a dar de alta.
    **/
    public void darDeAltaSorteo(Sorteo... sorteos){
        for(Sorteo o: sorteos){    
            this.sorteos.add(o);
        }
    }

    /**
    * Este método da de alta nuevas salas.
    * @param array Un array de objetos Sala a dar de alta.
    **/
    public void darDeAltaSala(Sala... array){

        for(Sala o: array){
            
            this.salas.add(o);
        }
    }

    
   /**
    * Este método desglosa las estadísticas por día.
    * @return Un texto con el número de entradas vendidas y los beneficios por cada día de la semana.
    **/
    public ArrayList<String> desglosarEstadisticasPorDia(){
        ArrayList<String> result = new ArrayList<>();
        String texto = "";
        int nEntrsVendidas;
        double facturacion;

        for (DayOfWeek d: DayOfWeek.values()){
            nEntrsVendidas = 0;
            facturacion = 0;
            texto += d.name() + ":\n";

            for(Exposicion e: this.exposiciones.values()){

                nEntrsVendidas += e.getEstadisticas().getNumEntradasVendidas_dia(d);
                facturacion += e.getEstadisticas().getBeneficios_dia(d);   
            }

            texto += "---Nº de entradas vendidas: "+nEntrsVendidas+"\n";
            texto += "---Beneficios: "+facturacion;
            result.add(texto);
            texto = "";
        }

        return result;
    }


   /**
    * Este método desglosa las estadísticas por exposición.
    * @return Un texto con el número de entradas vendidas y los beneficios por cada exposición.
    **/
    public ArrayList<String> desglosarEstadisticasPorExposicion(){
        ArrayList<String> result = new ArrayList<>();
        String texto = "";

        for(Exposicion e: this.exposiciones.values()){

            texto += "EXPOSICIÓN: "+e.getNombre()+"\n";
            texto += "---Nº de entradas vendidas: "+e.getEstadisticas().getNumEntradasVendidas()+"\n";
            texto += "---Beneficios: "+e.getEstadisticas().getBeneficios();
            result.add(texto);
            texto = "";
        }

        return result;
    }

    //Getters

    public LocalTime getHoraApertura(){

        return this.horaApertura;
    }

    public LocalTime getHoraCierre(){

        return this.horaCierre;
    }

    public ArrayList<Exposicion> getExposiciones(){
        return new ArrayList<Exposicion>(exposiciones.values());
    }

    public ArrayList<Obra> getObras(){

        return this.obras;
    }

    public ArrayList<Sala> getSalas(){

        return this.salas;
    }

    public ArrayList<Sala> getAllSalas(){
        ArrayList<Sala> allSalas = new ArrayList<>();
        allSalas.addAll(salas);
        for (Exposicion e : exposiciones.values()){
            allSalas.addAll(e.getAllSalas());   
        }
        return allSalas;
    }

    public ArrayList<Sorteo> getSorteos(){

        return this.sorteos;
    }

    public ArrayList<Temporal> getExposTemporales(){
        ArrayList<Temporal> temp = new ArrayList<>();
        for (Exposicion e : exposiciones.values()){
            Temporal t = e.getTemporales();
            if (t != null)
                temp.add(t);
        }
        return temp;
    }

    public ArrayList<Permanente> getExposPermanentes(){
        ArrayList<Permanente> perm = new ArrayList<>();
        for (Exposicion e : exposiciones.values()){
            Permanente t = e.getPermanentes();
            if (t != null)
                perm.add(t);
        }
        return perm;
    }

    //Setters

    public void setHoraApertura(LocalTime hAp){

        this.horaApertura = hAp;
    }

    public void setHoraCierre(LocalTime hCi){

        this.horaCierre = hCi;
    }

    public void setExposiciones(ArrayList<Exposicion> expos){
        for (Exposicion e: expos){
            String nombre = e.getNombre();
            if (!exposiciones.containsKey(nombre))
                this.exposiciones.put(nombre, e);
            else{
                System.err.println("No puede haber dos exposiciones con el mismo nombre");
            }
        }
    }

    public void setObras(ArrayList<Obra> obrs){

        this.obras = obrs;
    }

    /**
    * Lee de un fichero las obras inciales del Centro de exposiciones
    * @param fileName nombre del fichero
    */
    public void parse(String fileName) throws IOException{
      BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
      String linea;
      
      while ((linea = file.readLine()) != null){
        String lineas[] = linea.split(";");
        while (lineas.length < 18) {
            lineas = Arrays.copyOf(lineas, lineas.length + 1);
            lineas[lineas.length - 1] = "";
        }
        if (lineas[0].equals("CUADRO")){
          Cuadro cuad; 
          cuad = Cuadro.parse(lineas);
          this.darDeAltaObras(cuad);
        }
        else if (lineas[0].equals("ESCULTURA")){
          Escultura escult;
          escult = Escultura.parse(lineas);
          this.darDeAltaObras(escult);
        }
        else if (lineas[0].equals("FOTOGRAFIA")){
          Fotografia foto;
          foto = Fotografia.parse(lineas);
          this.darDeAltaObras(foto);
        }
        else if (lineas[0].equals("AUDIOVISUAL")){
          MontajeAudiovisual montaje;
          montaje = MontajeAudiovisual.parse(lineas);
          this.darDeAltaObras(montaje);
        }
      }
      file.close();
    }

    /**
    * Lee de un fichero las salas inciales del Centro de exposiciones
    * @param fileName nombre del fichero
    */
    public ArrayList<Exposala> readSalas (String fileName) throws IOException{
        BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String linea;
        ArrayList<Exposala> expoSalas = new ArrayList<>();
        while ((linea = file.readLine()) != null){
            String lineas[] = linea.split(";");
            if (lineas[0].equals("SALA")){
                Dimension dim = new Dimension(Integer.parseInt(lineas[2]) * 100, Integer.parseInt(lineas[3]) * 100, Integer.parseInt(lineas[4]) * 100); 
                Sala sala = new Sala(lineas[1], dim, Integer.parseInt(lineas[5]), Integer.parseInt(lineas[6]), Integer.parseInt(lineas[7]), Integer.parseInt(lineas[8]));
                this.darDeAltaSala(sala);
            }
            if (lineas[0].equals("EXPOSALA")){
                Dimension dim = new Dimension(Integer.parseInt(lineas[2]) * 100, Integer.parseInt(lineas[3]) * 100, Integer.parseInt(lineas[4]) * 100);
                Exposala expoSala = new Exposala(lineas[1], dim, Integer.parseInt(lineas[5]), Integer.parseInt(lineas[6]), Integer.parseInt(lineas[7]), Integer.parseInt(lineas[8]));
                expoSalas.add(expoSala);
            }
        }
        file.close();
        return expoSalas;
    }

    public void readSorteos(String fileName) throws IOException{
        BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String linea;
        while ((linea = file.readLine()) != null){
            String lineas[] = linea.split(";");
            if (lineas[0].equals("FIJO")){
                SorteoFechaFija sort = new SorteoFechaFija(Integer.parseInt(lineas[4]) , Integer.parseInt(lineas[5])  , LocalDateTime.parse(lineas[6]), LocalDateTime.parse(lineas[7]),Integer.parseInt(lineas[8]), this.getExpositionByName(lineas[9]), LocalDateTime.parse(lineas[1]));
                this.darDeAltaSorteo(sort);
            }
            else if (lineas[0].equals("INTERVALO")){
                SorteoIntervalo sort = new SorteoIntervalo(Integer.parseInt(lineas[4]) , Integer.parseInt(lineas[5])  , LocalDateTime.parse(lineas[6]), LocalDateTime.parse(lineas[7]),Integer.parseInt(lineas[8]), this.getExpositionByName(lineas[9]), LocalDateTime.parse(lineas[2]), LocalDateTime.parse(lineas[3]));
                this.darDeAltaSorteo(sort);
            }
            else if (lineas[0].equals("LIBRE")){
                SorteoFechaLibre sort = new SorteoFechaLibre(Integer.parseInt(lineas[4]) , Integer.parseInt(lineas[5])  , LocalDateTime.parse(lineas[6]), LocalDateTime.parse(lineas[7]),Integer.parseInt(lineas[8]), this.getExpositionByName(lineas[9]));
                this.darDeAltaSorteo(sort);
            }
        }
        file.close(); 
    }

    public void readExposicion (Sistema sistema,String fileName, ArrayList<Exposala> exposalas) throws IOException {
        BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String linea;
        while ((linea = file.readLine()) != null){
            String lineas[] = linea.split(";");
            if (lineas[0].equals("PERMANENTE")){
                Descuento desc = null;
                if (lineas[8].equals("FRECUENTE")) {
                    desc = new DescuentoClienteFrecuente(Double.parseDouble(lineas[9]), Integer.parseInt(lineas[10]),Integer.parseInt(lineas[11]));
                }
                else if (lineas[8].equals("ANTICIPADA")) {
                    desc = new DescuentoCompraAnticipada(Double.parseDouble(lineas[9]), Integer.parseInt(lineas[12]));
                }
                Permanente expo = new Permanente(lineas[1], lineas[2], LocalDate.parse(lineas[3]), Integer.parseInt(lineas[5]),Double.parseDouble(lineas[6]), desc, EstadoExpo.fromString(lineas[7]), null);
               
                if (lineas.length > 12) {
                    String[] subconjunto = new String[lineas.length - 13];
                    System.arraycopy(lineas, 13, subconjunto, 0, subconjunto.length);
                    parsearContenido(subconjunto, expo, exposalas);
                }
                this.darDeAltaExposiciones(sistema, expo);
            }

            else if (lineas[0].equals("TEMPORAL")){
                Descuento desc = null;
                if (lineas[8].equals("FRECUENTE")) {
                    desc = new DescuentoClienteFrecuente(Double.parseDouble(lineas[9]), Integer.parseInt(lineas[10]),Integer.parseInt(lineas[11]));
                }
                else if (lineas[8].equals("ANTICIPADA")) {
                    desc = new DescuentoCompraAnticipada(Double.parseDouble(lineas[9]), Integer.parseInt(lineas[12]));
                }
                Temporal expo = new Temporal(lineas[1], lineas[2], LocalDate.parse(lineas[3]), Integer.parseInt(lineas[5]), LocalDate.parse(lineas[4]),Double.parseDouble(lineas[6]), desc, EstadoExpo.fromString(lineas[7]), null);

                if (lineas.length > 12) {
                    String[] subconjunto = new String[lineas.length - 13];
                    System.arraycopy(lineas, 13, subconjunto, 0, subconjunto.length);
                    parsearContenido(subconjunto, expo, exposalas);
                }
                this.darDeAltaExposiciones(sistema, expo);
            }
        }
        file.close(); 
    }
    
    public void parsearContenido(String[] contenido, Exposicion expo, ArrayList<Exposala> expoSalas) {
        for (String sala : contenido) {
            String[] partes = sala.split(":");
            String[] obrasString = partes[1].split("\\|"); 
            Exposala expoSala = null;
            String name[] = partes[0].split("-");
            boolean subSala = false;
            
            for (Exposala sl : expoSalas){
                if (sl.getNombre().equals(name[0]) && partes[0].contains("-")){
                    sl.addSubsala(new Exposala(partes[0], sl.getDimension(), sl.getAforo(), sl.getTomasCorriente(), sl.getTemperatura(), sl.getHumedad()));
                    expoSala = sl;
                    subSala = true;
                }
                else if (sl.getNombre().equals(partes[0])) {//habra que comprobar que en el dividir se le poner el nombre indicado, y en el readSala si se le un nombre de estilo subsala que se guarde dentro de subsalas de la padre
                    expoSala = sl;
                } 
            }

            ArrayList<Obra> obrasElim = new ArrayList<>();
            for (String obs : obrasString) {
                for (Obra o: obras){
                    if (o.getTitulo().equals(obs)){
                        if (expoSala.addObras(o)){
                            obrasElim.add(o); 
                            break;
                        }   
                    }
                }
            }
            
            obras.removeAll(obrasElim);
            if (!subSala)
                expo.aniadirSalas(expoSala);
        }
    }
}

