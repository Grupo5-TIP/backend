package com.unq.edu.tpi.tip.backend.exceptions;

public class OrderEmptyException extends Exception{
	private String err;

	public OrderEmptyException(){
		this.err = "The order must have items.";
	}

	@Override
	public String getMessage(){
		return err;
	}
}
