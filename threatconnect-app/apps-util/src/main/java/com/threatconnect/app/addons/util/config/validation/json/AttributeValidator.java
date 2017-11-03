package com.threatconnect.app.addons.util.config.validation.json;

import com.threatconnect.app.addons.util.config.attribute.json.Attribute;
import com.threatconnect.app.addons.util.config.attribute.json.AttributeType;
import com.threatconnect.app.addons.util.config.attribute.json.AttributeValidationRule;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.app.addons.util.config.validation.Validator;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Greg Marut
 */
public class AttributeValidator extends Validator<Attribute>
{
	@Override
	public void validate(final Attribute object) throws ValidationException
	{
		//make sure the list of attribute types is not empty
		if (object.getTypes().isEmpty())
		{
			throw new ValidationException("Attribute types cannot be empty.");
		}
		
		//load the map of attribute validation rules by name
		Map<String, AttributeValidationRule> validationRuleMap = object.getValidationRules().stream().collect(
			Collectors.toMap(AttributeValidationRule::getName, a -> a));
		
		//create the attribute type validator with the validation rule map
		AttributeTypeValidator attributeTypeValidator = new AttributeTypeValidator(validationRuleMap);
		
		//for each of the attribute types
		for (AttributeType attributeType : object.getTypes())
		{
			attributeTypeValidator.validate(attributeType);
		}
	}
}
