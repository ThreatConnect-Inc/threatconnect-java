package com.threatconnect.app.addons.util.config.attribute.json;

/**
 * @author Greg Marut
 */
public class AttributeValidationRule
{
	private AttributeValidationRuleType type;
	private String name;
	private String description;
	private String data;
	
	public AttributeValidationRuleType getType()
	{
		return type;
	}
	
	public void setType(final AttributeValidationRuleType type)
	{
		this.type = type;
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
	
	public String getData()
	{
		return data;
	}
	
	public void setData(final String data)
	{
		this.data = data;
	}
}
