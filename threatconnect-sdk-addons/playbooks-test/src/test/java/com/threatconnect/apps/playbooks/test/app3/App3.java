package com.threatconnect.apps.playbooks.test.app3;

import com.threatconnect.sdk.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.sdk.app.ExitStatus;
import com.threatconnect.sdk.playbooks.app.PlaybooksApp;
import com.threatconnect.sdk.playbooks.app.PlaybooksAppConfig;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public class App3 extends PlaybooksApp
{
	public static final String PARAM_SPLIT_ON = "join_on";
	public static final String PARAM_INPUT_CONCAT = "input_concat";
	
	public static final String PARAM_OUTPUT_SPLIT = "test.app3.split";
	
	@Override
	protected ExitStatus execute(final PlaybooksAppConfig playbooksAppConfig) throws Exception
	{
		//read the parameters
		String splitOn = readStringContent(PARAM_SPLIT_ON);
		String concat = readStringContent(PARAM_INPUT_CONCAT);
		
		//split the strings
		String[] split = concat.split(Pattern.quote(splitOn));
		
		//check to see if the output needs to be written
		if (isOutputParamExpected(PARAM_OUTPUT_SPLIT, PlaybookVariableType.StringArray))
		{
			//write the output
			writeStringListContent(PARAM_OUTPUT_SPLIT, Arrays.asList(split));
		}
		
		return ExitStatus.Success;
	}
	
	@Override
	public String getLogFilename()
	{
		return "App3.log";
	}
}
