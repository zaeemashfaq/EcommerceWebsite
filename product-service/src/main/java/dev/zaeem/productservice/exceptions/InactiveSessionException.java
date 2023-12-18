package dev.zaeem.productservice.exceptions;

public class InactiveSessionException extends Exception{
    public InactiveSessionException(String message){
        super(message);
    }
}
