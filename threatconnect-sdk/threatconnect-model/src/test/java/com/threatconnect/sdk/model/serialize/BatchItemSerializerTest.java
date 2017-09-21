package com.threatconnect.sdk.model.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.gregmarut.support.beangenerator.BeanPropertyGenerator;
import com.gregmarut.support.beangenerator.rule.RuleBuilder;
import com.gregmarut.support.beangenerator.rule.condition.FieldNameMatchesCondition;
import com.gregmarut.support.beangenerator.value.NullValue;
import com.gregmarut.support.beangenerator.value.StringValue;
import com.threatconnect.sdk.model.Address;
import com.threatconnect.sdk.model.Attribute;
import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.FileOccurrence;
import com.threatconnect.sdk.model.Group;
import com.threatconnect.sdk.model.GroupType;
import com.threatconnect.sdk.model.Host;
import com.threatconnect.sdk.model.Incident;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.ItemType;
import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.sdk.model.Threat;
import com.threatconnect.sdk.model.Url;
import com.threatconnect.sdk.model.util.TagUtil;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BatchItemSerializerTest
{
	private static final Logger logger = LoggerFactory.getLogger(BatchItemSerializerTest.class);
	
	private final BeanPropertyGenerator beanPropertyGenerator;
	private final JsonParser jsonParser;
	
	public BatchItemSerializerTest()
	{
		this.jsonParser = new JsonParser();
		beanPropertyGenerator = new BeanPropertyGenerator(false);
		
		StringValue xidGenValue = new StringValue()
		{
			@Override
			public String getValue()
			{
				return UUID.randomUUID().toString();
			}
			
			@Override
			public String getValue(final Field field)
			{
				return getValue();
			}
		};
		
		RuleBuilder ruleBuilder = new RuleBuilder(beanPropertyGenerator.getConfiguration().getRuleMapping());
		ruleBuilder.forType(String.class).when(new FieldNameMatchesCondition("xid"))
			.thenReturn(new NullValue<String>(String.class));
		ruleBuilder.forType(String.class).when(new FieldNameMatchesCondition("md5"))
			.thenReturn("098f6bcd4621d373cade4e832627b4f6");
		ruleBuilder.forType(String.class).when(new FieldNameMatchesCondition("sha1"))
			.thenReturn("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3");
		ruleBuilder.forType(String.class).when(new FieldNameMatchesCondition("sha256"))
			.thenReturn("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08");
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
		Attribute incidentAttribute = new Attribute();
		incidentAttribute.setType("Source");
		incidentAttribute.setValue("GroupUnitTest");
		incident.getAttributes().add(incidentAttribute);
		Attribute hostAttribute = new Attribute();
		hostAttribute.setType("Source");
		hostAttribute.setValue("IndicatorUnitTest");
		host.getAttributes().add(hostAttribute);
		
		SecurityLabel securityLabel = new SecurityLabel();
		securityLabel.setName("TLP:GREEN");
		securityLabel.setDescription("This is a security label");
		incident.getSecurityLabels().add(securityLabel);
		address.getSecurityLabels().add(securityLabel);
		
		//serialize the results
		List<? extends Item> items = Arrays.asList(incident, host);
		BatchItemSerializer batchItemSerializer = new BatchItemSerializer(items);
		String json = batchItemSerializer.convertToJsonString();
		logger.info(json);
		
		//deserialize the results
		BatchItemDeserializer batchItemDeserializer = new BatchItemDeserializer(json);
		List<Item> restoredItems = batchItemDeserializer.convertToItems();
		
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
		
		Assert.assertEquals("TLP:GREEN", group.getSecurityLabels().get(0).getName());
		Assert.assertEquals("This is a security label", group.getSecurityLabels().get(0).getDescription());
	}
	
	@Test
	public void serializeInflate2() throws IOException, NoSuchFieldException, IllegalAccessException, ParseException
	{
		java.io.File source = new java.io.File("src/test/resources/sample.json");
		Assert.assertTrue(source.exists());
		
		try (FileReader reader = new FileReader(source))
		{
			//read the items in
			JsonElement jsonElement = jsonParser.parse(reader);
			
			logger.info(jsonElement.toString());
			BatchItemDeserializer batchItemDeserializer = new BatchItemDeserializer(jsonElement.getAsJsonObject());
			batchItemDeserializer.convertToItems();
			
			//use reflection to read the xid map from this class
			Field field = BatchItemDeserializer.class.getDeclaredField("xidMap");
			field.setAccessible(true);
			Map<String, Item> xidMap = (Map<String, Item>) field.get(batchItemDeserializer);
			File file = (File) xidMap.get("e336e2dd-5dfb-48cd-a33a-f8809e83e904:170139");
			Assert.assertNotNull(file);
			
			Assert.assertEquals(1, file.getFileOccurrences().size());
			FileOccurrence fileOccurrence = file.getFileOccurrences().get(0);
			
			Assert.assertEquals("drop1.exe", fileOccurrence.getFileName());
			Assert.assertEquals("C:\\test\\", fileOccurrence.getPath());
			
			Date d = new SimpleDateFormat(Constants.ISO_DATE_TIME_FORMAT).parse(
				"2017-03-03T18:00:00-06:00");
			Assert.assertEquals(d, fileOccurrence.getDate());
		}
	}
	
	@Test
	public void serializeInflate3() throws IOException
	{
		//build some test objects with some associations
		Incident incident = beanPropertyGenerator.get(Incident.class);
		Threat threat = beanPropertyGenerator.get(Threat.class);
		incident.getAssociatedItems().add(threat);
		
		//serialize the results
		List<? extends Item> items = Arrays.asList(incident);
		BatchItemSerializer batchItemSerializer = new BatchItemSerializer(items);
		String json = batchItemSerializer.convertToJsonString();
		logger.info(json);
		
		//deserialize the results
		BatchItemDeserializer batchItemDeserializer = new BatchItemDeserializer(json);
		List<Item> restoredItems = batchItemDeserializer.convertToItems();
		
		Assert.assertEquals(1, restoredItems.size());
		Assert.assertEquals(ItemType.GROUP, restoredItems.get(0).getItemType());
		
		Incident restoredIncident = (Incident) restoredItems.get(0);
		Assert.assertEquals(incident.getXid(), restoredIncident.getXid());
		Assert.assertEquals(1, restoredIncident.getAssociatedItems().size());
		
		Threat restoredThreat = (Threat) restoredIncident.getAssociatedItems().iterator().next();
		Assert.assertEquals(threat.getXid(), restoredThreat.getXid());
	}
	
	@Test
	public void serializeInflate4() throws IOException, ParseException
	{
		File file = beanPropertyGenerator.get(File.class);
		file.getFileOccurrences().clear();
		
		FileOccurrence fileOccurrence = beanPropertyGenerator.get(FileOccurrence.class);
		fileOccurrence.setDate(
			new SimpleDateFormat(Constants.ISO_DATE_TIME_FORMAT).parse("2017-03-03T18:00:00-06:00"));
		file.getFileOccurrences().add(fileOccurrence);
		
		//serialize the results
		List<? extends Item> items = Arrays.asList(file);
		BatchItemSerializer batchItemSerializer = new BatchItemSerializer(items);
		String json = batchItemSerializer.convertToJsonString();
		logger.info(json);
		
		//deserialize the results
		BatchItemDeserializer batchItemDeserializer = new BatchItemDeserializer(json);
		List<Item> restoredItems = batchItemDeserializer.convertToItems();
		
		File restoredFile = (File) restoredItems.get(0);
		Assert.assertEquals(1, restoredFile.getFileOccurrences().size());
		FileOccurrence restoreFileOccurrence = restoredFile.getFileOccurrences().get(0);
		
		Assert.assertEquals(fileOccurrence.getFileName(), restoreFileOccurrence.getFileName());
		Assert.assertEquals(fileOccurrence.getPath(), restoreFileOccurrence.getPath());
		Assert.assertEquals(fileOccurrence.getDate(), restoreFileOccurrence.getDate());
	}
}
