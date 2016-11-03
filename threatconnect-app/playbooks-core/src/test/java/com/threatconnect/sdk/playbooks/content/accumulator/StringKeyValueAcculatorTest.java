package com.threatconnect.sdk.playbooks.content.accumulator;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.playbooks.content.entity.StringKeyValue;
import com.threatconnect.sdk.playbooks.db.DBService;
import com.threatconnect.sdk.playbooks.db.EmbeddedMapDBService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author Greg Marut
 */
public class StringKeyValueAcculatorTest
{
	private static final Logger logger = LoggerFactory.getLogger(StringKeyValueAcculatorTest.class);
	
	private AppConfig appConfig;
	
	private StringAccumulator stringAccumulator;
	private StringKeyValueAccumulator stringKeyValueAccumulator;
	private StringKeyValueArrayAccumulator stringKeyValueArrayAccumulator;
	
	@Before
	public void init()
	{
		//register the inmemory database
		DBService dbService = new EmbeddedMapDBService();
		
		//create the string accumulator
		stringAccumulator = new StringAccumulator(dbService);
		stringKeyValueAccumulator = new StringKeyValueAccumulator(dbService);
		stringKeyValueArrayAccumulator = new StringKeyValueArrayAccumulator(dbService);
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
	public void arrayTest() throws ContentException
	{
		StringKeyValue[] kvs = new StringKeyValue[3];
		kvs[0] = new StringKeyValue();
		kvs[0].setKey("key0");
		kvs[0].setValue("value0");
		kvs[1] = new StringKeyValue();
		kvs[1].setKey("key1");
		kvs[1].setValue("value1");
		kvs[2] = new StringKeyValue();
		kvs[2].setKey("key2");
		kvs[2].setValue("value2");
		
		stringKeyValueArrayAccumulator.writeContent("#App:123:kvs!KeyValueArray", Arrays.asList(kvs));
		
		List<StringKeyValue> result = stringKeyValueArrayAccumulator.readContent("#App:123:kvs!KeyValueArray");
		
		Assert.assertNotNull(result);
		Assert.assertEquals(3, result.size());
		Assert.assertEquals(kvs[0].getKey(), result.get(0).getKey());
		Assert.assertEquals(kvs[0].getValue(), result.get(0).getValue());
		Assert.assertEquals(kvs[1].getKey(), result.get(1).getKey());
		Assert.assertEquals(kvs[1].getValue(), result.get(1).getValue());
		Assert.assertEquals(kvs[2].getKey(), result.get(2).getKey());
		Assert.assertEquals(kvs[2].getValue(), result.get(2).getValue());
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
