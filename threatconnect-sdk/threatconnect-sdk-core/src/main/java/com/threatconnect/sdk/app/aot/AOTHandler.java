package com.threatconnect.sdk.app.aot;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.threatconnect.app.apps.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Timer;
import java.util.TimerTask;

public class AOTHandler
{
	private static final Logger logger = LoggerFactory.getLogger(AOTHandler.class);
	
	public static final String PARAM_DB_PATH = "tc_playbook_db_path";
	public static final String PARAM_DB_PORT = "tc_playbook_db_port";
	
	private static final String MESSAGE_TYPE_EXECUTE = "execute";
	private static final String MESSAGE_TYPE_TERMINATE = "terminate";
	
	private static final int DEFAULT_AOT_TIMEOUT_SECONDS = 60;
	
	public AOTHandler(final AppConfig appConfig, final AOTListener aotListener)
	{
		Gson gson = new Gson();
		
		//building the redis connection object
		final String host = appConfig.getString(PARAM_DB_PATH);
		final int port = appConfig.getInteger(PARAM_DB_PORT);
		logger.trace("Building RedisDBService on {}:{}", host, port);
		Jedis jedis = new Jedis(host, port);
		
		//retrieve the timeout
		final int timeoutSeconds = appConfig.getInteger(AppConfig.TC_TERMINATE_SECONDS, DEFAULT_AOT_TIMEOUT_SECONDS);
		final long timeout = timeoutSeconds * 1000L;
		
		//initialize the timer to schedule a timeout termination of this app
		final Timer shutdownTimer = new Timer();
		shutdownTimer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				aotListener.terminate(true);
			}
		}, timeout);
		
		//subscribe to the action channel
		jedis.subscribe(new JedisPubSub()
		{
			@Override
			public void onMessage(final String channel, final String message)
			{
				try
				{
					//deserialize the message
					AOTMessage aotMessage = gson.fromJson(message, AOTMessage.class);
					
					//determine what type of message this is
					switch (aotMessage.getType())
					{
						case MESSAGE_TYPE_EXECUTE:
							//cancel the timeout timer
							shutdownTimer.cancel();
							
							//tell the listener that an execute instruction was received
							aotListener.execute();
							break;
						case MESSAGE_TYPE_TERMINATE:
							//tell the listener that a terminate instruction was received
							aotListener.terminate(false);
							break;
						default:
							logger.warn("Unknown message received: {}", message);
							break;
					}
				}
				catch (JsonSyntaxException e)
				{
					logger.warn("Unknown message received: {}", message);
				}
			}
		}, appConfig.getActionChannel());
	}
}
