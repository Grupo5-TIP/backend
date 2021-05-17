package com.unq.edu.tpi.tip.backend.exceptions;

public class StateNotFoundException extends Exception{
    private String err;

    public StateNotFoundException(){
        this.err = "The order must have items.";
    }

    @Override
    public String getMessage(){
        return err;
    }
}
