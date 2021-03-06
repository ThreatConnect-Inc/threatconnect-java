package com.threatconnect.app.addons.util.config.layout;

import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.install.InstallUtil;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Greg Marut
 */
public class LayoutTest
{
	@Test
	public void loadLayoutJson1() throws IOException, ValidationException
	{
		File installFile = new File("src/test/resources/install.json");
		File layoutFile = new File("src/test/resources/layout.json");
		Install install = InstallUtil.load(installFile);
		Layout layout = LayoutUtil.load(layoutFile, install);
		
		Assert.assertEquals(1, layout.getInputs().get(0).getParameters().size());
		Assert.assertEquals("Step 1", layout.getInputs().get(0).getTitle());
		Assert.assertEquals("Step 2", layout.getInputs().get(1).getTitle());
		Assert.assertEquals("username", layout.getInputs().get(0).getParameters().get(0).getName());
		Assert.assertEquals("password", layout.getInputs().get(1).getParameters().get(0).getName());
	}
	
	@Test
	public void loadLayoutJson2() throws IOException, ValidationException
	{
		File installFile = new File("src/test/resources/install_with_layout.json");
		Install install = InstallUtil.load(installFile);
		Layout layout = install.getLayout();
		
		Assert.assertEquals(1, layout.getInputs().get(0).getParameters().size());
		Assert.assertEquals("Step 1", layout.getInputs().get(0).getTitle());
		Assert.assertEquals("Step 2", layout.getInputs().get(1).getTitle());
		Assert.assertEquals("username", layout.getInputs().get(0).getParameters().get(0).getName());
		Assert.assertEquals("password", layout.getInputs().get(1).getParameters().get(0).getName());
	}
}
