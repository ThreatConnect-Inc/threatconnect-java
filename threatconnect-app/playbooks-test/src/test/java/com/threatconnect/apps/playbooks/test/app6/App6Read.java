package com.threatconnect.apps.playbooks.test.app6;

import com.google.gson.Gson;
import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.apps.ExitStatus;
import com.threatconnect.app.playbooks.app.PlaybooksApp;
import com.threatconnect.app.playbooks.app.PlaybooksAppConfig;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

/**
 * @author Greg Marut
 */
public class App6Read extends PlaybooksApp
{
	public static final String PARAM_USER = "user";
	
	public static final String OUTPUT_NAME = "test.app6.value";
	
	@Override
	protected ExitStatus execute(final PlaybooksAppConfig playbooksAppConfig) throws Exception
	{
		//read the first and last name
		final byte[] custom = readCustomTypeContent(PARAM_USER);
		final User user = new Gson().fromJson(new InputStreamReader(new ByteArrayInputStream(custom)), User.class);
		
		if (isOutputParamExpected(OUTPUT_NAME, StandardPlaybookType.String))
		{
			writeStringContent(OUTPUT_NAME, user.getFirstName() + " " + user.getLastName());
		}
		
		return ExitStatus.Success;
	}
	
	@Override
	public String getLogFilename()
	{
		return "App6Read.log";
	}
}
