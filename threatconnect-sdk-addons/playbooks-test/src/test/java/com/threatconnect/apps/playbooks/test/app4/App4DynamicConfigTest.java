package com.threatconnect.apps.playbooks.test.app4;

import com.threatconnect.apps.playbooks.test.config.PlaybooksTestConfiguration;
import com.threatconnect.apps.playbooks.test.orc.PlaybooksOrchestrationBuilder;
import com.threatconnect.sdk.addons.util.config.install.ParamDataType;
import com.threatconnect.sdk.addons.util.config.install.PlaybookVariableType;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Greg Marut
 */
public class App4DynamicConfigTest
{
	@Before
	public void init()
	{
		PlaybooksTestConfiguration.getInstance().registerEmbeddedDBService();
		
		//@formatter:off
		//build the dynamic configuration for this app
		PlaybooksTestConfiguration.getInstance()
			.createPlaybookConfigBuilder(App4DynamicConfig.class)
				.addPlaybookParam(App4DynamicConfig.PARAM_INPUT_FIRST_NAME, ParamDataType.String, PlaybookVariableType.String)
				.addPlaybookParam(App4DynamicConfig.PARAM_INPUT_LAST_NAME, ParamDataType.String, PlaybookVariableType.String)
				.addOutputVariable(App4DynamicConfig.OUTPUT_NAME, PlaybookVariableType.String)
			.build();
		//@formatter:on
	}
	
	@Test
	public void test() throws Exception
	{
		//@formatter:off
		//create a new playbooks orchestration builder for defining our runtime
		PlaybooksOrchestrationBuilder
			.runApp(App4DynamicConfig.class)
				.withInput()
					.asString(App4DynamicConfig.PARAM_INPUT_FIRST_NAME, "Greg")
					.asString(App4DynamicConfig.PARAM_INPUT_LAST_NAME, "Marut")
				.then()
				.onSuccess().assertOutput()
					.assertEquals(App4DynamicConfig.OUTPUT_NAME, PlaybookVariableType.String, "Greg Marut")
				.then()
			//execute the apps
			.build().run();
		//@formatter:on
	}
}
