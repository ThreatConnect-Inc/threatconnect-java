package com.threatconnect.sdk.model.serialize;

import com.google.gson.Gson;
import com.gregmarut.support.beangenerator.BeanPropertyGenerator;
import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.Incident;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.Url;
import com.threatconnect.sdk.model.util.ModelSerializationUtil;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author Greg Marut
 */
public class SerializeTest
{
	private static final Logger logger = LoggerFactory.getLogger(SerializeTest.class);
	
	private final BeanPropertyGenerator beanPropertyGenerator;
	private final Gson gson;
	
	public SerializeTest()
	{
		this.beanPropertyGenerator = new BeanPropertyGenerator();
		this.gson = ModelSerializationUtil.createJson();
	}
	
	@Test
	public void gsonTest1()
	{
		Incident incident = beanPropertyGenerator.get(Incident.class);
		File file = beanPropertyGenerator.get(File.class);
		Url url = beanPropertyGenerator.get(Url.class);
		
		List<Item> items = Arrays.asList(incident, file, url);
		
		//serialize the items list
		String json = gson.toJson(items);
		logger.debug(json);
		List<Item> restored = ModelSerializationUtil.fromJson(json);
		
		Assert.assertEquals(incident.getClass(), restored.get(0).getClass());
		Assert.assertEquals(file, restored.get(1));
		Assert.assertEquals(url, restored.get(2));
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
		String json = gson.toJson(items);
		logger.debug(json);
		List<Item> restored = ModelSerializationUtil.fromJson(json);
		
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
		String json = gson.toJson(items);
		List<Item> restored = ModelSerializationUtil.fromJson(json);
		
		//serialize the items list again
		String json2 = gson.toJson(restored);
		
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
		String json = gson.toJson(items);
		List<Item> restored = ModelSerializationUtil.fromJson(json);
		
		//serialize the items list again
		String json2 = gson.toJson(restored);
		
		//make sure both json objects are the same
		Assert.assertEquals(json, json2);
	}
}
