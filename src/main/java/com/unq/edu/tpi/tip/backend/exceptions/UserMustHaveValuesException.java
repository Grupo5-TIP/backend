package com.unq.edu.tpi.tip.backend.exceptions;

public class UserMustHaveValuesException extends Exception
{
	private String err;

	public UserMustHaveValuesException()
	{
		this.err = "Username and password must not be blank.";
	}

	@Override
	public String getMessage(){
		return err;
	}
}
