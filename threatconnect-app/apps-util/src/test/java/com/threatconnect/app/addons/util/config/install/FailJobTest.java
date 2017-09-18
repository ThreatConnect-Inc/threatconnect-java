package com.threatconnect.app.addons.util.config.install;

import com.threatconnect.app.addons.util.config.validation.JobValidator;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Greg Marut
 */
public class FailJobTest
{
	@Test
	public void fail1() throws IOException, ValidationException
	{
		try
		{
			File file = new File("src/test/resources/fail.job.json");
			JobUtil.load(file, true, new JobValidator(false));
			Assert.fail();
		}
		catch (ValidationException e)
		{
			Assert.assertEquals("Parameter \"api_default_org\" may not contain a ThreatConnect variable.", e.getMessage());
		}
	}
	
	public void assertEqualsIgnoreCase(final String expected, final String actual)
	{
		Assert.assertTrue(expected.equalsIgnoreCase(actual));
	}
}
