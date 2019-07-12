package com.threatconnect.sdk.app.service.launcher;

import com.google.gson.Gson;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.services.Service;
import com.threatconnect.app.services.message.CommandMessage;
import com.threatconnect.app.services.message.CommandType;
import com.threatconnect.app.services.message.Heartbeat;
import com.threatconnect.app.services.message.Ready;
import com.threatconnect.sdk.app.exception.AppInitializationException;
import com.threatconnect.sdk.log.ServerLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * @author Greg Marut
 */
public abstract class ServiceLauncher<S extends Service>
{
	private static final Logger logger = LoggerFactory.getLogger(ServiceLauncher.class);
	
	public static final String PARAM_DB_PATH = "tc_playbook_db_path";
	public static final String PARAM_DB_PORT = "tc_playbook_db_port";
	
	protected final Gson gson;
	protected final Jedis subscriber;
	protected final Jedis publisher;
	
	private final AppConfig appConfig;
	private final S service;
	private final String serverChannel;
	private final String clientChannel;
	
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
		
		//building the redis connection object
		final String host = appConfig.getString(PARAM_DB_PATH);
		final int port = appConfig.getInteger(PARAM_DB_PORT);
		logger.trace("Building Redis connection on {}:{}", host, port);
		subscriber = new Jedis(host, port);
		publisher = new Jedis(host, port);
		
		//retrieve the config for
		this.serverChannel = appConfig.getTcServerChannel();
		this.clientChannel = appConfig.getTcClientChannel();
		
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
		Thread thread = new Thread(() -> subscriber.subscribe(new JedisHandler(), serverChannel));
		thread.setName(serverChannel + ".Subscriber");
		thread.setDaemon(false);
		thread.start();
		
		//send a ready message to the server to notify that this service has started
		Ready ready = new Ready();
		sendMessage(ready);
	}
	
	protected void sendMessage(final CommandMessage message)
	{
		logger.trace("Sending Message: \"" + message.getCommand() + "\", Channel: \"" + clientChannel + "\"");
		publisher.publish(clientChannel, gson.toJson(message));
	}
	
	protected void onMessageReceived(final CommandType command, final String message)
	{
		switch (command)
		{
			case Shutdown:
				handleShutdownCommand();
				break;
			case Heartbeat:
				handleHeartbeatCommand();
				break;
		}
	}
	
	private void handleHeartbeatCommand()
	{
		//:TODO: set a timer to detect if core stops sending heartbeat messages
		
		//send a heartbeat message in response to the server's heartbeat
		sendHeartbeatMessage();
	}
	
	private void handleShutdownCommand()
	{
		try
		{
			//notify the service that we are shutting down
			getService().onShutdown();
			
			// flush the logs to the server
			ServerLogger.getInstance(getAppConfig()).flushToServer();
		}
		catch (Exception e)
		{
			System.exit(1);
		}
		finally
		{
			System.exit(0);
		}
	}
	
	private void sendHeartbeatMessage()
	{
		Heartbeat heartbeat = new Heartbeat();
		sendMessage(heartbeat);
	}
	
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
			logger.trace(message);
			
			//read and handle the command
			final CommandType command = CommandMessage.getCommandFromMessage(message);
			if (null != command)
			{
				onMessageReceived(command, message);
			}
			else
			{
				logger.trace("Dropping message: Missing command");
			}
		}
	}
}
