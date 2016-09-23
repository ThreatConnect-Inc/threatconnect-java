package com.threatconnect.sdk.playbooks.content.accumulator;

import com.threatconnect.sdk.app.AppConfig;
import com.threatconnect.sdk.playbooks.app.PlaybooksAppConfig;
import com.threatconnect.sdk.playbooks.content.entity.StringKeyValue;
import com.threatconnect.sdk.playbooks.db.DBService;
import com.threatconnect.sdk.playbooks.db.DBServiceFactory;
import com.threatconnect.sdk.playbooks.db.EmbeddedMapDBService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Marut
 */
public class StringKeyValueAcculatorTest
{
	private static final Logger logger = LoggerFactory.getLogger(StringKeyValueAcculatorTest.class);

	private StringAccumulator stringAccumulator;
	private StringKeyValueAccumulator stringKeyValueAccumulator;

	@Before
	public void init()
	{
		//register the inmemory database
		DBService dbService = new EmbeddedMapDBService();
		DBServiceFactory.registerCustomDBService("Memory", dbService);
		AppConfig.getInstance().set(PlaybooksAppConfig.PARAM_DB_TYPE, "Memory");

		//create the string accumulator
		stringAccumulator = new StringAccumulator(dbService);
		stringKeyValueAccumulator = new StringKeyValueAccumulator(dbService);
	}

	@Test
	public void basicTest() throws ContentException
	{
		StringKeyValue kv = new StringKeyValue();
		kv.setKey("somekey");
		kv.setValue("somevalue");
		
		stringKeyValueAccumulator.writeContent("#App:123:kv!KeyValue", kv);
		
		StringKeyValue result = stringKeyValueAccumulator.readContent("#App:123:kv!KeyValue");

		Assert.assertNotNull(result);
		Assert.assertEquals(kv.getKey(), result.getKey());
		Assert.assertEquals(kv.getValue(), result.getValue());
	}
	
	@Test
	public void embeddedVariableTest() throws ContentException
	{
		stringAccumulator.writeContent("#App:123:name!String", "Greg");
		stringAccumulator.writeContent("#App:123:hello!String", "Hello #App:123:name!String!");
		
		StringKeyValue kv = new StringKeyValue();
		kv.setKey("somekey");
		kv.setValue("#App:123:hello!String");
		
		stringKeyValueAccumulator.writeContent("#App:123:kv!KeyValue", kv);
		
		StringKeyValue result = stringKeyValueAccumulator.readContent("#App:123:kv!KeyValue");
		
		Assert.assertNotNull(result);
		Assert.assertEquals(kv.getKey(), result.getKey());
		Assert.assertEquals("Hello Greg!", result.getValue());
	}
}
