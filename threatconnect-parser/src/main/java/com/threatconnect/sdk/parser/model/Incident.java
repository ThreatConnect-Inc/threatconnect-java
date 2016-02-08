package com.threatconnect.sdk.parser.model;

import java.util.Date;

public class Incident extends Group
{
	private Date eventDate;
	
	public Incident()
	{
		super(GroupType.INCIDENT);
	}
	
	public Date getEventDate()
	{
		return eventDate;
	}
	
	public void setEventDate(Date eventDate)
	{
		this.eventDate = eventDate;
	}
}
