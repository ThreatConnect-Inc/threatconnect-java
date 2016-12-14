package com.threatconnect.app.playbooks.content.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.gregmarut.support.beangenerator.BeanPropertyGenerator;
import com.threatconnect.app.playbooks.content.entity.TCEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Greg Marut
 */
public class TCEntityConverterTest
{
	private static final Logger logger = LoggerFactory.getLogger(TCEntityConverterTest.class);
	
	private static final String DEFAULT_DATE = "2016-12-12T12:01:00-0500";
	
	private BeanPropertyGenerator beanPropertyGenerator;
	private Date defaultDate;
	
	@Before
	public void init() throws ParseException
	{
		beanPropertyGenerator = new BeanPropertyGenerator();
		
		//create a new date formatter and set a static date to use for all date fields
		DateFormat dateFormat = new SimpleDateFormat(ContentConverter.DATE_FORMAT);
		defaultDate = dateFormat.parse(DEFAULT_DATE);
		beanPropertyGenerator.getConfiguration().getDefaultValues().putStaticValue(Date.class, defaultDate);
	}
	
	@Test
	public void convertTCEntity() throws ConversionException
	{
		final TCEntityConverter entityConverter = new TCEntityConverter();
		
		//create a new TCEntity
		TCEntity tcEntity = beanPropertyGenerator.get(TCEntity.class);
		
		//write the entity to bytes
		byte[] bytes = entityConverter.toByteArray(tcEntity);
		String stringContent = new String(bytes);
		logger.info(stringContent);
		
		//parse the content as json and verify that the dates were saved correctly
		JsonElement jsonElement = new JsonParser().parse(stringContent);
		String dateAdded = jsonElement.getAsJsonObject().get("dateAdded").getAsString();
		String dateLastModified = jsonElement.getAsJsonObject().get("dateLastModified").getAsString();
		
		Assert.assertEquals(DEFAULT_DATE, dateAdded);
		Assert.assertEquals(DEFAULT_DATE, dateLastModified);
	}
}