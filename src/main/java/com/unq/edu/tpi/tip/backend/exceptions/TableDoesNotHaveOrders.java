package com.unq.edu.tpi.tip.backend.exceptions;

public class TableDoesNotHaveOrders extends Exception
{
	private String err;

	public TableDoesNotHaveOrders(Long tableId)
	{
		this.err = "Table with id " + tableId.toString() + " does not have orders.";
	}

	@Override
	public String getMessage(){
		return err;
	}
}
