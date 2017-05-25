package com.threatconnect.app.playbooks.content.accumulator;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.playbooks.content.entity.KeyValue;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.db.EmbeddedMapDBService;
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
public class KeyValueAcculatorTest
{
	private static final Logger logger = LoggerFactory.getLogger(KeyValueAcculatorTest.class);
	
	private AppConfig appConfig;
	
	private StringAccumulator stringAccumulator;
	private KeyValueAccumulator keyValueAccumulator;
	private KeyValueArrayAccumulator keyValueArrayAccumulator;
	
	
	
	@Before
	public void init()
	{
		//register the inmemory database
		DBService dbService = new EmbeddedMapDBService();
		
		//create the string accumulator
		stringAccumulator = new StringAccumulator(dbService);
		keyValueAccumulator = new KeyValueAccumulator(dbService);
		keyValueArrayAccumulator = new KeyValueArrayAccumulator(dbService);
	}
	
	@Test
	public void basicTest() throws ContentException
	{
		KeyValue kv = new KeyValue("somekey", "somevalue");
		
		keyValueAccumulator.writeContent("#App:123:kv!KeyValue", kv);
		
		KeyValue result = keyValueAccumulator.readContent("#App:123:kv!KeyValue");
		
		Assert.assertNotNull(result);
		Assert.assertEquals(kv.getKey(), result.getKey());
		Assert.assertEquals(kv.getValue(), result.getValue());
	}
	
	@Test
	public void arrayTest() throws ContentException
	{
		KeyValue[] kvs = new KeyValue[3];
		kvs[0] = new KeyValue("key0", "value0");
		kvs[1] = new KeyValue("key1", "value1");
		kvs[2] = new KeyValue("key2", "value2");
		
		keyValueArrayAccumulator.writeContent("#App:123:kvs!KeyValueArray", Arrays.asList(kvs));
		
		List<KeyValue> result = keyValueArrayAccumulator.readContent("#App:123:kvs!KeyValueArray");
		
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
		
		KeyValue kv = new KeyValue("somekey", "#App:123:hello!String");
		
		keyValueAccumulator.writeContent("#App:123:kv!KeyValue", kv);
		
		KeyValue result = keyValueAccumulator.readContent("#App:123:kv!KeyValue");
		
		Assert.assertNotNull(result);
		Assert.assertEquals(kv.getKey(), result.getKey());
		Assert.assertEquals("Hello Greg!", result.getValue());
	}
}
