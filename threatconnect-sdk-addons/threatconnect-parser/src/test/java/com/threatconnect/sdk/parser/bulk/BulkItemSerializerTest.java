package com.threatconnect.sdk.parser.bulk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gregmarut.support.beangenerator.BeanPropertyGenerator;
import com.threatconnect.sdk.model.Address;
import com.threatconnect.sdk.model.Attribute;
import com.threatconnect.sdk.model.Group;
import com.threatconnect.sdk.model.GroupType;
import com.threatconnect.sdk.model.Host;
import com.threatconnect.sdk.model.Incident;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.ItemType;
import com.threatconnect.sdk.model.Url;
import com.threatconnect.sdk.model.util.TagUtil;
import com.threatconnect.sdk.parser.service.bulk.BulkItemDeserializer;
import com.threatconnect.sdk.parser.service.bulk.BulkItemSerializer;
import com.threatconnect.sdk.parser.util.AttributeHelper;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BulkItemSerializerTest
{
	private static final Logger logger = LoggerFactory.getLogger(BulkItemSerializerTest.class);
	
	private final BeanPropertyGenerator beanPropertyGenerator;
	private final Gson gson;
	private final JsonParser jsonParser;
	
	public BulkItemSerializerTest()
	{
		beanPropertyGenerator = new BeanPropertyGenerator(false);
		this.gson = new GsonBuilder().setPrettyPrinting().create();
		this.jsonParser = new JsonParser();
	}
	
	@Test
	public void serializeInflate() throws IOException
	{
		//build some test objects with some associations
		Incident incident = beanPropertyGenerator.get(Incident.class);
		Address address = beanPropertyGenerator.get(Address.class);
		Url url = beanPropertyGenerator.get(Url.class);
		Host host = beanPropertyGenerator.get(Host.class);
		incident.getAssociatedItems().add(address);
		incident.getAssociatedItems().add(url);
		
		TagUtil.addTag("GroupTag", incident);
		TagUtil.addTag("IndicatorTag", host);
		AttributeHelper.addSourceAttribute(incident, "GroupUnitTest");
		AttributeHelper.addSourceAttribute(host, "IndicatorUnitTest");
		
		//serialize the results
		List<? extends Item> items = Arrays.asList(incident, host);
		BulkItemSerializer bulkItemSerializer = new BulkItemSerializer(items);
		JsonObject root = bulkItemSerializer.convertToJson();
		logger.info(gson.toJson(root));
		
		//deserialize the results
		BulkItemDeserializer bulkItemDeserializer = new BulkItemDeserializer(root);
		List<Item> restoredItems = bulkItemDeserializer.convertToItems();
		
		Assert.assertEquals(2, restoredItems.size());
		Assert.assertEquals(ItemType.GROUP, restoredItems.get(0).getItemType());
		Assert.assertEquals(ItemType.INDICATOR, restoredItems.get(1).getItemType());
		
		Group group = (Group) restoredItems.get(0);
		Indicator indicator = (Indicator) restoredItems.get(1);
		Assert.assertEquals(GroupType.INCIDENT, group.getGroupType());
		Assert.assertEquals(2, group.getAssociatedItems().size());
		
		Assert.assertEquals(1, group.getTags().size());
		Assert.assertEquals("GroupTag", group.getTags().iterator().next());
		
		Assert.assertEquals(1, group.getAttributes().size());
		Attribute groupAttribute = group.getAttributes().iterator().next();
		Assert.assertEquals("GroupUnitTest", groupAttribute.getValue());
		Assert.assertEquals("Source", groupAttribute.getType());
		
		Assert.assertEquals(1, indicator.getTags().size());
		Assert.assertEquals("IndicatorTag", indicator.getTags().iterator().next());
		
		Assert.assertEquals(1, indicator.getAttributes().size());
		Attribute indicatorAttribute = indicator.getAttributes().iterator().next();
		Assert.assertEquals("IndicatorUnitTest", indicatorAttribute.getValue());
		Assert.assertEquals("Source", indicatorAttribute.getType());
	}
	
	@Test
	public void serializeInflate2() throws IOException
	{
		File source = new File("src/test/resources/sample.json");
		Assert.assertTrue(source.exists());
		
		try (FileReader reader = new FileReader(source))
		{
			//read the items in
			JsonElement jsonElement = jsonParser.parse(reader);
			
			logger.info(gson.toJson(jsonElement));
			BulkItemDeserializer bulkItemDeserializer = new BulkItemDeserializer(jsonElement.getAsJsonObject());
			List<Item> items = bulkItemDeserializer.convertToItems();
		}
	}
}
