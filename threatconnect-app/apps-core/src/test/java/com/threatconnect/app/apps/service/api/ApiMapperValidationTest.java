package com.threatconnect.app.apps.service.api;

import com.threatconnect.app.apps.service.api.mapping.ApiRouter;
import org.junit.Assert;
import org.junit.Test;

public class ApiMapperValidationTest
{
	@Test
	public void ambiguousMappingTest()
	{
		try
		{
			new ApiRouter(new AmbiguousApiService());
			Assert.fail("Expected runtime exception");
		}
		catch (RuntimeException e)
		{
			Assert.assertTrue(e.getMessage().startsWith("Ambiguous api mapping detected."));
		}
	}
	
	@Test
	public void pathVariableTypoMappingTest()
	{
		try
		{
			new ApiRouter(new PathVariabeTypoApiService());
			Assert.fail("Expected runtime exception");
		}
		catch (RuntimeException e)
		{
			Assert.assertTrue(e.getMessage().startsWith("Unable to resolve unknown variable"));
		}
	}
}
