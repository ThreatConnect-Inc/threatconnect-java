package com.threatconnect.sdk.app.service.launcher;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.service.message.CommandMessage;
import com.threatconnect.app.apps.service.message.Heartbeat;
import com.threatconnect.sdk.app.exception.AppInitializationException;
import com.threatconnect.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Greg Marut
 */
public abstract class ServiceLauncher<S>
{
	private static final Logger logger = LoggerFactory.getLogger(ServiceLauncher.class);
	
	public static final String PARAM_DB_PATH = "tc_playbook_db_path";
	public static final String PARAM_DB_PORT = "tc_playbook_db_port";
	
	private static final String FIELD_COMMAND = "command";
	
	protected final Gson gson;
	protected final Jedis subscriber;
	protected final Jedis publisher;
	
	private final AppConfig appConfig;
	private final S service;
	private final JsonParser jsonParser;
	private final int heartbeatSeconds;
	private final String serverChannel;
	private final String clientChannel;
	
	private Timer heartbeatTimer;
	
	public ServiceLauncher(final AppConfig appConfig, final S service) throws AppInitializationException
	{
		if (null == appConfig)
		{
			throw new IllegalArgumentException("appConfig cannot be null.");
		}
		
		if (null == service)
		{
			throw new IllegalArgumentException("service cannot be null.");
		}
		
		this.appConfig = appConfig;
		this.service = service;
		
		this.gson = new Gson();
		this.jsonParser = new JsonParser();
		
		//building the redis connection object
		final String host = appConfig.getString(PARAM_DB_PATH);
		final int port = appConfig.getInteger(PARAM_DB_PORT);
		logger.trace("Building Redis connection on {}:{}", host, port);
		subscriber = new Jedis(host, port);
		publisher = new Jedis(host, port);
		
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
	
	public AppConfig getAppConfig()
	{
		return appConfig;
	}
	
	public S getService()
	{
		return service;
	}
	
	public void start()
	{
		logger.trace("Starting service: " + getClass().getName());
		
		//make a blocking call to wait for the result from redis
		logger.trace("Subscribing to channel: " + serverChannel);
		startDaemon(() -> subscriber.subscribe(new JedisHandler(), serverChannel), serverChannel + ".Subscriber");
		
		if (null != heartbeatTimer)
		{
			heartbeatTimer.cancel();
		}
		
		//calculate the number of milliseconds to send the heartbeat
		final long millis = TimeUnit.SECONDS.toMillis(heartbeatSeconds);
		
		//create the new timer
		logger.trace("Scheduling heartbeat with a period of " + millis + " mills");
		heartbeatTimer = new Timer();
		heartbeatTimer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				sendHeartbeatMessage();
			}
		}, 0, millis);
	}
	
	private void startDaemon(Runnable callable, String name)
	{
		Thread t = new Thread(callable);
		t.setName(name);
		t.setDaemon(true);
		t.start();
	}
	
	private void sendHeartbeatMessage()
	{
		Heartbeat heartbeat = new Heartbeat();
		sendMessage(heartbeat);
	}
	
	protected void sendMessage(final CommandMessage message)
	{
		logger.trace("Sending Message: \"" + message.getCommand() + "\", Channel: \"" + clientChannel + "\"");
		publisher.publish(clientChannel, gson.toJson(message));
	}
	
	protected abstract void onMessageReceived(final CommandMessage.Command command, final String message);
	
	/**
	 * Handles the incoming messages from the server
	 */
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
					CommandMessage.Command command = CommandMessage.Command.valueOf(commandValue);
					onMessageReceived(command, message);
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
