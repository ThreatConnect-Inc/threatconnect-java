package com.threatconnect.app.addons.util.config.install;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Greg Marut
 */
public class InstallTest
{
	@Test
	public void loadInstallJson() throws IOException
	{
		File file = new File("src/test/resources/install.json");
		
		try (InputStream is = new FileInputStream(file))
		{
			Install install = new Gson().fromJson(new InputStreamReader(is), Install.class);
			
			Assert.assertEquals("Sample App", install.getDisplayName());
			Assert.assertEquals("1.0.0", install.getProgramVersion());
			Assert.assertEquals("com.threatconnect.apps.example.ExampleMain", install.getProgramMain());
			
			Assert.assertEquals(2, install.getParams().size());
			Assert.assertEquals(1, install.getFeeds().size());
		}
	}
}
