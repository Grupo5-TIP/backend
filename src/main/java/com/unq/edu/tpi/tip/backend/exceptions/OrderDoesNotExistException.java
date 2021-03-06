package com.unq.edu.tpi.tip.backend.exceptions;

public class OrderDoesNotExistException extends Exception{
    private String err;

    public OrderDoesNotExistException(Long tableId)
    {
        this.err = "Order with id " + tableId.toString() + " does not exist.";
    }

    @Override
    public String getMessage(){
        return err;
    }
}
