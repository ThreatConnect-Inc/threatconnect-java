package com.threatconnect.apps.playbooks.test.app3;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.apps.playbooks.test.config.PlaybooksTestConfiguration;
import com.threatconnect.apps.playbooks.test.orc.PlaybooksOrchestrationBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

/**
 * @author Greg Marut
 */
public class App3Test
{
	@Before
	public void init()
	{
		PlaybooksTestConfiguration.getInstance().registerEmbeddedDBService();
		PlaybooksTestConfiguration.getInstance().loadFileAndConfigure(new File("src/test/resources/App3.install.json"));
	}
	
	@Test
	public void test() throws Exception
	{
		//@formatter:off
		//create a new playbooks orchestration builder for defining our runtime
		PlaybooksOrchestrationBuilder
			.runApp(App3.class)
				.withAppParam()
					.set(App3.PARAM_SPLIT_ON, ",")
				.then()
				.withPlaybookParam()
					.asString(App3.PARAM_INPUT_CONCAT,"one,two,three")
				.then()
				.onSuccess().assertOutput()
					.assertNotNull(App3.PARAM_OUTPUT_SPLIT, StandardPlaybookType.StringArray)
					.assertStringArraySize(App3.PARAM_OUTPUT_SPLIT, StandardPlaybookType.StringArray, 3)
					.assertStringArrayEquals(App3.PARAM_OUTPUT_SPLIT, StandardPlaybookType.StringArray, Arrays.asList("one", "two", "three"))
				.then()
			//execute the apps
			.build().run();
		//@formatter:on
	}
}
