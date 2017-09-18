package com.threatconnect.app.addons.util.config.install;

import com.threatconnect.app.addons.util.config.install.serialize.InvalidEnumException;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
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
			InstallUtil.load(file);
			Assert.fail();
		}
		catch (InvalidEnumException e)
		{
			Assert.assertTrue(e.getMessage().contains("NOT A VALID TYPE is not a valid value"));
		}
	}
	
	@Test
	public void fail3() throws IOException, ValidationException
	{
		try
		{
			File file = new File("src/test/resources/fail3.install.json");
			InstallUtil.load(file);
			Assert.fail();
		}
		catch (InvalidEnumException e)
		{
			Assert.assertTrue(e.getMessage().contains("N/A is not a valid value"));
		}
	}
	
	@Test
	public void fail4() throws IOException
	{
		try
		{
			File file = new File("src/test/resources/fail4.install.json");
			InstallUtil.load(file);
			Assert.fail();
		}
		catch (ValidationException e)
		{
			Assert.assertTrue(e.getMessage()
				.contains("Invalid programVersion. Must be in <MAJOR>.<MINOR>.<PATCH> format (e.g. 1.0.0)"));
		}
	}
}
