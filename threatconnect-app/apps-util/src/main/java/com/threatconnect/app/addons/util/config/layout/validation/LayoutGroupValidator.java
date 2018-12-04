package com.threatconnect.app.addons.util.config.layout.validation;

import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.layout.LayoutGroup;
import com.threatconnect.app.addons.util.config.layout.Parameter;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.app.addons.util.config.validation.Validator;

public class LayoutGroupValidator extends Validator<LayoutGroup>
{
	private final Install install;
	
	public LayoutGroupValidator(final Install install)
	{
		this.install = install;
	}
	
	@Override
	public void validate(final LayoutGroup object) throws ValidationException
	{
		//make sure the title is defined
		if (isNullOrEmpty(object.getTitle()))
		{
			throw new ValidationException("title is not defined.");
		}
		
		//make sure there are parameters
		if(object.getParameters().isEmpty())
		{
			throw new ValidationException("parameters array must contain at least 1 item");
		}
		
		//for each of the parameters
		ParameterValidator parameterValidator = new ParameterValidator(install);
		for (Parameter parameter : object.getParameters())
		{
			parameterValidator.validate(parameter);
		}
	}
}
