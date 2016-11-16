package com.threatconnect.apps.app.test.app1;

import com.threatconnect.apps.app.test.config.AppTestConfiguration;
import com.threatconnect.apps.app.test.orc.AppOrchestrationBuilder;
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
		AppTestConfiguration.getInstance().loadFileAndConfigure(new File("src/test/resources/App1.install.json"));
	}
	
	@Test
	public void test() throws Exception
	{
		//@formatter:off
		//create a new playbooks orchestration builder for defining our runtime
		AppOrchestrationBuilder
			.runApp(App1.class)
				.withAppParam()
					.set(App1.PARAM_FIRST_NAME, "Greg")
					.set(App1.PARAM_LAST_NAME, "Marut")
				.then()
				.onSuccess().assertOutput()
					.assertMessageTcContains("Hello Greg Marut")
				.then()
			//execute the apps
			.build().run();
		//@formatter:on
	}
}
