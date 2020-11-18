package com.threatconnect.apps.playbooks.test.app1;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.execution.entity.KeyValue;
import com.threatconnect.apps.playbooks.test.config.PlaybooksTestConfiguration;
import com.threatconnect.apps.playbooks.test.orc.PlaybooksOrchestrationBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
		List<KeyValue> kvs = new ArrayList<KeyValue>();
		kvs.add(new KeyValue("Key", "Value"));
		
		//@formatter:off
		//create a new playbooks orchestration builder for defining our runtime
		PlaybooksOrchestrationBuilder
			.runApp(App1.class)
				.withPlaybookParam()
					.asString(App1.PARAM_INPUT_FIRST_NAME, "Greg")
					.asString(App1.PARAM_INPUT_LAST_NAME, "Marut")
					.asKeyValueArray(App1.PARAM_INPUT_KVS, kvs)
				.then()
				.onSuccess().assertOutput()
					.assertEquals(App1.OUTPUT_NAME, StandardPlaybookType.String, "Greg Marut")
				.then()
			//execute the apps
			.build().run();
		//@formatter:on
	}
}
