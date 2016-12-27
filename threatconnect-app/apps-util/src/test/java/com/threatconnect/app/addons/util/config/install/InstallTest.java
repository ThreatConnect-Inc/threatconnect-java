package com.threatconnect.app.addons.util.config.install;

import com.google.gson.Gson;
import com.threatconnect.app.addons.util.config.install.type.PlaybookVariableType;
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
	
	@Test
	public void loadPlaybookInstallJson() throws IOException
	{
		File file = new File("src/test/resources/playbook.install.json");
		
		try (InputStream is = new FileInputStream(file))
		{
			Install install = new Gson().fromJson(new InputStreamReader(is), Install.class);
			
			Assert.assertEquals("Sample Playbook", install.getDisplayName());
			Assert.assertEquals("1.0.0", install.getProgramVersion());
			Assert.assertEquals("com.threatconnect.apps.playbooks.SamplePlaybookMain", install.getProgramMain());
			
			Assert.assertEquals(2, install.getParams().size());
			
			Assert.assertEquals(3, install.getPlaybook().getOutputVariables().size());
			Assert.assertEquals("pb.output1", install.getPlaybook().getOutputVariables().get(0).getName());
			Assert.assertEquals("pb.output2", install.getPlaybook().getOutputVariables().get(1).getName());
			Assert.assertEquals("pb.output3", install.getPlaybook().getOutputVariables().get(2).getName());
			
			Assert
				.assertEquals(PlaybookVariableType.String, install.getPlaybook().getOutputVariables().get(0).getType());
			Assert
				.assertEquals(PlaybookVariableType.String, install.getPlaybook().getOutputVariables().get(1).getType());
			Assert.assertEquals(PlaybookVariableType.KeyValueArray,
				install.getPlaybook().getOutputVariables().get(2).getType());
		}
	}
}
