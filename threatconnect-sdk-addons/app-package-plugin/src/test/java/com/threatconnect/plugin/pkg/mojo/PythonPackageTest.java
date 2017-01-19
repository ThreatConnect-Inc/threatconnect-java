package com.threatconnect.plugin.pkg.mojo;

import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

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
		pythonPackageMojo.execute();
	}
}
