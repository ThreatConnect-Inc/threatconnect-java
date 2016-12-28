package com.threatconnect.app.addons.util.config.install.validation;

import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.install.Param;
import com.threatconnect.app.addons.util.config.install.Playbook;
import com.threatconnect.app.addons.util.config.install.RunLevelType;

/**
 * @author Greg Marut
 */
public class InstallValidator extends Validator<Install>
{
	private final Validator<Param> paramValidator;
	private final Validator<Playbook> playbookValidator;
	
	public InstallValidator()
	{
		this.paramValidator = new ParamValidator();
		this.playbookValidator = new PlaybookValidator();
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
			for (RunLevelType runLevelType : object.getRuntimeLevel())
			{
				//this runlevel must either be an organization or a space organization to be multiple
				if (runLevelType != RunLevelType.Organization && runLevelType != RunLevelType.SpaceOrganization)
				{
					throw new ValidationException("Multiple runLevels must be Organization and SpaceOrganization");
				}
			}
		}
		
		//check to see if this is a playbook app
		if (containsRunLevel(object, RunLevelType.Playbook))
		{
			//check to see if the playbook object is missing
			if (null == object.getPlaybook())
			{
				throw new ValidationException("'playbook' config must be defined for a playbook app.");
			}
			
			//validate the playbook
			playbookValidator.validate(object.getPlaybook());
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
	
	private boolean containsRunLevel(final Install install, final RunLevelType runLevelType)
	{
		//for each of the run levels
		for (RunLevelType level : install.getRuntimeLevel())
		{
			if (level == runLevelType)
			{
				return true;
			}
		}
		
		return false;
	}
}
