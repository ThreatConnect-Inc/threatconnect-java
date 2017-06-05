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

import java.util.List;

/**
 * @author Greg Marut
 */
public class SerializeTest
{
	private static final Logger logger = LoggerFactory.getLogger(SerializeTest.class);
	
	private final BeanPropertyGenerator beanPropertyGenerator;
	
	public SerializeTest()
	{
		this.beanPropertyGenerator = new BeanPropertyGenerator();
		RuleBuilder ruleBuilder = new RuleBuilder(beanPropertyGenerator.getConfiguration().getRuleMapping());
		ruleBuilder.forType(String.class).when(new FieldNameMatchesCondition("xid")).thenReturn(new NullValue<String>(String.class));
		ruleBuilder.forType(String.class).when(new FieldNameMatchesCondition("md5"))
			.thenReturn("098f6bcd4621d373cade4e832627b4f6");
		ruleBuilder.forType(String.class).when(new FieldNameMatchesCondition("sha1"))
			.thenReturn("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3");
		ruleBuilder.forType(String.class).when(new FieldNameMatchesCondition("sha256"))
			.thenReturn("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08");
	}
	
	@Test
	public void gsonTest1()
	{
		Incident incident = beanPropertyGenerator.get(Incident.class);
		
		//serialize the item
		BulkItemSerializer bulkItemSerializer = new BulkItemSerializer(incident);
		String json = bulkItemSerializer.convertToJsonString();
		logger.debug(json);
		BulkItemDeserializer bulkItemDeserializer = new BulkItemDeserializer(json);
		List<Item> restored = bulkItemDeserializer.convertToItems();
		
		Assert.assertEquals(incident.getClass(), restored.get(0).getClass());
		Assert.assertEquals(incident.getXid(), restored.get(0).getXid());
	}
	
	@Test
	public void gsonTest2()
	{
		Incident incident = beanPropertyGenerator.get(Incident.class);
		File file = beanPropertyGenerator.get(File.class);
		Url url = beanPropertyGenerator.get(Url.class);
		
		incident.getAssociatedItems().add(file);
		incident.getAssociatedItems().add(url);
		
		//serialize the item
		BulkItemSerializer bulkItemSerializer = new BulkItemSerializer(incident);
		String json = bulkItemSerializer.convertToJsonString();
		logger.debug(json);
		BulkItemDeserializer bulkItemDeserializer = new BulkItemDeserializer(json);
		List<Item> restored = bulkItemDeserializer.convertToItems();
		
		Assert.assertTrue(restored.get(0).getAssociatedItems().contains(file));
		Assert.assertTrue(restored.get(0).getAssociatedItems().contains(url));
		Assert.assertEquals(incident.getXid(), restored.get(0).getXid());
	}
	
	@Test
	public void gsonTest3()
	{
		File file = beanPropertyGenerator.get(File.class);
		
		//serialize the item
		BulkItemSerializer bulkItemSerializer = new BulkItemSerializer(file);
		String json = bulkItemSerializer.convertToJsonString();
		BulkItemDeserializer bulkItemDeserializer = new BulkItemDeserializer(json);
		List<Item> restored = bulkItemDeserializer.convertToItems();
		
		//serialize the items list again
		BulkItemSerializer bulkItemSerializer2 = new BulkItemSerializer(restored);
		String json2 = bulkItemSerializer2.convertToJsonString();
		
		//make sure both json objects are the same
		Assert.assertEquals(json, json2);
	}
	
	@Test
	public void gsonTest4()
	{
		Incident incident = beanPropertyGenerator.get(Incident.class);
		File file = beanPropertyGenerator.get(File.class);
		Url url = beanPropertyGenerator.get(Url.class);
		
		incident.getAssociatedItems().add(url);
		incident.getAssociatedItems().add(file);
		
		//serialize the item
		BulkItemSerializer bulkItemSerializer = new BulkItemSerializer(incident);
		String json = bulkItemSerializer.convertToJsonString();
		logger.debug(json);
		BulkItemDeserializer bulkItemDeserializer = new BulkItemDeserializer(json);
		List<Item> restored = bulkItemDeserializer.convertToItems();
		
		//serialize the items list again
		BulkItemSerializer bulkItemSerializer2 = new BulkItemSerializer(restored);
		String json2 = bulkItemSerializer2.convertToJsonString();
		
		//make sure both json objects are the same
		Assert.assertEquals(json, json2);
	}
}
