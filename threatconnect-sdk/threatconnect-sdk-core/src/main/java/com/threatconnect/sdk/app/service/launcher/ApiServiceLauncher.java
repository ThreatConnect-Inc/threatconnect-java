package com.threatconnect.sdk.app.service.launcher;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.db.DBServiceFactory;
import com.threatconnect.app.playbooks.db.DBWriteException;
import com.threatconnect.app.services.api.ApiService;
import com.threatconnect.app.services.api.mapping.ApiNotFoundException;
import com.threatconnect.app.services.api.mapping.ApiRouter;
import com.threatconnect.app.services.message.CommandType;
import com.threatconnect.app.services.message.ListServiceAcknowledgeMessage;
import com.threatconnect.app.services.message.ListServices;
import com.threatconnect.app.services.message.RunService;
import com.threatconnect.app.services.message.RunServiceAcknowledgeMessage;
import com.threatconnect.sdk.app.exception.AppInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class ApiServiceLauncher extends ServiceLauncher<ApiService>
{
	private static final Logger logger = LoggerFactory.getLogger(ApiServiceLauncher.class);
	
	private final ApiRouter apiRouter;
	private final DBService dbService;
	
	public ApiServiceLauncher(final AppConfig appConfig, final ApiService apiService) throws AppInitializationException
	{
		super(appConfig, apiService);
		
		this.apiRouter = new ApiRouter(apiService);
		this.dbService = DBServiceFactory.buildFromAppConfig(appConfig);
	}
	
	@Override
	protected void onMessageReceived(final CommandType command, final String message)
	{
		switch (command)
		{
			case ListServices:
				handleListServices(gson.fromJson(message, ListServices.class));
				break;
			case RunService:
				handleRunServiceEvent(gson.fromJson(message, RunService.class));
				break;
			default:
				super.onMessageReceived(command, message);
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
			Object result = apiRouter.routeApiEvent(runService, response);
			
			response.setStatus("OK");
			response.setStatusCode(200);
			writeBody(response, result);
		}
		catch (ApiNotFoundException e)
		{
			response.setStatus("Not Found");
			response.setStatusCode(404);
		}
		catch (InvocationTargetException | IllegalAccessException | DBWriteException e)
		{
			logger.error(e.getMessage(), e);
			response.setStatus("Error");
			response.setStatusCode(500);
		}
		
		sendMessage(response);
	}
	
	private void writeBody(final RunServiceAcknowledgeMessage runServiceAcknowledgeMessage, final Object object) throws DBWriteException
	{
		if (null != object)
		{
			final String key = "response.body";
			runServiceAcknowledgeMessage.setBodyVariable(key);
			
			if (object instanceof byte[])
			{
				dbService.saveValue(key, (byte[]) object);
			}
			else
			{
				dbService.saveValue(key, object.toString().getBytes());
			}
		}
	}
}
