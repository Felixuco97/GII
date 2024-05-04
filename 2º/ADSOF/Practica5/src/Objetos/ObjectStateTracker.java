package Objetos;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.*;

import Codigo_registration.RegistrationState;

/**
 * Clase que gestiona estados para objetos de cualquier clase y guarda un
 * registro de su trayectoria.
 * 
 * @author Felix felix.lopezl@estudiante.uam.es
 * @author Alex alex.pinneiro@estudiante.uam.es
 */
public class ObjectStateTracker<T,S extends Comparable<S>>{
    
    private LinkedHashMap<S, LinkedHashSet<T>> stateRegs = new LinkedHashMap<>(); // Mapa de estados con los sets de objetos correspondientes
    private LinkedHashMap<S, Predicate<T>> mapConditions = new LinkedHashMap<>(); // Mapa de estados y condiciones
    private S elseCondition;
    private ArrayList<T> regs = new ArrayList<>(); // Lista de objetos
    private LinkedHashMap<T, LinkedHashMap<S, LocalDateTime>> cambios = new LinkedHashMap<>(); // Mapa de objetos y su trayectoria (mapa de estado y fecha)

    
    /**
     * Constructor de la clase que guarda en una lista con orden de inserción
     * los estados pasados como argumento
     * 
     * @param sts conjunto de estados
     */
    public ObjectStateTracker(@SuppressWarnings("unchecked") S... sts){
        for(S s: sts){
            stateRegs.put(s, new LinkedHashSet<>());
        }
    }

    /**
     * Añade objetos a la lista general de objetos a gestionar y les asigna un estado llamando a setObject()
     * 
     * @param rs objetos para trackear
     */
    public void addObjects(@SuppressWarnings("unchecked") T... rs){
        for(T t: rs){
        	if(!regs.contains(t)) { // el método contains utiliza para comprar el equals() de la clase del objeto
	            regs.add(t);
	            setObject(t);
        	}
        }
    }

    /**
     * Actualiza los estados de todos los objetos gestionados por la clase llamando a setObject()
     */
    public void updateStates(){
        for(T t: regs){
            setObject(t);
        }
    }

    /**
     * Añade un objeto a un set y devuelve el set actualizado.
     * (Se usa para añadir objetos al mapa de estados)
     * 
     * @param lista el set donde se añadirá el objeto
     * @param t el objeto a añadir
     * @return el conjunto actualizado
     */
    public LinkedHashSet<T> addObjectToMap(LinkedHashSet<T> lista,T t){
        lista.add(t);
        return lista;
    }

    /**
     * Elimina un objeto del set y devuelve el set actualizado.
     * (Se usa para eliminar objetos del mapa de estados)
     * 
     * @param lista el set donde se añadirá el objeto
     * @param t el objeto a añadir
     * @return el conjunto actualizado
     */
    public LinkedHashSet<T> removeObjectFromMap(LinkedHashSet<T> lista,T t){
        lista.remove(t);
        return lista;
    }

    /**
     * Asigna o actualiza el estado de un objeto comprobando en qué estado encaja;
     * si no le corresponde ninguno se usa elseCondition. Si se hace algún cambio,
     * este se registra para tener guardada la trayectoria de cada objeto.
     * 
     * @param t objeto a settear
     */
    public void setObject(T t){
    	S current = getObjectState(t);
        for(S s : mapConditions.keySet()){

            if(mapConditions.get(s).test(t) == true){
            	if(current != null && s.equals(current)==false) {
            		stateRegs.put(current, this.removeObjectFromMap(stateRegs.get(current), t));
            	}
            	stateRegs.put(s, this.addObjectToMap(stateRegs.get(s), t));
            	this.agregarCambio(t, s, LocalDateTime.now());
                return;
            }
        }

        stateRegs.put(elseCondition, this.addObjectToMap(stateRegs.get(elseCondition), t));
        this.agregarCambio(t, elseCondition, LocalDateTime.now());
    }
    
    /**
     * Devuelve el estado actual de un objeto.
     * 
     * @param t objeto del que se gettea el estado
     * @return
     */
    private S getObjectState(T t){
    	for(S s : mapConditions.keySet()){
            if(stateRegs.get(s).contains(t)){
            	return s;            
            }
        }
    	return null;
    }

    /**
     * Se guarda en la clase la condición que debe cumplir un objeto para
     * estar en un estado determinado.
     * 
     * @param rs estado a asignar condición
     * @param condition condición propia del estado
     * @return
     */
    public ObjectStateTracker<T,S> withState(S rs, Predicate<T> condition){
    	if (!(stateRegs.keySet()).contains(rs)) {
            throw new IllegalStateException("El estado " + rs + " no es válido.");
        }
        mapConditions.put(rs, condition);
        return this;
    }

    /**
     * Se guarda en la clase el estado por defecto a utilizar si los objetos
     * no cumplen ninguna condición para estar en otro.
     * 
     * @param rs estado a asignar condición
     * @param condition condición propia del estado
     * @return
     */
    public ObjectStateTracker<T,S> elseState(S rs){
    	if (!(stateRegs.keySet()).contains(rs)) {
            throw new IllegalStateException("El estado " + rs + " no es válido.");
        }
        elseCondition = rs;
        return this;
    }
    
    /**
     * Registra un cambio de estado
     * 
     * @param obj objeto que cambia de estado
     * @param state estado nuevo
     * @param fecha fecha y hora del cambio
     */
    public void agregarCambio(T obj, S state, LocalDateTime fecha) {
        // Comprueba si el objeto ya tiene entrada en el mapa
        LinkedHashMap<S, LocalDateTime> mapInterno = cambios.get(obj);

        // Si el objeto aún no está, se añade con un mapa interno inicializado
        if (mapInterno == null) {
            mapInterno = new LinkedHashMap<>();
            cambios.put(obj, mapInterno);
        }

        // Registra el cambio en el mapa interno
        mapInterno.put(state, fecha);
    }
    
    /**
     * Construye un String con la trayectoria de un objeto
     * 
     * @param object objeto del que se obtiene la trayectoria
     * @return String texto con la trayectoria 
     */
    public String trajectory(T object) {
        LinkedHashMap<S, LocalDateTime> trajec = cambios.get(object);

        StringBuilder sb = new StringBuilder();
        S lastState = null;
        for (Map.Entry<S, LocalDateTime> entry : trajec.entrySet()) {
            if (lastState == null) {
                sb.append("[(in: ").append(entry.getKey()).append(" at: ").append(entry.getValue()).append(")");
            } else {
                sb.append(", (from: ").append(lastState).append(" to ").append(entry.getKey())
                  .append(" at: ").append(entry.getValue()).append(")");
            }
            lastState = entry.getKey();
        }
        sb.append("]");
        return sb.toString();
    }

    public String toString(){

        String text = "{";

        for(S s: stateRegs.keySet()){
            text += s+"="+stateRegs.get(s);
            text += (!s.equals(RegistrationState.REJECTED) ? ", " : "");
        }

        text += "}";

        return text;
        
    }
    
}
