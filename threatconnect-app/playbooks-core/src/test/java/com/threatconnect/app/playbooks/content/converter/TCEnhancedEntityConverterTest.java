package com.threatconnect.app.playbooks.content.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.gregmarut.support.beangenerator.BeanPropertyGenerator;
import com.threatconnect.sdk.model.Address;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.ItemType;
import com.threatconnect.sdk.model.util.JsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * @author Greg Marut
 */
public class TCEnhancedEntityConverterTest
{
	private static final Logger logger = LoggerFactory.getLogger(TCEnhancedEntityConverterTest.class);
	
	private static final String DEFAULT_DATE = "2016-12-08T20:56:11Z";
	
	private BeanPropertyGenerator beanPropertyGenerator;
	
	@Before
	public void init() throws ParseException
	{
		beanPropertyGenerator = new BeanPropertyGenerator();
		
		//create a new date formatter and set a static date to use for all date fields
		Date defaultDate = ContentConverter.DEFAULT_DATE_FORMATTER.parse(DEFAULT_DATE);
		beanPropertyGenerator.getConfiguration().getDefaultValues().putStaticValue(Date.class, defaultDate);
	}
	
	@Test
	public void convertTCEnhancedEntity() throws ConversionException
	{
		final TCEnhancedEntityConverter entityConverter = new TCEnhancedEntityConverter();
		
		//create a new TCEntity
		Address indicator = beanPropertyGenerator.get(Address.class);
		
		//write the entity to bytes
		byte[] bytes = entityConverter.toByteArray(indicator);
		String stringContent = new String(bytes);
		logger.info(stringContent);
		
		//parse the content as json and verify that the dates were saved correctly
		JsonElement jsonElement = new JsonParser().parse(stringContent);
		JsonArray indicatorArray = JsonUtil.getAsJsonArray(jsonElement, "indicator");
		String summary = indicatorArray.get(0).getAsJsonObject().get("summary").getAsString();
		
		Assert.assertEquals(indicator.toString(), summary);
		
		Item resolvedItem = entityConverter.fromByteArray(bytes);
		Assert.assertEquals(ItemType.INDICATOR, resolvedItem.getItemType());
		Indicator resolvedIndicator = (Indicator) resolvedItem;
		Assert.assertEquals(indicator.getIndicatorType(), resolvedIndicator.getIndicatorType());
	}
}