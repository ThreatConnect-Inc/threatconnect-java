package com.threatconnect.apps.playbooks.test.app6;

import com.google.gson.Gson;
import com.threatconnect.app.apps.ExitStatus;
import com.threatconnect.app.playbooks.app.PlaybooksApp;
import com.threatconnect.app.playbooks.app.PlaybooksAppConfig;

/**
 * @author Greg Marut
 */
public class App6Create extends PlaybooksApp
{
	public static final String PARAM_INPUT_FIRST_NAME = "first_name";
	public static final String PARAM_INPUT_LAST_NAME = "last_name";
	
	public static final String OUTPUT_NAME = "test.app6.user";
	
	@Override
	protected ExitStatus execute(final PlaybooksAppConfig playbooksAppConfig) throws Exception
	{
		//read the first and last name
		final String firstName = readStringContent(PARAM_INPUT_FIRST_NAME);
		final String lastName = readStringContent(PARAM_INPUT_LAST_NAME);
		
		if (isOutputParamExpected(OUTPUT_NAME, App6Test.CUSTOM_PLAYBOOK_TYPE))
		{
			User user = new User();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			
			final byte[] data = new Gson().toJson(user).getBytes();
			writeCustomTypeContent(OUTPUT_NAME, data, App6Test.CUSTOM_PLAYBOOK_TYPE);
		}
		
		return ExitStatus.Success;
	}
	
	@Override
	public String getLogFilename()
	{
		return "App6Create.log";
	}
}
