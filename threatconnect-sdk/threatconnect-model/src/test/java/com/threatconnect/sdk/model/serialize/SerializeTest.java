package com.threatconnect.sdk.model.serialize;

import com.google.gson.Gson;
import com.gregmarut.support.beangenerator.BeanPropertyGenerator;
import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.Incident;
import com.threatconnect.sdk.model.Url;
import com.threatconnect.sdk.model.util.ModelSerializationUtil;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		
		//serialize the item
		String json = gson.toJson(incident);
		logger.debug(json);
		Incident restored = ModelSerializationUtil.fromJson(json);
		
		Assert.assertEquals(incident.getClass(), restored.getClass());
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
		String json = gson.toJson(incident);
		logger.debug(json);
		Incident restoredIncident = ModelSerializationUtil.fromJson(json);
		
		Assert.assertTrue(restoredIncident.getAssociatedItems().contains(file));
		Assert.assertTrue(restoredIncident.getAssociatedItems().contains(url));
	}
	
	@Test
	public void gsonTest3()
	{
		File file = beanPropertyGenerator.get(File.class);
		
		//serialize the item
		String json = gson.toJson(file);
		File restored = ModelSerializationUtil.fromJson(json);
		
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
		
		//serialize the item
		String json = gson.toJson(incident);
		logger.debug(json);
		Incident restored = ModelSerializationUtil.fromJson(json);
		
		//serialize the items list again
		String json2 = gson.toJson(restored);
		
		//make sure both json objects are the same
		Assert.assertEquals(json, json2);
	}
}
