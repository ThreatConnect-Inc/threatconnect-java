package com.threatconnect.app.addons.util.config.attribute.csv;

import com.threatconnect.app.addons.util.config.InvalidCsvFileException;
import com.threatconnect.app.addons.util.config.attribute.csv.AttributeType;
import com.threatconnect.app.addons.util.config.attribute.csv.AttributeTypeReaderUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Greg Marut
 */
public class AttributeTypeTest
{
	@Test
	public void loadAttribute1() throws IOException, InvalidCsvFileException
	{
		File file = new File("src/test/resources/attributes1.csv");
		List<AttributeType> attributeTypes = AttributeTypeReaderUtil.read(file);
		
		Assert.assertEquals(1, attributeTypes.size());
		Assert.assertEquals("Name", attributeTypes.get(0).getName());
		Assert.assertEquals("Description", attributeTypes.get(0).getDescription());
		Assert.assertEquals("Error", attributeTypes.get(0).getErrorMessage());
		Assert.assertEquals(50, attributeTypes.get(0).getMaxSize());
		Assert.assertEquals(2, attributeTypes.get(0).getTypes().size());
		Assert.assertEquals("Url", attributeTypes.get(0).getTypes().get(0));
		Assert.assertEquals("Address", attributeTypes.get(0).getTypes().get(1));
		Assert.assertFalse(attributeTypes.get(0).isAllowMarkdown());
	}
	
	@Test
	public void loadAttribute2() throws IOException, InvalidCsvFileException
	{
		File file = new File("src/test/resources/attributes2.csv");
		List<AttributeType> attributeTypes = AttributeTypeReaderUtil.read(file);
		
		Assert.assertEquals(2, attributeTypes.size());
		Assert.assertEquals("Name1", attributeTypes.get(0).getName());
		Assert.assertEquals("Name2", attributeTypes.get(1).getName());
		Assert.assertEquals("Description", attributeTypes.get(0).getDescription());
		Assert.assertEquals("Error", attributeTypes.get(0).getErrorMessage());
		Assert.assertEquals(50, attributeTypes.get(0).getMaxSize());
		Assert.assertEquals(2, attributeTypes.get(0).getTypes().size());
		Assert.assertEquals("Url", attributeTypes.get(0).getTypes().get(0));
		Assert.assertEquals("Address", attributeTypes.get(0).getTypes().get(1));
		Assert.assertTrue(attributeTypes.get(0).isAllowMarkdown());
	}
}
