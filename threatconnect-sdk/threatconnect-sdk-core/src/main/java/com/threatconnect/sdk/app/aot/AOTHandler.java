package com.threatconnect.sdk.app.aot;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.threatconnect.app.apps.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;

public class AOTHandler
{
	private static final Logger logger = LoggerFactory.getLogger(AOTHandler.class);
	
	public static final String PARAM_DB_PATH = "tc_playbook_db_path";
	public static final String PARAM_DB_PORT = "tc_playbook_db_port";
	
	private static final String MESSAGE_TYPE_EXECUTE = "execute";
	private static final String MESSAGE_TYPE_TERMINATE = "terminate";
	
	private static final int DEFAULT_AOT_TIMEOUT_SECONDS = 60;
	
	private final AppConfig appConfig;
	private final AOTListener aotListener;
	private final Gson gson;
	private final Jedis jedis;
	
	public AOTHandler(final AppConfig appConfig, final AOTListener aotListener)
	{
		this.appConfig = appConfig;
		this.aotListener = aotListener;
		this.gson = new Gson();
		
		//building the redis connection object
		final String host = appConfig.getString(PARAM_DB_PATH);
		final int port = appConfig.getInteger(PARAM_DB_PORT);
		logger.trace("Building RedisDBService on {}:{}", host, port);
		jedis = new Jedis(host, port);
		
		//retrieve the timeout
		final int timeoutSeconds = appConfig.getInteger(AppConfig.TC_TERMINATE_SECONDS, DEFAULT_AOT_TIMEOUT_SECONDS);
		
		//make a blocking call to wait for the result from redis
		List<String> results = jedis.blpop(timeoutSeconds, appConfig.getTcActionChannel());
		
		//check to see if we got a valid response (no timeout)
		if (null != results && results.size() == 2)
		{
			final String key = results.get(0);
			final String value = results.get(1);
			
			//handle the message and then terminate this app
			handleMessage(value);
			aotListener.terminate(false);
		}
		else
		{
			aotListener.terminate(true);
		}
	}
	
	/**
	 * Pushes the exit code to the exit channel
	 *
	 * @param exitCode
	 */
	public void sendExitCode(final int exitCode)
	{
		jedis.rpush(appConfig.getTcExitChannel(), Integer.toString(exitCode));
	}
	
	/**
	 * Handles the message and returns whether or not it was successful
	 *
	 * @param message
	 * @return
	 */
	private boolean handleMessage(final String message)
	{
		try
		{
			//make sure the message is not null
			if (null != message)
			{
				//deserialize the message
				AOTMessage aotMessage = gson.fromJson(message, AOTMessage.class);
				
				//make sure the type is not null
				if (null != aotMessage.getType())
				{
					//determine what type of message this is
					switch (aotMessage.getType())
					{
						case MESSAGE_TYPE_EXECUTE:
							//tell the listener that an execute instruction was received
							aotListener.execute(this);
							return true;
						case MESSAGE_TYPE_TERMINATE:
							//tell the listener that a terminate instruction was received
							aotListener.terminate(false);
							return true;
						default:
							logger.warn("Unknown message received: {}", message);
							return false;
					}
				}
				else
				{
					logger.warn("Message was missing required field \"type\"");
					return false;
				}
			}
			else
			{
				logger.warn("Null message received.");
				return false;
			}
		}
		catch (JsonSyntaxException e)
		{
			logger.warn("Unknown message received: {}", message);
			return false;
		}
	}
}
