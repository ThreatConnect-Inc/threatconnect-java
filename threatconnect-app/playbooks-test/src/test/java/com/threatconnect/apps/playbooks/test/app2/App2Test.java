package com.threatconnect.apps.playbooks.test.app2;

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
public class App2Test
{
	@Before
	public void init()
	{
		PlaybooksTestConfiguration.getInstance().registerEmbeddedDBService();
		PlaybooksTestConfiguration.getInstance().loadFileAndConfigure(new File("src/test/resources/App2.install.json"));
	}
	
	@Test
	public void test() throws Exception
	{
		//@formatter:off
		//create a new playbooks orchestration builder for defining our runtime
		PlaybooksOrchestrationBuilder
			.runApp(App2.class)
				.withAppParam()
					.set(App2.PARAM_JOIN_ON, ",")
				.then()
				.withPlaybookParam()
					.asStringList(App2.PARAM_INPUT_ARRAY, Arrays.asList("one", "two", "three"))
				.then()
				.onSuccess().assertOutput()
					.assertEquals(App2.PARAM_OUTPUT_CONCAT, StandardPlaybookType.String, "one,two,three")
				.then()
			//execute the apps
			.build().run();
		//@formatter:on
	}
}
