package com.threatconnect.sdk.blueprints.content.accumulator;

import com.threatconnect.sdk.app.AppConfig;
import com.threatconnect.sdk.blueprints.app.BlueprintsAppConfig;
import com.threatconnect.sdk.blueprints.content.entity.StringKeyValue;
import com.threatconnect.sdk.blueprints.db.DBService;
import com.threatconnect.sdk.blueprints.db.DBServiceFactory;
import com.threatconnect.sdk.blueprints.db.EmbeddedMapDBService;
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
		AppConfig.getInstance().set(BlueprintsAppConfig.PARAM_DB_TYPE, "Memory");

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
}
