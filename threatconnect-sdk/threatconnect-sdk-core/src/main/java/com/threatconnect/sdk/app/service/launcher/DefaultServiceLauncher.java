package com.threatconnect.sdk.app.service.launcher;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.service.Service;
import com.threatconnect.app.apps.service.ServiceConfiguration;
import com.threatconnect.app.apps.service.message.CommandMessage;
import com.threatconnect.app.apps.service.message.CreateCommandConfig;
import com.threatconnect.app.apps.service.message.DeleteCommandConfig;
import com.threatconnect.app.apps.service.message.NameValuePair;
import com.threatconnect.app.apps.service.message.UpdateCommandConfig;
import com.threatconnect.app.playbooks.app.PlaybooksAppConfig;
import com.threatconnect.app.playbooks.content.ContentService;
import com.threatconnect.app.playbooks.db.RedisDBService;
import com.threatconnect.sdk.app.exception.AppInitializationException;
import com.threatconnect.sdk.log.ServerLogger;
import com.threatconnect.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultServiceLauncher extends ServiceLauncher
{
	private static final Logger logger = LoggerFactory.getLogger(DefaultServiceLauncher.class);
	
	public static final String PARAM_DB_PATH = "tc_playbook_db_path";
	public static final String PARAM_DB_PORT = "tc_playbook_db_port";
	
	private static final String FIELD_COMMAND = "command";
	
	private final Gson gson;
	private final JsonParser jsonParser;
	private final Jedis jedis;
	private final int heartbeatSeconds;
	private final String serverChannel;
	private final String clientChannel;
	private final ContentService contentService;
	
	private final Map<Long, ServiceConfiguration> serviceConfigurations;
	
	public DefaultServiceLauncher(final AppConfig appConfig, final Class<? extends Service> serviceClass) throws AppInitializationException
	{
		super(appConfig, serviceClass);
		
		this.gson = new Gson();
		this.jsonParser = new JsonParser();
		this.serviceConfigurations = new HashMap<Long, ServiceConfiguration>();
		
		//building the redis connection object
		final String host = appConfig.getString(PARAM_DB_PATH);
		final int port = appConfig.getInteger(PARAM_DB_PORT);
		logger.trace("Building Redis connection on {}:{}", host, port);
		jedis = new Jedis(host, port);
		contentService = new ContentService(new RedisDBService(new PlaybooksAppConfig(getAppConfig()), jedis));
		
		//retrieve the config for
		Integer heartbeatSeconds = appConfig.getTcHeartbeatSeconds();
		this.serverChannel = appConfig.getTcServerChannel();
		this.clientChannel = appConfig.getTcClientChannel();
		
		if (null != heartbeatSeconds)
		{
			this.heartbeatSeconds = heartbeatSeconds;
		}
		else
		{
			throw new AppInitializationException("Unable to initialize service: " + AppConfig.TC_HEARTBEAT_SECONDS + " parameter is missing.");
		}
		
		if (null == serverChannel)
		{
			throw new AppInitializationException("Unable to initialize service: " + AppConfig.TC_SERVER_CHANNEL + " parameter is missing.");
		}
		
		if (null == clientChannel)
		{
			throw new AppInitializationException("Unable to initialize service: " + AppConfig.TC_CLIENT_CHANNEL + " parameter is missing.");
		}
	}
	
	@Override
	public void start()
	{
		//make a blocking call to wait for the result from redis
		logger.trace("Subscribing to channel: " + serverChannel);
		jedis.subscribe(new JedisHandler(), serverChannel);
	}
	
	private void handleCreateCommand(final CreateCommandConfig createCommandConfig)
	{
		ServiceConfiguration serviceConfiguration = updateServiceConfiguration(createCommandConfig.getConfigId(), createCommandConfig.getConfig());
		getService().onConfigurationCreated(serviceConfiguration);
	}
	
	private void handleUpdateCommand(final UpdateCommandConfig updateCommandConfig)
	{
		ServiceConfiguration serviceConfiguration = updateServiceConfiguration(updateCommandConfig.getConfigId(), updateCommandConfig.getConfig());
		getService().onConfigurationUpdated(serviceConfiguration);
	}
	
	private void handleDeleteCommand(final DeleteCommandConfig deleteCommandConfig)
	{
		ServiceConfiguration serviceConfiguration = serviceConfigurations.remove(deleteCommandConfig.getConfigId());
		if (null != serviceConfiguration)
		{
			getService().onConfigurationDeleted(serviceConfiguration);
		}
	}
	
	private ServiceConfiguration updateServiceConfiguration(final long configId, final List<NameValuePair<String, String>> params)
	{
		//retrieve the service configuration for the config id. If it does not exists, create it
		ServiceConfiguration serviceConfiguration = serviceConfigurations.computeIfAbsent(configId, id -> {
			ServiceConfiguration sc = new ServiceConfiguration();
			sc.setConfigId(id);
			return sc;
		});
		
		//update the params
		serviceConfiguration.getParams().clear();
		serviceConfiguration.getParams().addAll(params);
		
		return serviceConfiguration;
	}
	
	private class JedisHandler extends JedisPubSub
	{
		@Override
		public void onMessage(final String channel, final String message)
		{
			//parse the message as a json object
			logger.debug("Message received on channel: " + channel);
			JsonElement jsonMessage = jsonParser.parse(message);
			
			//read and handle the command
			final String commandValue = JsonUtil.getAsString(jsonMessage, FIELD_COMMAND);
			if (null != commandValue)
			{
				try
				{
					switch (CommandMessage.Command.valueOf(commandValue))
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
				catch (IllegalArgumentException e)
				{
					logger.warn("Unrecognized command: " + commandValue);
				}
			}
			else
			{
				logger.trace("Dropping message: Missing command");
			}
		}
	}
}
