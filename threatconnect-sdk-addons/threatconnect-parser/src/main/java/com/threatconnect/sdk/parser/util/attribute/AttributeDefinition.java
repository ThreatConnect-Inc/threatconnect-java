package com.threatconnect.sdk.parser.util.attribute;

import com.threatconnect.sdk.model.GroupType;

import java.util.HashSet;
import java.util.Set;

public class AttributeDefinition
{
	private String name;
	private String description;
	private String errorMessage;
	private int maxSize;
	
	private final Set<String> indicatorTypes;
	private final Set<GroupType> groupTypes;
	
	public AttributeDefinition()
	{
		this.indicatorTypes = new HashSet<String>();
		this.groupTypes = new HashSet<GroupType>();
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getErrorMessage()
	{
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
	
	public int getMaxSize()
	{
		return maxSize;
	}
	
	public void setMaxSize(int maxSize)
	{
		this.maxSize = maxSize;
	}
	
	public Set<String> getIndicatorTypes()
	{
		return indicatorTypes;
	}
	
	public Set<GroupType> getGroupTypes()
	{
		return groupTypes;
	}
}
