package com.threatconnect.app.addons.util.config.attribute.json;

import com.threatconnect.app.addons.util.config.validation.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Greg Marut
 */
public class AttributeTest
{
	@Test
	public void loadAttribute1() throws IOException, ValidationException
	{
		File file = new File("src/test/resources/attributes1.json");
		Attribute attribute = AttributeUtil.load(file);
		
		Assert.assertEquals(1, attribute.getTypes().size());
		
		AttributeType attributeType = attribute.getTypes().get(0);
		
		Assert.assertEquals("MD5", attributeType.getName());
		Assert.assertEquals("MD5 Hash Code", attributeType.getDescription());
		Assert.assertEquals("Please enter a valid MD5 Hash Code", attributeType.getErrorMessage());
		Assert.assertEquals(32, attributeType.getMaxLength());
		Assert.assertEquals(1, attributeType.getIndicators().size());
		Assert.assertEquals("File", attributeType.getIndicators().get(0));
		Assert.assertFalse(attributeType.isAllowMarkdown());
		Assert.assertTrue(attributeType.isSystem());
	}
}
