package com.threatconnect.app.addons.util.config.attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class Attribute
{
	private final List<String> types;
	
	private String name;
	private String description;
	private String errorMessage;
	private int maxSize;
	private boolean allowMarkdown;
	
	public Attribute()
	{
		this.types = new ArrayList<String>();
	}
	
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
	
	public String getErrorMessage()
	{
		return errorMessage;
	}
	
	public void setErrorMessage(final String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
	
	public List<String> getTypes()
	{
		return types;
	}
	
	public int getMaxSize()
	{
		return maxSize;
	}
	
	public void setMaxSize(final int maxSize)
	{
		this.maxSize = maxSize;
	}
	
	public boolean isAllowMarkdown()
	{
		return allowMarkdown;
	}
	
	public void setAllowMarkdown(final boolean allowMarkdown)
	{
		this.allowMarkdown = allowMarkdown;
	}
}
