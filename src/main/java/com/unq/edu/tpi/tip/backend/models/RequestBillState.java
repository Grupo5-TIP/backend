package com.unq.edu.tpi.tip.backend.models;

import javax.persistence.Entity;

@Entity
public class RequestBillState extends State
{
	public RequestBillState()
	{
		super("bill");
	}
}
