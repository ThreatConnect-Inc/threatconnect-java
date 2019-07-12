package com.threatconnect.sdk.app.service.launcher;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.playbooks.app.PlaybooksAppConfig;
import com.threatconnect.app.playbooks.content.ContentService;
import com.threatconnect.app.playbooks.db.RedisDBService;
import com.threatconnect.app.services.message.CommandType;
import com.threatconnect.app.services.message.CreateCommandConfig;
import com.threatconnect.app.services.message.DeleteCommandConfig;
import com.threatconnect.app.services.message.FireEvent;
import com.threatconnect.app.services.message.UpdateCommandConfig;
import com.threatconnect.playbooks.service.FireEventListener;
import com.threatconnect.playbooks.service.PlaybookService;
import com.threatconnect.playbooks.service.ServiceConfiguration;
import com.threatconnect.sdk.app.exception.AppInitializationException;

import java.util.Map;
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
			fireEvent.setTriggerId(serviceConfiguration.getConfigId());
			sendMessage(fireEvent);
		};
	}
	
	private void handleCreateCommand(final CreateCommandConfig createCommandConfig)
	{
		ServiceConfiguration serviceConfiguration = getServiceConfiguration(createCommandConfig.getTriggerId(), createCommandConfig.getConfig());
		getService().createServiceConfiguration(serviceConfiguration);
	}
	
	private void handleUpdateCommand(final UpdateCommandConfig updateCommandConfig)
	{
		ServiceConfiguration serviceConfiguration = getServiceConfiguration(updateCommandConfig.getTriggerId(), updateCommandConfig.getConfig());
		getService().updateServiceConfiguration(serviceConfiguration);
	}
	
	private void handleDeleteCommand(final DeleteCommandConfig deleteCommandConfig)
	{
		getService().deleteServiceConfiguration(deleteCommandConfig.getTriggerId());
	}
	
	private ServiceConfiguration getServiceConfiguration(final long configId, final Map<String, Object> config)
	{
		//retrieve the service configuration for the config id. If it does not exists, create it
		ServiceConfiguration serviceConfiguration = getService().getServiceConfigurations().getOrDefault(
			configId, new ServiceConfiguration(configId));
		
		//update the params
		serviceConfiguration.getConfig().clear();
		serviceConfiguration.getConfig().putAll(config);
		
		return serviceConfiguration;
	}
	
	@Override
	protected void onMessageReceived(final CommandType command, final String message)
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
			default:
				super.onMessageReceived(command, message);
		}
	}
}
