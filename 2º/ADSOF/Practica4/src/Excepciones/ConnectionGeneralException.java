package Excepciones;

import objetos.*;

public class ConnectionGeneralException extends RuntimeException{

    private Node node;
    
    public ConnectionGeneralException(Node node){

        this.node = node;
    }

    public String toString(){

        String text = "";

        text += "Connection exception: " + this.node.fullNameForException() + " is ";

        return text;
    }
}
