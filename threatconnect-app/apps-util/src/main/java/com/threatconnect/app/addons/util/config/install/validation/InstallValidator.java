package com.threatconnect.app.addons.util.config.install.validation;

import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.install.Param;
import com.threatconnect.app.addons.util.config.install.RunLevel;

/**
 * @author Greg Marut
 */
public class InstallValidator extends Validator<Install>
{
	//holds the validator for validating parameters
	private final Validator<Param> paramValidator;
	
	public InstallValidator()
	{
		this.paramValidator = new ParamValidator();
	}
	
	@Override
	public void validate(final Install object) throws ValidationException
	{
		//validate the program language
		if (isNullOrEmpty(object.getProgramLanguage()))
		{
			throw new ValidationException("programLanguage is not defined.");
		}
		
		//validate the program main
		if (isNullOrEmpty(object.getProgramMain()))
		{
			throw new ValidationException("programMain is not defined.");
		}
		
		//validate the runtime levels
		if (object.getRuntimeLevel().isEmpty())
		{
			throw new ValidationException("runtimeLevel is not defined.");
		}
		//check to see if there are multiple runlevels
		else if (object.getRuntimeLevel().size() > 1)
		{
			//for each of the run levels
			for (RunLevel runLevel : object.getRuntimeLevel())
			{
				//this runlevel must either be an organization or a space organization to be multiple
				if (runLevel != RunLevel.Organization && runLevel != RunLevel.SpaceOrganization)
				{
					throw new ValidationException("Multiple runLevels must be Organization and SpaceOrganization");
				}
			}
		}
		
		//validate the list delimiter
		if (isNullOrEmpty(object.getListDelimiter()))
		{
			throw new ValidationException("listDelimiter is not defined.");
		}
		
		//for each of the params
		for (Param param : object.getParams())
		{
			//validate this param
			paramValidator.validate(param);
		}
	}
}
