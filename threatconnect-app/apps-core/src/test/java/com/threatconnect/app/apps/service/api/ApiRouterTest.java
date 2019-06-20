package com.threatconnect.app.apps.service.api;

import com.threatconnect.app.apps.service.api.mapping.ApiNotFoundException;
import com.threatconnect.app.apps.service.api.mapping.ApiRouter;
import com.threatconnect.app.apps.service.message.RunService;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class ApiRouterTest
{
	@Test
	public void simpleTest() throws IllegalAccessException, InvocationTargetException, ApiNotFoundException
	{
		SimpleApiService simpleApiService = new SimpleApiService();
		ApiRouter apiRouter = new ApiRouter(simpleApiService);
		
		RunService runService = new RunService();
		runService.setMethod("GET");
		runService.setPath("/say/hi");
		
		Object result = apiRouter.routeApiEvent(runService);
		Assert.assertEquals(result, "Hi");
	}
	
	@Test
	public void variableTest1() throws IllegalAccessException, InvocationTargetException, ApiNotFoundException
	{
		SimpleApiService simpleApiService = new SimpleApiService();
		ApiRouter apiRouter = new ApiRouter(simpleApiService);
		
		RunService runService = new RunService();
		runService.setMethod("GET");
		runService.setPath("/say/hi/Greg");
		
		Object result = apiRouter.routeApiEvent(runService);
		Assert.assertEquals(result, "Hi Greg");
	}
	
	@Test
	public void variableTest2() throws IllegalAccessException, InvocationTargetException, ApiNotFoundException
	{
		SimpleApiService simpleApiService = new SimpleApiService();
		ApiRouter apiRouter = new ApiRouter(simpleApiService);
		
		RunService runService = new RunService();
		runService.setMethod("GET");
		runService.setPath("/say/Hello/John/Doe");
		
		Object result = apiRouter.routeApiEvent(runService);
		Assert.assertEquals(result, "Hello John Doe");
	}
}
