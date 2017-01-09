package com.threatconnect.app.addons.util.config.install.validation;

import com.threatconnect.app.addons.util.config.install.Feed;

/**
 * @author Greg Marut
 */
public class FeedValidator extends Validator<Feed>
{
	@Override
	public void validate(final Feed object) throws ValidationException
	{
		//make sure the source name is defined
		if (isNullOrEmpty(object.getSourceName()))
		{
			throwMissingFieldValidationException("sourceName", object);
		}
		
		//make sure the source category is defined
		if (isNullOrEmpty(object.getSourceCategory()))
		{
			throwMissingFieldValidationException("sourceCategory", object);
		}
		
		//make sure the source description is defined
		if (isNullOrEmpty(object.getSourceDescription()))
		{
			throwMissingFieldValidationException("sourceDescription", object);
		}
		
		//make sure the indicator limit is set
		if (null == object.getIndicatorLimit())
		{
			throwMissingFieldValidationException("indicatorLimit", object);
		}
		
		//make sure the job file is defined
		if (isNullOrEmpty(object.getJobFile()))
		{
			throwMissingFieldValidationException("jobFile", object);
		}
		
		//make sure the deprecation object is defined
		if (null == object.getDeprecation())
		{
			throwMissingFieldValidationException("deprecation", object);
		}
	}
	
	private void throwMissingFieldValidationException(final String missingFieldName, final Feed object)
		throws ValidationException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("No ");
		sb.append(missingFieldName);
		sb.append(" is defined for feed");
		
		//try to find a value to reference when identifying the parameter
		if (!isNullOrEmpty(object.getSourceName()))
		{
			sb.append(" ");
			sb.append(object.getSourceName());
		}
		
		throw new ValidationException(sb.toString());
	}
}
