package com.threatconnect.sdk.parser.model;

public class Attribute
{
	private String type;
	private String value;
	private String source;
	private Boolean displayed;
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}
	
	public String getSource()
	{
		return source;
	}
	
	public void setSource(String source)
	{
		this.source = source;
	}
	
	public Boolean getDisplayed()
	{
		return displayed;
	}
	
	public void setDisplayed(Boolean displayed)
	{
		this.displayed = displayed;
	}
}
