package com.threatconnect.plugin.pkg.mojo;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Greg Marut
 */
public class FeedPackageTest
{
	@Rule
	public MojoRule rule = new MojoRule()
	{
		@Override
		protected void before() throws Throwable
		{
		}
		
		@Override
		protected void after()
		{
		}
	};
	
	@Test
	public void thirdPartyPackageTest() throws Exception
	{
		File pom = new File("src/test/resources/package/feed/pom.xml");
		Assert.assertNotNull(pom);
		Assert.assertTrue(pom.exists());
		
		FeedPackageMojo feedPackageMojo = (FeedPackageMojo) rule.lookupMojo("feed-package", pom);
		Assert.assertNotNull(feedPackageMojo);
		
		clean(new File(feedPackageMojo.getOutputDirectory()));
		
		feedPackageMojo.execute();
		
		assertExcludes();
	}
	
	private void assertExcludes()
	{
		File root = new File("target/package/FeedPackageTest_v1.0");
		Assert.assertTrue(root.exists());
		Assert.assertTrue(root.isDirectory());
		
		File feedJsonFile = new File(root.getPath() + "/feed.json");
		Assert.assertTrue(feedJsonFile.exists());
	}
	
	private void clean(final File outputDirectory) throws IOException
	{
		//add protection against deleting any directory not in the target directory
		Assert.assertTrue("outputDirectory must start be located in the \"target\" folder",
			outputDirectory.getPath().startsWith("target" + File.separator));
		FileUtils.deleteDirectory(outputDirectory);
	}
}
