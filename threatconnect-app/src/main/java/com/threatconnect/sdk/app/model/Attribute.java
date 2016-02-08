package com.threatconnect.sdk.app.model;

public class Attribute
{
	private String key;
	private String value;
	private Boolean displayed;
	
	public String getKey()
	{
		return key;
	}
	
	public void setKey(String key)
	{
		this.key = key;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(String value)
	{
		this.value = value;
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
