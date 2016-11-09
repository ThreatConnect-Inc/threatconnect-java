package com.threatconnect.apps.playbooks.test.app1;

import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.app.apps.ExitStatus;
import com.threatconnect.app.playbooks.app.PlaybooksApp;
import com.threatconnect.app.playbooks.app.PlaybooksAppConfig;

/**
 * @author Greg Marut
 */
public class App1 extends PlaybooksApp
{
	public static final String PARAM_INPUT_FIRST_NAME = "first_name";
	public static final String PARAM_INPUT_LAST_NAME = "last_name";
	
	public static final String OUTPUT_NAME = "test.app1.name";
	
	@Override
	protected ExitStatus execute(final PlaybooksAppConfig playbooksAppConfig) throws Exception
	{
		//read the first and last name
		final String firstName = readStringContent(PARAM_INPUT_FIRST_NAME);
		final String lastName = readStringContent(PARAM_INPUT_LAST_NAME);
		
		if (isOutputParamExpected(OUTPUT_NAME, PlaybookVariableType.String))
		{
			writeStringContent(OUTPUT_NAME, firstName + " " + lastName);
		}
		
		return ExitStatus.Success;
	}
	
	@Override
	public String getLogFilename()
	{
		return "App1.log";
	}
}
