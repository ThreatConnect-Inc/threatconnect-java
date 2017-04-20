package com.threatconnect.sdk.model;

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
	
	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		
		final Attribute attribute = (Attribute) o;
		
		if (type != null ? !type.equals(attribute.type) : attribute.type != null)
		{
			return false;
		}
		else if (value != null ? !value.equals(attribute.value) : attribute.value != null)
		{
			return false;
		}
		else if (source != null ? !source.equals(attribute.source) : attribute.source != null)
		{
			return false;
		}
		else
		{
			return displayed != null ? displayed.equals(attribute.displayed) : attribute.displayed == null;
		}
	}
	
	@Override
	public int hashCode()
	{
		int result = type != null ? type.hashCode() : 0;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		result = 31 * result + (source != null ? source.hashCode() : 0);
		result = 31 * result + (displayed != null ? displayed.hashCode() : 0);
		return result;
	}
}
