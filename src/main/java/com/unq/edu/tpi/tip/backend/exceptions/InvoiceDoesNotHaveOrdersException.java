package com.unq.edu.tpi.tip.backend.exceptions;

public class InvoiceDoesNotHaveOrdersException extends Exception
{
	private String err;

	public InvoiceDoesNotHaveOrdersException()
	{
		this.err = "The invoice does not have orders.";
	}

	@Override public String getMessage()
	{
		return err;
	}
}