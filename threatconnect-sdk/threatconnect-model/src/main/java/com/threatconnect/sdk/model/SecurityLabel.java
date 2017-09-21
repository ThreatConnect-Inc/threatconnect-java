package com.threatconnect.sdk.model;

import java.util.Date;

/**
 * @author Greg Marut
 */
public class SecurityLabel
{
	private String name;
	private String description;
	private Date dateAdded;
	private String color;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(final String name)
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(final String description)
	{
		this.description = description;
	}
	
	public Date getDateAdded()
	{
		return dateAdded;
	}
	
	public void setDateAdded(final Date dateAdded)
	{
		this.dateAdded = dateAdded;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public void setColor(final String color)
	{
		this.color = color;
	}
}
