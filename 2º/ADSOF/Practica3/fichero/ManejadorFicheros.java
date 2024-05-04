/**
 * Clase encargada de manejar las operaciones de lectura y escritura de datos de ingredientes, platos y menús a un fichero.
 * 
 * @author Felix felix.lopezl@estudiante.uam.es
 * @author Alex alex.pinneiro@estudiante.uam.es
 * 
 */
package fichero;

import java.util.*;
import objetos.*;
import objetos.infoNutricional.*;
import java.io.*;
import objetos.enums.*;

public class ManejadorFicheros {

    private static Map<String, Ingrediente> ingredientes = new LinkedHashMap<>();
    private static Map<String, Plato> platos = new LinkedHashMap<>();
    private static ArrayList<Menu> menus = new ArrayList<>();

    /**
     * Escribe la información de una lista de menús en un fichero.
     * 
     * @param desc Fichero donde se guardará la información.
     * @param menus La lista de menús a guardar en el fichero.
     */
    public static void guardarFichero(String desc, List<Menu> menus){

        try{

        BufferedWriter bufferEscritor = new BufferedWriter(new FileWriter(desc));
        ArrayList<Ingrediente> ingredientes = new ArrayList<>();
        ArrayList<Plato> platos = new ArrayList<>();
        ArrayList<Menu> mens = new ArrayList<>();

        for(Menu m: menus){

            mens.add(m);

            for(Plato p: m.getPlatos()){

                if(!platos.contains(p)){
                    platos.add(p);

                    for(Ingrediente i: p.getIngredientes().keySet()){

                        if(!ingredientes.contains(i)){
                            ingredientes.add(i);

                            if(i.getInfoNutricional() instanceof InfoNutricionalPeso){
                                bufferEscritor.write("INGREDIENTE_PESO;");
                            }
                            if(i.getInfoNutricional() instanceof InfoNutricionalUnidad){
                                bufferEscritor.write("INGREDIENTE_UNIDAD;");
                            }

                            if(i.getTipo() == null){
                                bufferEscritor.write(i.getNombre()+";"+i.getOtroTipo()+";");
                            } else{
                                bufferEscritor.write(i.getNombre()+";"+i.getTipo()+";");
                            }
                            InfoNutricional inf = i.getInfoNutricional();
                            bufferEscritor.write(inf.getCalorias()+";"+inf.getHidratosCarbono()+";"+inf.getGrasaTotal()+";"+inf.getGrasasSaturadas()+";"
                                                +inf.getProteinas()+";"+inf.getAzucares()+";"+inf.getFibra()+";"+inf.getSodio()+";");
                            ArrayList<Alergeno> als = i.getListaAlergenos();
                                
                            if(als.contains(Alergeno.GLUTEN)){
                                bufferEscritor.write("S;");
                            } else bufferEscritor.write("N;");
                            if(als.contains(Alergeno.LACTOSA)){
                                bufferEscritor.write("S;");
                            } else bufferEscritor.write("N;");
                            if(als.contains(Alergeno.HUEVO)){
                                bufferEscritor.write("S;");
                            } else bufferEscritor.write("N;");
                            if(als.contains(Alergeno.FRUTOS_SECOS)){
                                bufferEscritor.write("S");
                            } else bufferEscritor.write("N");

                            bufferEscritor.write("\n");
                        }
                    }
                    
                    bufferEscritor.write("PLATO;"+p.getNombre());

                    for(Plato pl: p.getPlatos()){

                        bufferEscritor.write(";PLATO "+pl.getNombre());
                    }

                    for(Ingrediente ing: p.getIngredientes().keySet()){
                        bufferEscritor.write(";INGREDIENTE "+ing.getNombre()+":"+p.getIngredientes().get(ing));
                    }

                    bufferEscritor.write("\n");
                }
            }

            bufferEscritor.write("MENU");

            for(Plato p: m.getPlatos()){

                bufferEscritor.write(";"+p.getNombre());
            }

            bufferEscritor.write("\n");
        }

        bufferEscritor.close();

        } catch(IOException e){

            e.printStackTrace();
        }
    }

    /**
     * Lee la información de ingredientes, platos y menús de un fichero y los almacena.
     * 
     * @param desc Fichero de donde se leerá la información.
     */
    public static void leerFichero(String desc){

        try{

        BufferedReader bufferLector = new BufferedReader(new InputStreamReader(new FileInputStream(desc)));

        String linea;
        String[] palabras;

        ingredientes = new LinkedHashMap<>();
        platos = new LinkedHashMap<>();
        menus = new ArrayList<>();

        while((linea = bufferLector.readLine()) != null){

            palabras = linea.split("[;:]|INGREDIENTE\\s");

            if(palabras[0].equals("INGREDIENTE_PESO") || palabras[0].equals("INGREDIENTE_UNIDAD")){

                String info = palabras[0];
                String nombre = palabras[1];
                String tipo = palabras[2];

                double calorias = Double.parseDouble(palabras[3]);
                double hidratosCarbono = Double.parseDouble(palabras[4]);
                double grasa = Double.parseDouble(palabras[5]);
                double saturadas = Double.parseDouble(palabras[6]);
                double proteinas = Double.parseDouble(palabras[7]);
                double azucares = Double.parseDouble(palabras[8]);
                double fibra = Double.parseDouble(palabras[9]);
                double sodio = Double.parseDouble(palabras[10]);

                String gluten = palabras[11];
                String lactosa = palabras[12];
                String huevos = palabras[13];
                String frutosSecos = palabras[14];

                TipoIngrediente tipoIng = null;

                if(tipo.equals("CARNE")){
                    tipoIng = TipoIngrediente.CARNE;
                }
                if(tipo.equals("PESCADO")){
                    tipoIng = TipoIngrediente.PESCADO;
                }
                if(tipo.equals("FRUTA_VERDURA")){
                    tipoIng = TipoIngrediente.FRUTA_VERDURA;
                }
                if(tipo.equals("LEGUMBRE")){
                    tipoIng = TipoIngrediente.LEGUMBRE;
                }
                if(tipo.equals("CEREAL")){
                    tipoIng = TipoIngrediente.CEREAL;
                }
                if(tipo.equals("HUEVO")){
                    tipoIng = TipoIngrediente.HUEVO;
                }
                if(tipo.equals("LACTEO")){
                    tipoIng = TipoIngrediente.LACTEO;
                }

                Alergeno glut = null;
                Alergeno lact = null;
                Alergeno huev = null;
                Alergeno frutSecos = null;

                if(gluten.equals("S")){
                    glut = Alergeno.GLUTEN;
                }
                if(lactosa.equals("S")){
                    lact = Alergeno.LACTOSA;
                }
                if(huevos.equals("S")){
                    huev = Alergeno.HUEVO;
                }
                if(frutosSecos.equals("S")){
                    frutSecos = Alergeno.FRUTOS_SECOS;
                }

                if(info.equals("INGREDIENTE_PESO")){
                    ingredientes.put(nombre, new Ingrediente(nombre, tipoIng, new InfoNutricionalPeso(calorias, hidratosCarbono, grasa, saturadas, proteinas, azucares, fibra, sodio)).tieneAlergenos(glut, lact, huev, frutSecos));
                }
                if(info.equals("INGREDIENTE_UNIDAD")){
                    ingredientes.put(nombre, new Ingrediente(nombre, tipoIng, new InfoNutricionalUnidad(calorias, hidratosCarbono, grasa, saturadas, proteinas, azucares, fibra, sodio)).tieneAlergenos(glut, lact, huev, frutSecos));      
                }
            }
            if(palabras[0].equals("PLATO")){

                String nombre = palabras[1];

                int n = 2;

                Plato plat = null;

                plat = new Plato(nombre);

                while(n < palabras.length){

                    if(palabras[n].equals("PLATO")){

                        String nombre1 = palabras[n+1];
                        String ingr = palabras[n+3];
                        Integer valor = Integer.parseInt(palabras[n+4]);

                        plat.addPlato(platos.get(nombre1));
                        plat.addIngrediente(ingredientes.get(ingr), valor);

                        n += 5;

                    } else {

                        plat = agregarIngredientes(nombre, palabras, n, plat);

                        break;
                    }
                }
                platos.put(nombre, plat);
            }
            if(palabras[0].equals("MENU")){

                Menu men = null;

                if(palabras.length-1 == 1)
                    men = new Menu(platos.get(palabras[1]));

                if(palabras.length-1 == 2)
                    men = new Menu(platos.get(palabras[1]), platos.get(palabras[2]));

                men.getId();

                menus.add(men);
            }
        }

        bufferLector.close();

        } catch (IOException e){

            e.printStackTrace();
        }
    }

    /**
     * Función auxiliar para agregar ingredientes a un plato mientras se procesa la información leída del fichero.
     * 
     * @param nombre El nombre del plato al cual se agregarán los ingredientes.
     * @param palabras Un array de cadenas representando los datos del plato e ingredientes.
     * @param n El índice inicial en el array para procesar los ingredientes.
     * @param plat El objeto Plato al cual se agregarán los ingredientes.
     * @return El objeto Plato con los ingredientes agregados.
     */
    public static Plato agregarIngredientes(String nombre, String[] palabras, int n, Plato plat){

        for(int j = n; j < palabras.length-2; j = j + 3){
            String ingred = palabras[j+1];
            Integer valor = Integer.parseInt(palabras[j+2]);  
            plat.addIngrediente(ingredientes.get(ingred), valor);
        }

        return plat;
    }
    
    /**
     * Devuelve la lista de menús almacenados.
     * 
     * @return Lista de menús.
     */
    public static List<Menu> getMenus(){
        return menus;
    }
}
