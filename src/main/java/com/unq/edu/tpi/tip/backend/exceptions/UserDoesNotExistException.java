package com.unq.edu.tpi.tip.backend.exceptions;

public class UserDoesNotExistException  extends Exception
{
	private String err;

	public UserDoesNotExistException()
	{
		this.err = "The user does not have exist.";
	}

	@Override public String getMessage()
	{
		return err;
	}
}