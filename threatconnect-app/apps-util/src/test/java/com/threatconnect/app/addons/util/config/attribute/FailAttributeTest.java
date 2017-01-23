package com.threatconnect.app.addons.util.config.attribute;

import com.threatconnect.app.addons.util.config.InvalidCsvLineException;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Greg Marut
 */
public class FailAttributeTest
{
	@Test
	public void failAttribute1() throws IOException, InvalidCsvLineException
	{
		try
		{
			File file = new File("src/test/resources/fail.attributes1.csv");
			AttributeReaderUtil.read(file);
		}
		catch (ValidationException e)
		{
			Assert.assertTrue(e.getMessage().contains("Attribute name cannot be empty."));
		}
	}
	
	@Test
	public void failAttribute2() throws IOException, InvalidCsvLineException
	{
		try
		{
			File file = new File("src/test/resources/fail.attributes2.csv");
			AttributeReaderUtil.read(file);
		}
		catch (ValidationException e)
		{
			Assert.assertTrue(e.getMessage().contains("Attribute types contains an empty value."));
		}
	}
}
