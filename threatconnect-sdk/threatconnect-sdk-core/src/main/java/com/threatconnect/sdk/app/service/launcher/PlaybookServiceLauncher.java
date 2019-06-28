package com.threatconnect.sdk.app.service.launcher;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.service.message.CommandMessage;
import com.threatconnect.app.apps.service.message.CreateCommandConfig;
import com.threatconnect.app.apps.service.message.DeleteCommandConfig;
import com.threatconnect.app.apps.service.message.FireEvent;
import com.threatconnect.app.apps.service.message.NameValuePair;
import com.threatconnect.app.apps.service.message.UpdateCommandConfig;
import com.threatconnect.app.playbooks.app.PlaybooksAppConfig;
import com.threatconnect.app.playbooks.app.service.FireEventListener;
import com.threatconnect.app.playbooks.app.service.PlaybookService;
import com.threatconnect.app.playbooks.app.service.ServiceConfiguration;
import com.threatconnect.app.playbooks.content.ContentService;
import com.threatconnect.app.playbooks.db.RedisDBService;
import com.threatconnect.sdk.app.exception.AppInitializationException;
import com.threatconnect.sdk.log.ServerLogger;

import java.util.List;
import java.util.UUID;

public class PlaybookServiceLauncher<S extends PlaybookService> extends ServiceLauncher<S>
{
	private final ContentService contentService;
	
	public PlaybookServiceLauncher(final AppConfig appConfig, final S service) throws AppInitializationException
	{
		super(appConfig, service);
		
		service.setFireEventListener(createFireEventListener());
		contentService = new ContentService(new RedisDBService(new PlaybooksAppConfig(getAppConfig()), subscriber));
	}
	
	protected FireEventListener createFireEventListener()
	{
		return serviceConfiguration -> {
			FireEvent fireEvent = new FireEvent();
			fireEvent.setSessionId(UUID.randomUUID().toString());
			fireEvent.setConfigId(serviceConfiguration.getConfigId());
			sendMessage(fireEvent);
		};
	}
	
	private void handleCreateCommand(final CreateCommandConfig createCommandConfig)
	{
		ServiceConfiguration serviceConfiguration = getServiceConfiguration(createCommandConfig.getConfigId(), createCommandConfig.getConfig());
		getService().createServiceConfiguration(serviceConfiguration);
	}
	
	private void handleUpdateCommand(final UpdateCommandConfig updateCommandConfig)
	{
		ServiceConfiguration serviceConfiguration = getServiceConfiguration(updateCommandConfig.getConfigId(), updateCommandConfig.getConfig());
		getService().updateServiceConfiguration(serviceConfiguration);
	}
	
	private void handleDeleteCommand(final DeleteCommandConfig deleteCommandConfig)
	{
		getService().deleteServiceConfiguration(deleteCommandConfig.getConfigId());
	}
	
	private ServiceConfiguration getServiceConfiguration(final long configId, final List<NameValuePair<String, String>> params)
	{
		//retrieve the service configuration for the config id. If it does not exists, create it
		ServiceConfiguration serviceConfiguration = getService().getServiceConfigurations().getOrDefault(configId, new ServiceConfiguration());
		serviceConfiguration.setConfigId(configId);
		
		//update the params
		serviceConfiguration.getParams().clear();
		serviceConfiguration.getParams().addAll(params);
		
		return serviceConfiguration;
	}
	
	@Override
	protected void onMessageReceived(final CommandMessage.Command command, final String message)
	{
		switch (command)
		{
			case CreateConfig:
				handleCreateCommand(gson.fromJson(message, CreateCommandConfig.class));
				break;
			case UpdateConfig:
				handleUpdateCommand(gson.fromJson(message, UpdateCommandConfig.class));
				break;
			case DeleteConfig:
				handleDeleteCommand(gson.fromJson(message, DeleteCommandConfig.class));
				break;
			case Shutdown:
				//notify the service that we are shutting down
				getService().onShutdown();
				
				// flush the logs to the server
				ServerLogger.getInstance(getAppConfig()).flushToServer();
				
				System.exit(0);
				break;
		}
	}
}
