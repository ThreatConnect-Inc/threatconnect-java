package com.threatconnect.app.addons.util.config.install;

import com.threatconnect.app.addons.util.config.install.serialize.InvalidEnumException;
import com.threatconnect.app.addons.util.config.install.validation.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Greg Marut
 */
public class FailInstallTest
{
	@Test
	public void fail1() throws IOException, ValidationException
	{
		try
		{
			File file = new File("src/test/resources/fail1.install.json");
			Install install = InstallUtil.load(file);
		}
		catch (InvalidEnumException e)
		{
			Assert.assertTrue(e.getMessage().contains("NOT A VALID TYPE is not a valid value"));
		}
	}
	
	@Test
	public void fail2() throws IOException, ValidationException
	{
		try
		{
			File file = new File("src/test/resources/fail2.install.json");
			Install install = InstallUtil.load(file);
		}
		catch (ValidationException e)
		{
			Assert.assertTrue(e.getMessage().contains("No type is defined for parameter username"));
		}
	}
	
	@Test
	public void fail3() throws IOException, ValidationException
	{
		try
		{
			File file = new File("src/test/resources/fail3.install.json");
			Install install = InstallUtil.load(file);
		}
		catch (InvalidEnumException e)
		{
			Assert.assertTrue(e.getMessage().contains("N/A is not a valid value"));
		}
	}
}
