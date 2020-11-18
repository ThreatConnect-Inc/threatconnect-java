package com.threatconnect.apps.playbooks.test.app5;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.execution.entity.KeyValue;
import com.threatconnect.apps.playbooks.test.config.PlaybooksTestConfiguration;
import com.threatconnect.apps.playbooks.test.orc.PlaybooksOrchestrationBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Greg Marut
 */
public class App5Test
{
	private static final String FIRST = "app5.first";
	private static final String SECOND = "app5.second";
	private static final String THIRD = "app5.third";
	
	@Before
	public void init()
	{
		PlaybooksTestConfiguration.getInstance().registerEmbeddedDBService();
		
		//@formatter:off
		PlaybooksTestConfiguration.getInstance()
			.createPlaybookConfigBuilder(new File("src/test/resources/App5.install.json"))
				.addOutputVariable(FIRST, StandardPlaybookType.String)
				.addOutputVariable(SECOND, StandardPlaybookType.String)
				.addOutputVariable(THIRD, StandardPlaybookType.String)
			.build();
		//@formatter:on
	}
	
	@Test
	public void test() throws Exception
	{
		List<KeyValue> mapping = new ArrayList<KeyValue>();
		mapping.add(new KeyValue(FIRST, "0"));
		mapping.add(new KeyValue(SECOND, "1"));
		mapping.add(new KeyValue(THIRD, "2"));
		
		//@formatter:off
		//create a new playbooks orchestration builder for defining our runtime
		PlaybooksOrchestrationBuilder
			.runApp(App5.class)
				.withPlaybookParam()
					.asStringList(App5.PARAM_INPUT_ARRAY, Arrays.asList("one", "two", "three"))
					.asKeyValueArray(App5.PARAM_INPUT_MAPPING, mapping)
				.then()
				.onSuccess().assertOutput()
					.assertEquals(FIRST, StandardPlaybookType.String, "one")
					.assertEquals(SECOND, StandardPlaybookType.String, "two")
					.assertEquals(THIRD, StandardPlaybookType.String, "three")
				.then()
			//execute the apps
			.build().run();
		//@formatter:on
	}
}
