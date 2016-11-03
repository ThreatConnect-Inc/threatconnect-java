package com.threatconnect.apps.playbooks.test.app2;

import com.threatconnect.sdk.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.app.apps.ExitStatus;
import com.threatconnect.sdk.playbooks.app.PlaybooksApp;
import com.threatconnect.sdk.playbooks.app.PlaybooksAppConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Greg Marut
 */
public class App2 extends PlaybooksApp
{
	public static final String PARAM_JOIN_ON = "join_on";
	public static final String PARAM_INPUT_ARRAY = "input_array";
	
	public static final String PARAM_OUTPUT_CONCAT = "test.app2.concat";
	
	@Override
	protected ExitStatus execute(final PlaybooksAppConfig playbooksAppConfig) throws Exception
	{
		//read the parameters
		String joinOn = readStringContent(PARAM_JOIN_ON);
		List<String> arrayList = readStringListContent(PARAM_INPUT_ARRAY);
		
		//join the array as a string
		String outputValue = StringUtils.join(arrayList, joinOn);
		
		//check to see if the output needs to be written
		if (isOutputParamExpected(PARAM_OUTPUT_CONCAT, PlaybookVariableType.String))
		{
			//write the output
			writeStringContent(PARAM_OUTPUT_CONCAT, outputValue);
		}
		
		return ExitStatus.Success;
	}
	
	@Override
	public String getLogFilename()
	{
		return "App2.log";
	}
}
