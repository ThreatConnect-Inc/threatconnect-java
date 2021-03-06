package com.threatconnect.apps.playbooks.test.app4;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.apps.ExitStatus;
import com.threatconnect.app.playbooks.app.PlaybooksApp;
import com.threatconnect.app.playbooks.app.PlaybooksAppConfig;

/**
 * @author Greg Marut
 */
public class App4DynamicConfig extends PlaybooksApp
{
	public static final String PARAM_INPUT_FIRST_NAME = "first_name";
	public static final String PARAM_INPUT_LAST_NAME = "last_name";
	
	public static final String OUTPUT_NAME = "test.app4.name";
	
	@Override
	protected ExitStatus execute(final PlaybooksAppConfig playbooksAppConfig) throws Exception
	{
		//read the first and last name
		final String firstName = readStringContent(PARAM_INPUT_FIRST_NAME);
		final String lastName = readStringContent(PARAM_INPUT_LAST_NAME);
		
		if (isOutputParamExpected(OUTPUT_NAME, StandardPlaybookType.String))
		{
			writeStringContent(OUTPUT_NAME, firstName + " " + lastName);
		}
		
		return ExitStatus.Success;
	}
	
	@Override
	public String getLogFilename()
	{
		return "App4DynamicConfig.log";
	}
}
