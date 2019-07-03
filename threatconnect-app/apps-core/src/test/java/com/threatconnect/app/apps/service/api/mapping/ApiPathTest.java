package com.threatconnect.app.apps.service.api.mapping;

import org.junit.Assert;
import org.junit.Test;

public class ApiPathTest
{
	@Test
	public void normalizeTest1()
	{
		Assert.assertEquals("/say/hi", ApiPath.normalize("/say/hi"));
	}
	
	@Test
	public void normalizeTest2()
	{
		Assert.assertEquals("/say/hi", ApiPath.normalize("say/hi"));
	}
	
	@Test
	public void normalizeTest3()
	{
		Assert.assertEquals("/say/hi", ApiPath.normalize("/say/hi/"));
	}
	
	@Test
	public void normalizeTest4()
	{
		Assert.assertEquals("/say/hi", ApiPath.normalize("say/hi/"));
	}
	
	@Test
	public void staticTest1()
	{
		ApiPath apiPath = new ApiPath("/say/hi");
		Assert.assertTrue(apiPath.matches("/say/hi"));
	}
	
	@Test
	public void variableTest1()
	{
		ApiPath apiPath = new ApiPath("/say/hi/{name}");
		Assert.assertTrue(apiPath.matches("/say/hi/Greg"));
	}
}
