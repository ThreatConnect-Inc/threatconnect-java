package com.threatconnect.app.addons.util.config.validation;

import com.threatconnect.app.addons.util.config.install.Param;
import com.threatconnect.app.addons.util.config.install.ParamDataType;

/**
 * @author Greg Marut
 */
public class ParamValidator extends Validator<Param>
{
	@Override
	public void validate(final Param object) throws ValidationException
	{
		//validate the param name
		if (isNullOrEmpty(object.getName()))
		{
			throwMissingFieldValidationException("name", object);
		}
		
		//validate the param label
		if (isNullOrEmpty(object.getLabel()))
		{
			throwMissingFieldValidationException("label", object);
		}
		
		//validate the object type
		if (null == object.getType())
		{
			throwMissingFieldValidationException("type", object);
		}
		
		//validate that the valid values list exists if the datatype requires it
		if ((object.getType() == ParamDataType.MultiChoice || object.getType() == ParamDataType.Choice)
			&& object.getValidValues().isEmpty())
		{
			throwMissingFieldValidationException("validValues", object);
		}
		
		//check to see if this param is to be exposed as a playbook key
		if (null != object.getExposePlaybookKeyAs())
		{
			//make sure that this is a playbook param
			if (object.getType() != ParamDataType.KeyValueList)
			{
				throw new ValidationException("Params that specify exposePlaybookKeyAs must be of type " + ParamDataType.KeyValueList.toString());
			}
		}
	}
	
	private void throwMissingFieldValidationException(final String missingFieldName, final Param object)
		throws ValidationException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("No ");
		sb.append(missingFieldName);
		sb.append(" is defined for parameter");
		
		//try to find a value to reference when identifying the parameter
		if (!isNullOrEmpty(object.getName()))
		{
			sb.append(" ");
			sb.append(object.getName());
		}
		else if (!isNullOrEmpty(object.getLabel()))
		{
			sb.append(" ");
			sb.append(object.getLabel());
		}
		
		throw new ValidationException(sb.toString());
	}
}
