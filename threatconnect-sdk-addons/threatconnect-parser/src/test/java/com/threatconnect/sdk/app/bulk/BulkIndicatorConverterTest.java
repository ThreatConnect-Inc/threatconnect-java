package com.threatconnect.sdk.app.bulk;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.gregmarut.support.beangenerator.BeanPropertyGenerator;
import com.threatconnect.sdk.parser.model.Attribute;
import com.threatconnect.sdk.parser.model.Host;
import com.threatconnect.sdk.parser.model.Indicator;
import com.threatconnect.sdk.parser.model.IndicatorType;
import com.threatconnect.sdk.parser.service.bulk.BulkIndicatorConverter;

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
		Assert.assertEquals(IndicatorType.HOST, indicators.get(0).getIndicatorType());
		Assert.assertEquals(IndicatorType.HOST, indicators.get(1).getIndicatorType());
		Assert.assertEquals(IndicatorType.HOST, indicators.get(2).getIndicatorType());
		
		for (Indicator indicator : indicators)
		{
			Host host = (Host) indicator;
			
			// the rating and confidence should be 1
			Assert.assertEquals(1.0, host.getRating().doubleValue(), 0);
			Assert.assertEquals(1.0, host.getConfidence().doubleValue(), 0);
			
			// there should be one tag
			Assert.assertEquals(1, host.getTags().size());
			
			// it should be "Dell SecureWorks"
			Assert.assertEquals("Dell SecureWorks", host.getTags().get(0));
			
			// there should be one attribute
			Assert.assertEquals(1, host.getAttributes().size());
			
			Assert.assertEquals("type", host.getAttributes().get(0).getType());
			Assert.assertEquals("value", host.getAttributes().get(0).getValue());
		}
	}
}
