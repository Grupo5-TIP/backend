package com.unq.edu.tpi.tip.backend.exceptions;

public class TableNotFoundException extends Exception
{
	private String err;

	public TableNotFoundException(Long tableId)
	{
		this.err = "Table with id " + tableId.toString() + " does not exist.";
	}

	@Override
	public String getMessage(){
		return err;
	}
}
