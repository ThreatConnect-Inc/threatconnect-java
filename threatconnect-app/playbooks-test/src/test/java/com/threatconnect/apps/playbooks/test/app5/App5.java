package com.threatconnect.apps.playbooks.test.app5;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.apps.ExitStatus;
import com.threatconnect.app.execution.entity.KeyValue;
import com.threatconnect.app.playbooks.app.PlaybooksApp;
import com.threatconnect.app.playbooks.app.PlaybooksAppConfig;

import java.util.List;

/**
 * @author Greg Marut
 */
public class App5 extends PlaybooksApp
{
	public static final String PARAM_INPUT_ARRAY = "input_array";
	public static final String PARAM_INPUT_MAPPING = "input_mapping";
	
	public static final String PARAM_OUTPUT_CONCAT = "test.app5.concat";
	
	@Override
	protected ExitStatus execute(final PlaybooksAppConfig playbooksAppConfig) throws Exception
	{
		//read the parameters
		List<String> arrayList = readStringListContent(PARAM_INPUT_ARRAY);
		List<KeyValue> mappings = readKeyValueArrayContent(PARAM_INPUT_MAPPING);
		
		//for each of the mappings
		for (KeyValue mapping : mappings)
		{
			//check to see if the output needs to be written
			if (isOutputParamExpected(mapping.getKey(), StandardPlaybookType.String))
			{
				//write the output
				writeStringContent(mapping.getKey(), arrayList.get(Integer.parseInt(mapping.getValue().toString())));
			}
		}
		
		return ExitStatus.Success;
	}
	
	@Override
	public String getLogFilename()
	{
		return "App5.log";
	}
}
