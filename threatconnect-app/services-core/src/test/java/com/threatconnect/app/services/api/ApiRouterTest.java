package com.threatconnect.app.services.api;

import com.threatconnect.app.services.api.mapping.ApiNotFoundException;
import com.threatconnect.app.services.api.mapping.ApiRouter;
import com.threatconnect.app.services.message.RunService;
import com.threatconnect.app.services.message.RunServiceAcknowledgedMessage;
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
		
		Object result = apiRouter.routeApiEvent(runService, new RunServiceAcknowledgedMessage());
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
		
		Object result = apiRouter.routeApiEvent(runService, new RunServiceAcknowledgedMessage());
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
		
		Object result = apiRouter.routeApiEvent(runService, new RunServiceAcknowledgedMessage());
		Assert.assertEquals(result, "Hello John Doe");
	}
}
