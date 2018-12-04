package com.threatconnect.app.addons.util.config.layout;

import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.install.InstallUtil;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FailLayoutTest
{
	@Test
	public void fail1() throws IOException, ValidationException
	{
		try
		{
			File installFile = new File("src/test/resources/install.json");
			File layoutFile = new File("src/test/resources/fail1.layout.json");
			Install install = InstallUtil.load(installFile);
			LayoutUtil.load(layoutFile, install);
			Assert.fail();
		}
		catch (ValidationException e)
		{
			Assert.assertTrue(e.getMessage().contains("parameter name \"unknown\" is not valid."));
		}
	}
	
	@Test
	public void fail2() throws IOException, ValidationException
	{
		try
		{
			File installFile = new File("src/test/resources/install.json");
			File layoutFile = new File("src/test/resources/fail2.layout.json");
			Install install = InstallUtil.load(installFile);
			LayoutUtil.load(layoutFile, install);
			Assert.fail();
		}
		catch (ValidationException e)
		{
			Assert.assertTrue(e.getMessage().contains("title is not defined."));
		}
	}
}
