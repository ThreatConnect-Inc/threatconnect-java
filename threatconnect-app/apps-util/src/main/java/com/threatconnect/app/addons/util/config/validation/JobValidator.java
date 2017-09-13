package com.threatconnect.app.addons.util.config.validation;

import com.threatconnect.app.addons.util.config.install.Job;

/**
 * @author Greg Marut
 */
public class JobValidator extends Validator<Job>
{
	public final boolean allowVariables;
	
	public JobValidator()
	{
		this(true);
	}
	
	public JobValidator(final boolean allowVariables)
	{
		this.allowVariables = allowVariables;
	}
	
	@Override
	public void validate(final Job object) throws ValidationException
	{
		//make sure the source name is defined
		if (isNullOrEmpty(object.getJobName()))
		{
			throwMissingFieldValidationException("jobName", object);
		}
		
		//make sure the source name is defined
		if (isNullOrEmpty(object.getProgramName()))
		{
			throwMissingFieldValidationException("programName", object);
		}
		
		//make sure the source category is defined
		if (isNullOrEmpty(object.getProgramVersion()))
		{
			throwMissingFieldValidationException("programVersion", object);
		}
	}
	
	private void throwMissingFieldValidationException(final String missingFieldName, final Job object)
		throws ValidationException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("No ");
		sb.append(missingFieldName);
		sb.append(" is defined for job");
		
		//try to find a value to reference when identifying the parameter
		if (!isNullOrEmpty(object.getJobName()))
		{
			sb.append(" ");
			sb.append(object.getJobName());
		}
		
		throw new ValidationException(sb.toString());
	}
}
