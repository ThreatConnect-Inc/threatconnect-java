package com.threatconnect.app.addons.util.config.validation.csv;

import com.threatconnect.app.addons.util.config.attribute.csv.AttributeType;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.app.addons.util.config.validation.Validator;

/**
 * @author Greg Marut
 */
public class AttributeTypeValidator extends Validator<AttributeType>
{
	@Override
	public void validate(final AttributeType object) throws ValidationException
	{
		//make sure the name is not empty
		if (isNullOrEmpty(object.getName()))
		{
			throw new ValidationException("Attribute name cannot be empty.");
		}
		
		//check to see if the types is empty
		if (object.getTypes().isEmpty())
		{
			throw new ValidationException("Attribute types cannot be empty.");
		}
		else
		{
			//for each of the types
			for (String type : object.getTypes())
			{
				if (isNullOrEmpty(type))
				{
					throw new ValidationException("Attribute types contains an empty value.");
				}
			}
		}
	}
}
