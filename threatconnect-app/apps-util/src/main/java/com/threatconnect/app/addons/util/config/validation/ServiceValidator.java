package com.threatconnect.app.addons.util.config.validation;

import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.install.RunLevelType;

public class ServiceValidator extends Validator<Install>
{
	private static final String DISPLAY_PATH_REGEX = "[a-zA-Z0-9\\_\\.]+";
	
	@Override
	public void validate(final Install install) throws ValidationException
	{
		//check to see if this is an api service
		if (RunLevelType.ApiService == install.getRuntimeLevel())
		{
			//make sure there is a displayPath
			if(null == install.getDisplayPath() || !install.getDisplayPath().matches(DISPLAY_PATH_REGEX))
			{
				throw new ValidationException("ApiServices require a valid displayPath: " + DISPLAY_PATH_REGEX);
			}
		}
	}
}
