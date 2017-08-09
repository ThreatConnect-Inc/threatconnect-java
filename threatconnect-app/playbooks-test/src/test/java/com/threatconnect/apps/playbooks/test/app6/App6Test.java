package com.threatconnect.apps.playbooks.test.app6;

import com.google.gson.Gson;
import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.apps.playbooks.test.config.PlaybooksTestConfiguration;
import com.threatconnect.apps.playbooks.test.orc.PlaybooksOrchestrationBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * @author Greg Marut
 */
public class App6Test
{
	public static final String CUSTOM_PLAYBOOK_TYPE = "User";
	
	@Before
	public void init()
	{
		PlaybooksTestConfiguration.getInstance().registerEmbeddedDBService();
		PlaybooksTestConfiguration.getInstance()
			.loadFileAndConfigure(new File("src/test/resources/App6Create.install.json"));
		PlaybooksTestConfiguration.getInstance()
			.loadFileAndConfigure(new File("src/test/resources/App6Read.install.json"));
	}
	
	@Test
	public void test() throws Exception
	{
		//@formatter:off
		//create a new playbooks orchestration builder for defining our runtime
		PlaybooksOrchestrationBuilder
			.runApp(App6Create.class)
				.withPlaybookParam()
					.asString(App6Create.PARAM_INPUT_FIRST_NAME, "Greg")
					.asString(App6Create.PARAM_INPUT_LAST_NAME, "Marut")
				.then().onSuccess()
			.runApp(App6Read.class)
				.withPlaybookParam()
					.fromLastRunUpstreamApp(App6Read.PARAM_USER, App6Create.class, App6Create.OUTPUT_NAME, App6Test.CUSTOM_PLAYBOOK_TYPE)
				.then()
				.onSuccess().assertOutput()
					.assertEquals(App6Read.OUTPUT_NAME, PlaybookVariableType.String, "Greg Marut")
				.then()
			//execute the apps
			.build().run();
		//@formatter:on
	}
	
	@Test
	public void test1() throws Exception
	{
		User user = new User();
		user.setFirstName("Greg");
		user.setLastName("Marut");
		
		final byte[] data = new Gson().toJson(user).getBytes();
		
		//@formatter:off
		//create a new playbooks orchestration builder for defining our runtime
		PlaybooksOrchestrationBuilder
			.runApp(App6Read.class)
				.withPlaybookParam()
					.asCustomType(App6Read.PARAM_USER, data, App6Test.CUSTOM_PLAYBOOK_TYPE)
				.then()
				.onSuccess().assertOutput()
					.assertEquals(App6Read.OUTPUT_NAME, PlaybookVariableType.String, "Greg Marut")
				.then()
			//execute the apps
			.build().run();
		//@formatter:on
	}
}
