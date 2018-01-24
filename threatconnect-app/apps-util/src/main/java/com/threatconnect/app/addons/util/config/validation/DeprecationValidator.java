package com.threatconnect.app.addons.util.config.validation;

import com.threatconnect.app.addons.util.config.install.ActionAtMinimumType;
import com.threatconnect.app.addons.util.config.install.Deprecation;

import java.util.Arrays;

/**
 * @author Greg Marut
 */
public class DeprecationValidator extends Validator<Deprecation>
{
	@Override
	public void validate(final Deprecation object) throws ValidationException
	{
		//check to see if the action at minimum is not null
		if (null != object.getActionAtMinimum())
		{
			try
			{
				//validate that the values are correct
				ActionAtMinimumType.valueOf(object.getActionAtMinimum());
			}
			catch (IllegalArgumentException e)
			{
				throw new ValidationException("actionAtMinimum contains an invalid value. Valid values are: " + Arrays
					.toString(ActionAtMinimumType.values()));
			}
		}
		else
		{
			//check to see if delete at minimum is true
			if (object.isDeleteAtMinimum())
			{
				object.setActionAtMinimum(ActionAtMinimumType.Delete.toString());
			}
			else
			{
				object.setActionAtMinimum(ActionAtMinimumType.None.toString());
			}
		}
	}
}
