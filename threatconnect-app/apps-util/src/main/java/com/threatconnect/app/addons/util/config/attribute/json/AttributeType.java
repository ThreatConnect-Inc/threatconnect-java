package com.threatconnect.app.addons.util.config.attribute.json;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class AttributeType
{
	private final List<String> indicators;
	private final List<String> groups;
	
	private String name;
	private String description;
	private String errorMessage;
	private int maxLength;
	private boolean allowMarkdown;
	private boolean system;
	private AttributeValidationRule validationRule;
	
	public AttributeType()
	{
		this.indicators = new ArrayList<String>();
		this.groups = new ArrayList<String>();
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
	
	public List<String> getIndicators()
	{
		return indicators;
	}
	
	public List<String> getGroups()
	{
		return groups;
	}
	
	public int getMaxLength()
	{
		return maxLength;
	}
	
	public void setMaxLength(final int maxLength)
	{
		this.maxLength = maxLength;
	}
	
	public boolean isAllowMarkdown()
	{
		return allowMarkdown;
	}
	
	public void setAllowMarkdown(final boolean allowMarkdown)
	{
		this.allowMarkdown = allowMarkdown;
	}
	
	public boolean isSystem()
	{
		return system;
	}
	
	public void setSystem(final boolean system)
	{
		this.system = system;
	}
	
	public AttributeValidationRule getValidationRule()
	{
		return validationRule;
	}
	
	public void setValidationRule(final AttributeValidationRule validationRule)
	{
		this.validationRule = validationRule;
	}
}
