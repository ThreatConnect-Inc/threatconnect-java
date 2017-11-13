package com.threatconnect.app.addons.util.config.attribute.json;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class Attribute
{
	private final List<AttributeType> types;
	private final List<AttributeValidationRule> validationRules;
	
	public Attribute()
	{
		this.types = new ArrayList<AttributeType>();
		this.validationRules = new ArrayList<AttributeValidationRule>();
	}
	
	public List<AttributeType> getTypes()
	{
		return types;
	}
	
	public List<AttributeValidationRule> getValidationRules()
	{
		return validationRules;
	}
}
