package com.threatconnect.app.addons.util.config.validation;

import com.threatconnect.app.addons.util.ParamVariableUtil;
import com.threatconnect.app.addons.util.config.install.JobParam;

import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public class JobParamValidator extends Validator<JobParam>
{
	public final boolean allowVariables;
	
	public JobParamValidator(final boolean allowVariables)
	{
		this.allowVariables = allowVariables;
	}
	
	@Override
	public void validate(final JobParam object) throws ValidationException
	{
		//make sure the source name is defined
		if (isNullOrEmpty(object.getName()))
		{
			throwMissingFieldValidationException("name", object);
		}
		
		//make sure the source name is defined
		if (isNullOrEmpty(object.getDefaultValue()))
		{
			throwMissingFieldValidationException("default", object);
		}
		
		//check to see if variables are not allowed
		if(!allowVariables)
		{
			final String defaultValue = object.getDefaultValue();
			Pattern pattern = Pattern.compile(ParamVariableUtil.REGEX_EXTERNAL_VARIABLE);
			if(pattern.matcher(defaultValue).find())
			{
				throw new ValidationException("Parameter \"" + object.getName() + "\" may not contain a ThreatConnect variable.");
			}
		}
	}
	
	private void throwMissingFieldValidationException(final String missingFieldName, final JobParam object)
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
		
		throw new ValidationException(sb.toString());
	}
}
