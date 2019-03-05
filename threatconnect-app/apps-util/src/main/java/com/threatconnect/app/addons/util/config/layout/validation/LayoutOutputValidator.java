package com.threatconnect.app.addons.util.config.layout.validation;

import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.layout.LayoutOutput;
import com.threatconnect.app.addons.util.config.layout.Parameter;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.app.addons.util.config.validation.Validator;

public class LayoutOutputValidator extends Validator<LayoutOutput>
{
	private final Install install;
	
	public LayoutOutputValidator(final Install install)
	{
		this.install = install;
	}
	
	@Override
	public void validate(final LayoutOutput object) throws ValidationException
	{
		//make sure the param name is not empty
		if (isNullOrEmpty(object.getName()))
		{
			throw new ValidationException("\"name\" field is not defined for output parameter.");
		}
		
		//make sure the param name is not empty
		if (isNullOrEmpty(object.getDisplay()))
		{
			throw new ValidationException("\"display\" field is not defined for output parameter: " + object.getName());
		}
		
		boolean outputFound = null != install.getPlaybook() &&
			install.getPlaybook().getOutputVariables().stream().anyMatch(p -> p.getName().equals(object.getName()));
		if (!outputFound)
		{
			throw new ValidationException("output \"" + object.getName() + "\" is not valid.");
		}
	}
}
