package com.unq.edu.tpi.tip.backend.exceptions;

public class TableDoesNotExistException extends Exception
{
	private String err;

	public TableDoesNotExistException(Long tableId)
	{
		this.err = "Table with id " + tableId.toString() + " does not exist.";
	}

	@Override
	public String getMessage(){
		return err;
	}
}
