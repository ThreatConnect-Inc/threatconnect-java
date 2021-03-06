package com.threatconnect.sdk.parser.bulk;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.gregmarut.support.beangenerator.BeanPropertyGenerator;
import com.threatconnect.sdk.model.Attribute;
import com.threatconnect.sdk.model.Host;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.parser.service.bulk.BulkIndicatorConverter;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BulkIndicatorConverterTest
{
	private BeanPropertyGenerator beanPropertyGenerator;
	
	public BulkIndicatorConverterTest()
	{
		beanPropertyGenerator = new BeanPropertyGenerator(false);
	}
	
	@Test
	public void serializeBoth()
	{
		List<Host> hosts = beanPropertyGenerator.getList(Host.class, 3);
		for (Indicator indicator : hosts)
		{
			indicator.getTags().add("Dell SecureWorks");
			indicator.getAttributes().add(beanPropertyGenerator.get(Attribute.class));
		}
		
		BulkIndicatorConverter converter = new BulkIndicatorConverter();
		JsonObject jsonObject = converter.convertToJson(hosts);
		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject));
		
		List<Indicator> indicators = converter.convertToIndicators(jsonObject);
		
		// there should be 3 indicators
		Assert.assertEquals(3, indicators.size());
		
		// they should be hosts
		Assert.assertEquals(Host.INDICATOR_TYPE, indicators.get(0).getIndicatorType());
		Assert.assertEquals(Host.INDICATOR_TYPE, indicators.get(1).getIndicatorType());
		Assert.assertEquals(Host.INDICATOR_TYPE, indicators.get(2).getIndicatorType());
		
		for (Indicator indicator : indicators)
		{
			Host host = (Host) indicator;
			
			// the rating and confidence should be 1
			Assert.assertEquals(1.0, host.getRating(), 0);
			Assert.assertEquals(1.0, host.getConfidence(), 0);
			
			// there should be one tag
			Assert.assertEquals(1, host.getTags().size());
			
			// it should be "Dell SecureWorks"
			Assert.assertEquals("Dell SecureWorks", host.getTags().iterator().next());
			
			// there should be one attribute
			Assert.assertEquals(1, host.getAttributes().size());
			
			Assert.assertEquals("type", host.getAttributes().iterator().next().getType());
			Assert.assertEquals("value", host.getAttributes().iterator().next().getValue());
		}
	}
}
