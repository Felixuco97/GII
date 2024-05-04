package Excepciones;

import objetos.*;

public class ConnectionException extends ConnectionGeneralException{
    
    public ConnectionException(Node node){

        super(node);
    }

    public String toString(){

        return super.toString() + "already connected to the network";
    }
}
