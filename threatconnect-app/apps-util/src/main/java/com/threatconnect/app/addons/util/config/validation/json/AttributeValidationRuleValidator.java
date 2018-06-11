package com.threatconnect.app.addons.util.config.validation.json;

import com.threatconnect.app.addons.util.config.attribute.json.AttributeValidationRule;
import com.threatconnect.app.addons.util.config.attribute.json.AttributeValidationRuleType;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.app.addons.util.config.validation.Validator;

import java.util.Arrays;
import java.util.stream.Collectors;

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
		
		//make sure the description is defined
		if (isNullOrEmpty(object.getDescription()))
		{
			throwMissingFieldValidationException("description", object);
		}
		
		//make sure the type is not missing
		if (null == object.getType())
		{
			StringBuilder sb = new StringBuilder();
			sb.append("No type is defined for validation rule");
			
			//try to find a value to reference when identifying the parameter
			if (!isNullOrEmpty(object.getName()))
			{
				sb.append(" \"");
				sb.append(object.getName());
				sb.append("\"");
			}
			
			sb.append(". Valid values are: ");
			sb.append(String.join(", ", Arrays.stream(AttributeValidationRuleType.values()).map(Enum::toString).collect(
				Collectors.toList())));
			throw new ValidationException(sb.toString());
		}
		
		//switch based on the type
		switch (object.getType())
		{
			//data field required
			case INTEGER:
			case REGEX:
			case XSD:
			case SELECT_ONE_PICKLIST:
			case SELECT_ONE_RADIO:
				validateDataFieldIsNotNullOrMissing(object.getType(), object);
				break;
			//data field not required
			case DATE:
			case DATE_TIME:
				break;
			default:
				throw new ValidationException("type is not valid.");
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
			sb.append(" \"");
			sb.append(object.getName());
			sb.append("\"");
		}
		
		throw new ValidationException(sb.toString());
	}
	
	private void validateDataFieldIsNotNullOrMissing(final AttributeValidationRuleType attributeValidationRuleType,
		final AttributeValidationRule object) throws ValidationException
	{
		//check to see if the data is missing
		if (isNullOrEmpty(object.getData()))
		{
			StringBuilder sb = new StringBuilder();
			sb.append("\"data\" must be defined when type is ");
			sb.append(attributeValidationRuleType);
			sb.append(" for validation rule");
			
			//try to find a value to reference when identifying the parameter
			if (!isNullOrEmpty(object.getName()))
			{
				sb.append(" ");
				sb.append(object.getName());
			}
			
			throw new ValidationException(sb.toString());
		}
	}
}
