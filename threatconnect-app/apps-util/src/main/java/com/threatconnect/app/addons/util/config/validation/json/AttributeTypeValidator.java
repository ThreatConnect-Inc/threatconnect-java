package com.threatconnect.app.addons.util.config.validation.json;

import com.threatconnect.app.addons.util.config.attribute.json.AttributeType;
import com.threatconnect.app.addons.util.config.attribute.json.AttributeValidationRule;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.app.addons.util.config.validation.Validator;

import java.util.Map;

/**
 * @author Greg Marut
 */
public class AttributeTypeValidator extends Validator<AttributeType>
{
	//holds the map of shared validation rules
	private final Map<String, AttributeValidationRule> validationRuleMap;
	
	public AttributeTypeValidator(final Map<String, AttributeValidationRule> validationRuleMap)
	{
		this.validationRuleMap = validationRuleMap;
	}
	
	@Override
	public void validate(final AttributeType object) throws ValidationException
	{
		//make sure the name is not empty
		if (isNullOrEmpty(object.getName()))
		{
			throw new ValidationException("AttributeType name cannot be empty.");
		}
		
		//check to see if this attribute has a validation rule
		if (null != object.getValidationRule())
		{
			//check to see if this validation rule exists in the shared list
			if (validationRuleMap.containsKey(object.getValidationRule().getName()))
			{
			
			}
		}
		
		//check to see if the indicators and groups is empty
		if (object.getIndicators().isEmpty() && object.getGroups().isEmpty())
		{
			throw new ValidationException("At least 1 entry must exist in either the indicators or groups array");
		}
		else
		{
			//for each of the indicators
			for (String indicator : object.getIndicators())
			{
				if (isNullOrEmpty(indicator))
				{
					throw new ValidationException("AttributeType indicators contains an empty value.");
				}
			}
			
			//for each of the groups
			for (String group : object.getGroups())
			{
				if (isNullOrEmpty(group))
				{
					throw new ValidationException("AttributeType groups contains an empty value.");
				}
			}
		}
	}
}
