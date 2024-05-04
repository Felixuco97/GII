package Excepciones;

import objetos.*;

public class DupplicateConnectionException extends ConnectionGeneralException{
    
    public DupplicateConnectionException(Node node){

        super(node);
    }

    public String toString(){

        return super.toString() + "connected to a different network";
    }
}
