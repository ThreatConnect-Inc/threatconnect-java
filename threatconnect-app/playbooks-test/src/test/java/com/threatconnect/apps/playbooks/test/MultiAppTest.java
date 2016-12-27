package com.threatconnect.apps.playbooks.test;

import com.threatconnect.apps.playbooks.test.app2.App2;
import com.threatconnect.apps.playbooks.test.app3.App3;
import com.threatconnect.apps.playbooks.test.config.PlaybooksTestConfiguration;
import com.threatconnect.apps.playbooks.test.orc.PlaybooksOrchestrationBuilder;
import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

/**
 * @author Greg Marut
 */
public class MultiAppTest
{
	@Before
	public void init()
	{
		PlaybooksTestConfiguration.getInstance().registerEmbeddedDBService();
		PlaybooksTestConfiguration.getInstance().loadFileAndConfigure(new File("src/test/resources/App2.install.json"));
		PlaybooksTestConfiguration.getInstance().loadFileAndConfigure(new File("src/test/resources/App3.install.json"));
	}
	
	@Test
	public void joinAndSplitStrings() throws Exception
	{
		//@formatter:off
		//create a new playbooks orchestration builder for defining our runtime
		PlaybooksOrchestrationBuilder
			.runApp(App2.class)
				.withAppParam()
					.set(App2.PARAM_JOIN_ON, ",")
				.then()
				.withInput()
					.asStringList(App2.PARAM_INPUT_ARRAY, Arrays.asList("one", "two", "three", "four"))
				.then()
				.onSuccess().assertOutput()
					.assertEquals(App2.PARAM_OUTPUT_CONCAT, PlaybookVariableType.String, "one,two,three,four")
				.then()
			.runApp(App3.class)
				.withAppParam()
					.set(App3.PARAM_SPLIT_ON, ",")
				.then()
				.withInput()
					.fromLastRunUpstreamApp(App3.PARAM_INPUT_CONCAT, App2.class, App2.PARAM_OUTPUT_CONCAT, PlaybookVariableType.String)
				.then()
				.onSuccess().assertOutput()
					.assertNotNull(App3.PARAM_OUTPUT_SPLIT, PlaybookVariableType.StringArray)
					.assertStringArraySize(App3.PARAM_OUTPUT_SPLIT, PlaybookVariableType.StringArray, 4)
					.assertStringArrayEquals(App3.PARAM_OUTPUT_SPLIT, PlaybookVariableType.StringArray, Arrays.asList("one", "two", "three", "four"))
				.then()
			//execute the apps
			.build().run();
		//@formatter:on
	}
}
