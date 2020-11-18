package com.threatconnect.app.playbooks.content.converter;

import com.threatconnect.app.execution.converter.ConversionException;
import com.threatconnect.app.execution.converter.KeyValueListConverter;
import com.threatconnect.app.execution.entity.KeyValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Greg Marut
 */
public class KeyValueListConverterTest
{
	@Test
	public void embeddedVariablesTest() throws ConversionException
	{
		final String sample =
			"[{\"key\":\"api_key\",\"value\":\"redacted\"},{\"key\":\"ip\",\"value\": #Trigger:430:trg.tc.address!String}]";
		
		KeyValueListConverter converter = new KeyValueListConverter();
		List<KeyValue> values = converter.fromByteArray(sample.getBytes());
		
		Assert.assertEquals("api_key", values.get(0).getKey());
		Assert.assertEquals("redacted", values.get(0).getValue());
		Assert.assertEquals("ip", values.get(1).getKey());
		Assert.assertEquals("#Trigger:430:trg.tc.address!String", values.get(1).getValue());
	}
}
