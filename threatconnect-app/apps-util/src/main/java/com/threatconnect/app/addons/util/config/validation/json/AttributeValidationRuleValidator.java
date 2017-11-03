package com.threatconnect.app.addons.util.config.validation.json;

import com.threatconnect.app.addons.util.config.attribute.json.AttributeValidationRule;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.app.addons.util.config.validation.Validator;

/**
 * @author Greg Marut
 */
public class AttributeValidationRuleValidator extends Validator<AttributeValidationRule>
{
	@Override
	public void validate(final AttributeValidationRule object) throws ValidationException
	{
		//make sure the name is defined
		if (isNullOrEmpty(object.getName()))
		{
			throwMissingFieldValidationException("name", object);
		}
	}
	
	private void throwMissingFieldValidationException(final String missingFieldName,
		final AttributeValidationRule object) throws ValidationException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("No ");
		sb.append(missingFieldName);
		sb.append(" is defined for validation rule");
		
		//try to find a value to reference when identifying the parameter
		if (!isNullOrEmpty(object.getName()))
		{
			sb.append(" ");
			sb.append(object.getName());
		}
		
		throw new ValidationException(sb.toString());
	}
}
