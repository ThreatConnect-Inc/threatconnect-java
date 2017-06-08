package com.threatconnect.sdk.model.serialize;

import com.gregmarut.support.beangenerator.BeanPropertyGenerator;
import com.gregmarut.support.beangenerator.rule.RuleBuilder;
import com.gregmarut.support.beangenerator.rule.condition.FieldNameMatchesCondition;
import com.gregmarut.support.beangenerator.value.NullValue;
import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.Incident;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.Url;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author Greg Marut
 */
public class SerializeListTest
{
	private static final Logger logger = LoggerFactory.getLogger(SerializeListTest.class);
	
	private final BeanPropertyGenerator beanPropertyGenerator;
	
	public SerializeListTest()
	{
		this.beanPropertyGenerator = new BeanPropertyGenerator();
		RuleBuilder ruleBuilder = new RuleBuilder(beanPropertyGenerator.getConfiguration().getRuleMapping());
		ruleBuilder.forType(String.class).when(new FieldNameMatchesCondition("xid")).thenReturn(new NullValue<String>(String.class));
		ruleBuilder.forType(String.class).when(new FieldNameMatchesCondition("md5")).thenReturn("098f6bcd4621d373cade4e832627b4f6");
		ruleBuilder.forType(String.class).when(new FieldNameMatchesCondition("sha1")).thenReturn("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3");
		ruleBuilder.forType(String.class).when(new FieldNameMatchesCondition("sha256")).thenReturn("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08");
	}
	
	@Test
	public void gsonTest1()
	{
		Incident incident = beanPropertyGenerator.get(Incident.class);
		File file = beanPropertyGenerator.get(File.class);
		Url url = beanPropertyGenerator.get(Url.class);
		
		List<Item> items = Arrays.asList(incident, file, url);
		
		//serialize the items list
		BatchItemSerializer batchItemSerializer = new BatchItemSerializer(items);
		String json = batchItemSerializer.convertToJsonString();
		logger.debug(json);
		BatchItemDeserializer batchItemDeserializer = new BatchItemDeserializer(json);
		List<Item> restored = batchItemDeserializer.convertToItems();
		
		Assert.assertEquals(incident.getClass(), restored.get(0).getClass());
		Assert.assertEquals(url, restored.get(1));
		Assert.assertEquals(file, restored.get(2));
	}
	
	@Test
	public void gsonTest2()
	{
		Incident incident = beanPropertyGenerator.get(Incident.class);
		File file = beanPropertyGenerator.get(File.class);
		Url url = beanPropertyGenerator.get(Url.class);
		
		incident.getAssociatedItems().add(file);
		incident.getAssociatedItems().add(url);
		
		List<Item> items = Arrays.asList((Item) incident);
		
		//serialize the items list
		BatchItemSerializer batchItemSerializer = new BatchItemSerializer(items);
		String json = batchItemSerializer.convertToJsonString();
		logger.debug(json);
		BatchItemDeserializer batchItemDeserializer = new BatchItemDeserializer(json);
		List<Item> restored = batchItemDeserializer.convertToItems();
		
		Assert.assertEquals(1, restored.size());
		Assert.assertEquals(incident.getClass(), restored.get(0).getClass());
		Incident restoredIncident = (Incident) restored.get(0);
		
		Assert.assertTrue(restoredIncident.getAssociatedItems().contains(file));
		Assert.assertTrue(restoredIncident.getAssociatedItems().contains(url));
	}
	
	@Test
	public void gsonTest3()
	{
		Incident incident = beanPropertyGenerator.get(Incident.class);
		File file = beanPropertyGenerator.get(File.class);
		Url url = beanPropertyGenerator.get(Url.class);
		
		List<Item> items = Arrays.asList(incident, file, url);
		
		//serialize the items list
		BatchItemSerializer batchItemSerializer = new BatchItemSerializer(items);
		String json = batchItemSerializer.convertToJsonString();
		BatchItemDeserializer batchItemDeserializer = new BatchItemDeserializer(json);
		List<Item> restored = batchItemDeserializer.convertToItems();
		
		//serialize the items list again
		BatchItemSerializer batchItemSerializer2 = new BatchItemSerializer(restored);
		String json2 = batchItemSerializer2.convertToJsonString();
		
		//make sure both json objects are the same
		Assert.assertEquals(json, json2);
	}
	
	@Test
	public void gsonTest4()
	{
		Incident incident = beanPropertyGenerator.get(Incident.class);
		File file = beanPropertyGenerator.get(File.class);
		Url url = beanPropertyGenerator.get(Url.class);
		
		incident.getAssociatedItems().add(file);
		incident.getAssociatedItems().add(url);
		
		List<Item> items = Arrays.asList((Item) incident);
		
		//serialize the items list
		BatchItemSerializer batchItemSerializer = new BatchItemSerializer(items);
		String json = batchItemSerializer.convertToJsonString();
		BatchItemDeserializer batchItemDeserializer = new BatchItemDeserializer(json);
		List<Item> restored = batchItemDeserializer.convertToItems();
		
		//serialize the items list again
		//serialize the items list again
		BatchItemSerializer batchItemSerializer2 = new BatchItemSerializer(restored);
		String json2 = batchItemSerializer2.convertToJsonString();
		
		//make sure both json objects are the same
		Assert.assertEquals(json, json2);
	}
}
