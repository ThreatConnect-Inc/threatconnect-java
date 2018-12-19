package com.threatconnect.app.addons.util.config.layout.validation;

import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.layout.Parameter;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.app.addons.util.config.validation.Validator;

public class ParameterValidator extends Validator<Parameter>
{
	private final Install install;
	
	public ParameterValidator(final Install install)
	{
		this.install = install;
	}
	
	@Override
	public void validate(final Parameter object) throws ValidationException
	{
		//make sure the param name is not empty
		if (isNullOrEmpty(object.getName()))
		{
			throw new ValidationException("name is not defined for this parameter.");
		}
		
		boolean paramNameFound = install.getParams().stream().anyMatch(p -> p.getName().equals(object.getName()));
		if(!paramNameFound)
		{
			throw new ValidationException("parameter name \"" + object.getName() + "\" is not valid.");
		}
	}
}
