package com.threatconnect.app.addons.util.config.attribute.json;

import java.util.Objects;

/**
 * @author Greg Marut
 */
public class AttributeValidationRule
{
	private AttributeValidationRuleType type;
	private String name;
	private String description;
	private String data;
	private int version;
	
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
	
	public int getVersion()
	{
		return version;
	}
	
	public void setVersion(final int version)
	{
		this.version = version;
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
		
		final AttributeValidationRule that = (AttributeValidationRule) o;
		return Objects.equals(name, that.name);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(name);
	}
}
