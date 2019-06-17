package com.threatconnect.sdk.app.service.launcher;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.service.Service;
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

public class DefaultServiceLauncher extends ServiceLauncher
{
	private static final Logger logger = LoggerFactory.getLogger(DefaultServiceLauncher.class);
	
	public static final String PARAM_DB_PATH = "tc_playbook_db_path";
	public static final String PARAM_DB_PORT = "tc_playbook_db_port";
	
	private static final String FIELD_CONFIG_ID = "configId";
	private static final String FIELD_COMMAND = "command";
	private static final String COMMAND_CREATE_CONFIG = "CreateConfig";
	private static final String COMMAND_SHUTDOWN = "Shutdown";
	
	private final Gson gson;
	private final JsonParser jsonParser;
	private final Jedis jedis;
	private final int heartbeatSeconds;
	private final String serverChannel;
	private final String clientChannel;
	private final ContentService contentService;
	
	public DefaultServiceLauncher(final AppConfig appConfig, final Class<? extends Service> serviceClass) throws AppInitializationException
	{
		super(appConfig, serviceClass);
		
		this.gson = new Gson();
		this.jsonParser = new JsonParser();
		
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
	
	private class JedisHandler extends JedisPubSub
	{
		@Override
		public void onMessage(final String channel, final String message)
		{
			//parse the message as a json object
			logger.debug("Message received on channel: " + channel);
			JsonElement jsonMessage = jsonParser.parse(message);
			
			//read and handle the command
			final String command = JsonUtil.getAsString(jsonMessage, FIELD_COMMAND);
			if (null != command)
			{
				switch (command)
				{
					case COMMAND_CREATE_CONFIG:
						
						break;
					case COMMAND_SHUTDOWN:
						//notify the service that we are shutting down
						getService().onShutdown();
						
						// flush the logs to the server
						ServerLogger.getInstance(getAppConfig()).flushToServer();
						
						System.exit(0);
						break;
				}
			}
			else
			{
				logger.trace("Dropping message: Missing command");
			}
		}
	}
}
