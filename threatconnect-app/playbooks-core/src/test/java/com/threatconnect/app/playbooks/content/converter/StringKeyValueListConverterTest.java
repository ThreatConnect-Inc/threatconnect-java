package com.threatconnect.app.playbooks.content.converter;

import com.threatconnect.app.playbooks.content.entity.StringKeyValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Greg Marut
 */
public class StringKeyValueListConverterTest
{
	@Test
	public void embeddedVariablesTest() throws ConversionException
	{
		final String sample =
			"[{\"key\":\"api_key\",\"value\":\"redacted\"},{\"key\":\"ip\",\"value\": #Trigger:430:trg.tc.address!String}]";
		
		StringKeyValueListConverter converter = new StringKeyValueListConverter();
		List<StringKeyValue> values = converter.fromByteArray(sample.getBytes());
		
		Assert.assertEquals("api_key", values.get(0).getKey());
		Assert.assertEquals("redacted", values.get(0).getValue());
		Assert.assertEquals("ip", values.get(1).getKey());
		Assert.assertEquals("#Trigger:430:trg.tc.address!String", values.get(1).getValue());
	}
}
