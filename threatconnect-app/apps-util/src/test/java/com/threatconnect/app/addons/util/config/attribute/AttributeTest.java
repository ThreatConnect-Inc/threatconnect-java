package com.threatconnect.app.addons.util.config.attribute;

import com.threatconnect.app.addons.util.config.InvalidCsvLineException;
import com.threatconnect.app.addons.util.config.install.validation.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Greg Marut
 */
public class AttributeTest
{
	@Test
	public void loadAttribute1() throws IOException, ValidationException, InvalidCsvLineException
	{
		File file = new File("src/test/resources/attributes1.csv");
		List<Attribute> attributes = AttributeReaderUtil.read(file);
		
		Assert.assertEquals(1, attributes.size());
		Assert.assertEquals("Name", attributes.get(0).getName());
		Assert.assertEquals("Description", attributes.get(0).getDescription());
		Assert.assertEquals("Error", attributes.get(0).getErrorMessage());
		Assert.assertEquals(50, attributes.get(0).getMaxSize());
		Assert.assertEquals(2, attributes.get(0).getTypes().size());
		Assert.assertEquals("Url", attributes.get(0).getTypes().get(0));
		Assert.assertEquals("Address", attributes.get(0).getTypes().get(1));
		Assert.assertFalse(attributes.get(0).isAllowMarkdown());
	}
	
	@Test
	public void loadAttribute2() throws IOException, ValidationException, InvalidCsvLineException
	{
		File file = new File("src/test/resources/attributes2.csv");
		List<Attribute> attributes = AttributeReaderUtil.read(file);
		
		Assert.assertEquals(2, attributes.size());
		Assert.assertEquals("Name1", attributes.get(0).getName());
		Assert.assertEquals("Name2", attributes.get(1).getName());
		Assert.assertEquals("Description", attributes.get(0).getDescription());
		Assert.assertEquals("Error", attributes.get(0).getErrorMessage());
		Assert.assertEquals(50, attributes.get(0).getMaxSize());
		Assert.assertEquals(2, attributes.get(0).getTypes().size());
		Assert.assertEquals("Url", attributes.get(0).getTypes().get(0));
		Assert.assertEquals("Address", attributes.get(0).getTypes().get(1));
		Assert.assertTrue(attributes.get(0).isAllowMarkdown());
	}
}
