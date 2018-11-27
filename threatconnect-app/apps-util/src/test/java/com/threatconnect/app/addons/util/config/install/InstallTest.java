package com.threatconnect.app.addons.util.config.install;

import com.threatconnect.app.addons.util.config.Feature;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Greg Marut
 */
public class InstallTest
{
	@Test
	public void loadInstallJson() throws IOException, ValidationException
	{
		File file = new File("src/test/resources/install.json");
		Install install = InstallUtil.load(file);
		
		Assert.assertEquals("Sample App", install.getDisplayName());
		Assert.assertEquals("1.0.0", install.getProgramVersion());
		Assert.assertEquals("com.threatconnect.apps.example.ExampleMain", install.getProgramMain());
		
		Assert.assertEquals(RunLevelType.System, install.getRuntimeLevel());
		
		Assert.assertEquals(2, install.getParams().size());
		Assert.assertEquals("default", install.getParams().get(0).getDefaultValue());
		
		Assert.assertEquals(1, install.getFeeds().size());
		Assert.assertTrue(install.getFeeds().get(0).isEnableBulkJson());
		Assert.assertTrue(install.getFeeds().get(0).getDeprecation().get(0).isPercentage());
		
		Assert.assertEquals(ActionAtMinimumType.Delete.toString(),
			install.getFeeds().get(0).getDeprecation().get(0).getActionAtMinimum());
		Assert.assertEquals(ActionAtMinimumType.Delete.toString(),
			install.getFeeds().get(0).getDeprecation().get(1).getActionAtMinimum());
		Assert.assertEquals(ActionAtMinimumType.Inactive.toString(),
			install.getFeeds().get(0).getDeprecation().get(2).getActionAtMinimum());
		Assert.assertEquals(ActionAtMinimumType.None.toString(),
			install.getFeeds().get(0).getDeprecation().get(3).getActionAtMinimum());
		
		Assert.assertTrue(install.getFeatures().contains(Feature.SECURE_PARAMS));
		Assert.assertEquals(2, install.getLabels().size());
	}
	
	@Test
	public void loadInstall2Json() throws IOException, ValidationException
	{
		File file = new File("src/test/resources/install2.json");
		Install install = InstallUtil.load(file);
		
		Assert.assertEquals("Sample App", install.getDisplayName());
		Assert.assertEquals("1.0.0", install.getProgramVersion());
		Assert.assertEquals("com.threatconnect.apps.example.ExampleMain", install.getProgramMain());
		Assert.assertEquals("|", install.getListDelimiter());
		
		Assert.assertEquals(RunLevelType.System, install.getRuntimeLevel());
		
		Assert.assertEquals(2, install.getParams().size());
		Assert.assertEquals("default", install.getParams().get(0).getDefaultValue());
		Assert.assertEquals(ParamDataType.Choice, install.getParams().get(0).getType());
		Assert.assertEquals(ParamDataType.String, install.getParams().get(1).getType());
		
		Assert.assertEquals(0, install.getFeeds().size());
	}
	
	@Test
	public void loadPlaybookInstallJson() throws IOException, ValidationException
	{
		File file = new File("src/test/resources/playbook.install.json");
		Install install = InstallUtil.load(file);
		
		Assert.assertEquals("Sample Playbook", install.getDisplayName());
		Assert.assertEquals("1.0.0", install.getProgramVersion());
		Assert.assertEquals("com.threatconnect.apps.playbooks.SamplePlaybookMain", install.getProgramMain());
		
		Assert.assertEquals(RunLevelType.Playbook, install.getRuntimeLevel());
		
		Assert.assertEquals(2, install.getParams().size());
		
		Assert.assertEquals(3, install.getPlaybook().getOutputVariables().size());
		Assert.assertEquals("pb.output1", install.getPlaybook().getOutputVariables().get(0).getName());
		Assert.assertEquals("pb.output2", install.getPlaybook().getOutputVariables().get(1).getName());
		Assert.assertEquals("pb.output3", install.getPlaybook().getOutputVariables().get(2).getName());
		
		assertEqualsIgnoreCase(StandardPlaybookType.String.toString(),
			install.getPlaybook().getOutputVariables().get(0).getType());
		assertEqualsIgnoreCase(StandardPlaybookType.String.toString(),
			install.getPlaybook().getOutputVariables().get(1).getType());
		assertEqualsIgnoreCase(StandardPlaybookType.KeyValueArray.toString(),
			install.getPlaybook().getOutputVariables().get(2).getType());
	}
	
	@Test
	public void loadPlaybook2InstallJson() throws IOException, ValidationException
	{
		File file = new File("src/test/resources/playbook2.install.json");
		Install install = InstallUtil.load(file);
		
		Assert.assertEquals("Sample Playbook 2", install.getDisplayName());
		Assert.assertEquals("1.0.0", install.getProgramVersion());
		Assert.assertEquals("com.threatconnect.apps.playbooks.SamplePlaybookMain", install.getProgramMain());
		
		Assert.assertEquals(RunLevelType.Playbook, install.getRuntimeLevel());
		
		Assert.assertEquals(2, install.getParams().size());
		Assert.assertEquals(ParamDataType.String, install.getParams().get(0).getType());
		Assert.assertEquals(ParamDataType.String, install.getParams().get(1).getType());
		
		Assert.assertEquals(3, install.getPlaybook().getOutputVariables().size());
		Assert.assertEquals("pb.output1", install.getPlaybook().getOutputVariables().get(0).getName());
		Assert.assertEquals("pb.output2", install.getPlaybook().getOutputVariables().get(1).getName());
		Assert.assertEquals("pb.output3", install.getPlaybook().getOutputVariables().get(2).getName());
		
		assertEqualsIgnoreCase(StandardPlaybookType.String.toString(),
			install.getPlaybook().getOutputVariables().get(0).getType());
		assertEqualsIgnoreCase(StandardPlaybookType.String.toString(),
			install.getPlaybook().getOutputVariables().get(1).getType());
		assertEqualsIgnoreCase(StandardPlaybookType.KeyValueArray.toString(),
			install.getPlaybook().getOutputVariables().get(2).getType());
	}
	
	public void assertEqualsIgnoreCase(final String expected, final String actual)
	{
		Assert.assertTrue(expected.equalsIgnoreCase(actual));
	}
}
