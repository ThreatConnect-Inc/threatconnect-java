package com.threatconnect.app.playbooks.content.accumulator;

import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.db.EmbeddedMapDBService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Marut
 */
public class StringAcculatorTest
{
	private static final Logger logger = LoggerFactory.getLogger(StringAcculatorTest.class);
	
	private StringAccumulator stringAccumulator;
	
	@Before
	public void init()
	{
		//register the inmemory database
		DBService dbService = new EmbeddedMapDBService();
		
		//create the string accumulator
		stringAccumulator = new StringAccumulator(dbService);
	}
	
	@Test
	public void embeddedStringVariables() throws ContentException
	{
		stringAccumulator.writeContent("#App:123:name!String", "Greg");
		stringAccumulator.writeContent("#App:123:hello!String", "Hello #App:123:name!String!!");
		
		String result = stringAccumulator.readContent("#App:123:hello!String");
		logger.info(result);
		
		Assert.assertEquals("Hello Greg!", result);
	}
	
	@Test
	public void multiEmbeddedStringVariables() throws ContentException
	{
		stringAccumulator.writeContent("#App:123:firstname!String", "Greg");
		stringAccumulator.writeContent("#App:123:lastname!String", "Marut");
		stringAccumulator
			.writeContent("#App:123:fullname!String", "#App:123:firstname!String #App:123:lastname!String");
		stringAccumulator.writeContent("#App:123:hello!String", "Hello #App:123:fullname!String!!");
		
		String result = stringAccumulator.readContent("#App:123:hello!String");
		logger.info(result);
		
		Assert.assertEquals("Hello Greg Marut!", result);
	}
	
	@Test
	public void repetitiveEmbeddedStringVariables() throws ContentException
	{
		stringAccumulator.writeContent("#App:123:test!String", "Test");
		stringAccumulator
			.writeContent("#App:123:test1!String", "#App:123:test!String #App:123:test!String! #App:123:test!String");
		
		String result = stringAccumulator.readContent("#App:123:test1!String");
		logger.info(result);
		
		Assert.assertEquals("Test Test Test", result);
	}
	
	@Test
	public void infiniteLoopEmbeddedStringVariables1() throws ContentException
	{
		stringAccumulator.writeContent("#App:123:one!String", "#App:123:two!String");
		stringAccumulator.writeContent("#App:123:two!String", "#App:123:one!String");
		
		String result = stringAccumulator.readContent("#App:123:one!String");
		logger.info(result);
		
		// this should have resolved to StringAccumulator.CYCLICAL_VARIABLE_REFERENCE and then detected that each
		// variable pointed to each other. At this point, a warning will be logged and it will make no further
		// attempt to resolve the next variable.
		Assert.assertEquals(StringAccumulator.CYCLICAL_VARIABLE_REFERENCE, result);
	}
	
	@Test
	public void infiniteLoopEmbeddedStringVariables2() throws ContentException
	{
		stringAccumulator.writeContent("#App:123:one!String", "#App:123:two!String");
		stringAccumulator.writeContent("#App:123:two!String", "#App:123:one!String");
		
		String result = stringAccumulator.readContent("#App:123:one!String and some other text");
		logger.info(result);
		
		// this should have resolved to StringAccumulator.CYCLICAL_VARIABLE_REFERENCE and then detected that each
		// variable pointed to each other. At this point, a warning will be logged and it will make no further
		// attempt to resolve the next variable.
		Assert.assertEquals(StringAccumulator.CYCLICAL_VARIABLE_REFERENCE + " and some other text", result);
	}
	
	@Test
	public void infiniteLoopEmbeddedStringVariables3() throws ContentException
	{
		stringAccumulator.writeContent("#App:123:one!String", "#App:123:two!String extra text");
		stringAccumulator.writeContent("#App:123:two!String", "#App:123:one!String");
		
		String result = stringAccumulator.readContent("#App:123:one!String and some more text");
		logger.info(result);
		
		// this should have resolved to StringAccumulator.CYCLICAL_VARIABLE_REFERENCE and then detected that each
		// variable pointed to each other. At this point, a warning will be logged and it will make no further
		// attempt to resolve the next variable.
		Assert.assertEquals(StringAccumulator.CYCLICAL_VARIABLE_REFERENCE + " extra text and some more text", result);
	}
	
	@Test
	public void nonRecursiveResolution() throws ContentException
	{
		stringAccumulator.writeContent("#App:123:json!String", "{\"value\": \"#App:123:value!String\"}");
		stringAccumulator.writeContent("#App:123:value!String", "123");
		
		String recursiveResolution = stringAccumulator.readContent("#App:123:json!String");
		String nonRecursiveResolution = stringAccumulator.readContent("#App:123:json!String", false);
		
		logger.info(recursiveResolution);
		logger.info(nonRecursiveResolution);
		
		Assert.assertEquals("{\"value\": \"123\"}", recursiveResolution);
		Assert.assertEquals("{\"value\": \"#App:123:value!String\"}", nonRecursiveResolution);
	}
}
