package com.threatconnect.apps.playbooks.test.app1;

import com.threatconnect.apps.playbooks.test.config.PlaybooksTestConfiguration;
import com.threatconnect.apps.playbooks.test.orc.PlaybooksOrchestrationBuilder;
import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * @author Greg Marut
 */
public class App1Test
{
	@Before
	public void init()
	{
		PlaybooksTestConfiguration.getInstance().registerEmbeddedDBService();
		PlaybooksTestConfiguration.getInstance().loadFileAndConfigure(new File("src/test/resources/App1.install.json"));
	}
	
	@Test
	public void test() throws Exception
	{
		//@formatter:off
		//create a new playbooks orchestration builder for defining our runtime
		PlaybooksOrchestrationBuilder
			.runApp(App1.class)
				.withPlaybookParam()
					.asString(App1.PARAM_INPUT_FIRST_NAME, "Greg")
					.asString(App1.PARAM_INPUT_LAST_NAME, "Marut")
				.then()
				.onSuccess().assertOutput()
					.assertEquals(App1.OUTPUT_NAME, PlaybookVariableType.String, "Greg Marut")
				.then()
			//execute the apps
			.build().run();
		//@formatter:on
	}
}
