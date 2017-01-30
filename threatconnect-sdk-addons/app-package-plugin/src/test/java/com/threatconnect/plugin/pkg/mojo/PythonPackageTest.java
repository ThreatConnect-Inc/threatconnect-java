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
public class PythonPackageTest
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
	public void pythonPackageTest() throws Exception
	{
		File pom = new File("src/test/resources/package/python/pom.xml");
		Assert.assertNotNull(pom);
		Assert.assertTrue(pom.exists());
		
		PythonPackageMojo pythonPackageMojo = (PythonPackageMojo) rule.lookupMojo("python-package", pom);
		Assert.assertNotNull(pythonPackageMojo);
		
		clean(new File(pythonPackageMojo.getOutputDirectory()));
		
		pythonPackageMojo.execute();
		
		assertExcludes();
	}
	
	private void assertExcludes()
	{
		File root = new File("target/package/PythonPackageTest_v1.0");
		Assert.assertTrue(root.exists());
		Assert.assertTrue(root.isDirectory());
		
		File pom = new File(root.getPath() + "/pom.xml");
		Assert.assertFalse(pom.exists());
		
		File customExclude = new File(root.getPath() + "/custom.exclude");
		Assert.assertFalse(customExclude.exists());
	}
	
	private void clean(final File outputDirectory) throws IOException
	{
		//add protection against deleting any directory not in the target directory
		Assert.assertTrue("outputDirectory must start be located in the \"target\" folder",
			outputDirectory.getPath().startsWith("target" + File.separator));
		FileUtils.deleteDirectory(outputDirectory);
	}
}
