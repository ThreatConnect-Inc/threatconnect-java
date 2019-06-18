package com.threatconnect.sdk.app.service.launcher;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.service.api.ApiService;
import com.threatconnect.app.apps.service.api.mapping.ApiNotFoundException;
import com.threatconnect.app.apps.service.api.mapping.ApiRouter;
import com.threatconnect.app.apps.service.message.CommandMessage;
import com.threatconnect.app.apps.service.message.ListServiceAcknowledgeMessage;
import com.threatconnect.app.apps.service.message.ListServices;
import com.threatconnect.app.apps.service.message.RunService;
import com.threatconnect.app.apps.service.message.RunServiceAcknowledgeMessage;
import com.threatconnect.sdk.app.exception.AppInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Base64;

public class ApiServiceLauncher extends ServiceLauncher<ApiService>
{
	private static final Logger logger = LoggerFactory.getLogger(ApiServiceLauncher.class);
	
	private final ApiRouter apiRouter;
	
	public ApiServiceLauncher(final AppConfig appConfig, final ApiService apiService) throws AppInitializationException
	{
		super(appConfig, apiService);
		
		this.apiRouter = new ApiRouter(apiService);
	}
	
	@Override
	protected void onMessageReceived(final CommandMessage.Command command, final String message)
	{
		switch (command)
		{
			case ListServices:
				handleListServices(gson.fromJson(message, ListServices.class));
				break;
			case RunService:
				handleRunServiceEvent(gson.fromJson(message, RunService.class));
				break;
		}
	}
	
	private void handleListServices(final ListServices listServices)
	{
		ListServiceAcknowledgeMessage response = new ListServiceAcknowledgeMessage();
		response.setData(apiRouter.getServiceItems());
		sendMessage(response);
	}
	
	private void handleRunServiceEvent(final RunService runService)
	{
		RunServiceAcknowledgeMessage response = new RunServiceAcknowledgeMessage();
		response.setRequestKey(runService.getRequestKey());
		
		try
		{
			//route this webhook event to the correct method
			Object result = apiRouter.routeApiEvent(runService);
			
			response.setStatus("OK");
			response.setStatusCode(200);
			writeBody(response, result);
		}
		catch (ApiNotFoundException e)
		{
			response.setStatus("Not Found");
			response.setStatusCode(404);
		}
		catch (InvocationTargetException | IllegalAccessException e)
		{
			logger.error(e.getMessage(), e);
			response.setStatus("Error");
			response.setStatusCode(500);
		}
		
		sendMessage(response);
	}
	
	private void writeBody(final RunServiceAcknowledgeMessage runServiceAcknowledgeMessage, final Object object)
	{
		if (null != object)
		{
			if (object instanceof byte[])
			{
				runServiceAcknowledgeMessage.setBody(Base64.getEncoder().encodeToString((byte[]) object));
				runServiceAcknowledgeMessage.setBinary(true);
			}
			else
			{
				runServiceAcknowledgeMessage.setBody(object.toString());
				runServiceAcknowledgeMessage.setBinary(false);
			}
		}
		else
		{
			runServiceAcknowledgeMessage.setBody(null);
			runServiceAcknowledgeMessage.setBinary(false);
		}
	}
}
