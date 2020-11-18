package com.threatconnect.sdk.app.executor;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.ExitStatus;
import com.threatconnect.app.playbooks.app.PlaybooksApp;
import com.threatconnect.app.playbooks.app.PlaybooksAppConfig;
import com.threatconnect.app.playbooks.content.ContentService;
import com.threatconnect.app.playbooks.db.RedisDBService;
import com.threatconnect.sdk.app.aot.AOTMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

public class AOTAppExecutor extends DefaultAppExecutor
{
	private static final Logger logger = LoggerFactory.getLogger(AOTAppExecutor.class);
	
	public static final String PARAM_DB_PATH = "tc_playbook_db_path";
	public static final String PARAM_DB_PORT = "tc_playbook_db_port";
	
	private static final String MESSAGE_TYPE_EXECUTE = "execute";
	private static final String MESSAGE_TYPE_TERMINATE = "terminate";
	
	private static final int DEFAULT_AOT_TIMEOUT_SECONDS = 60;
	
	private final Gson gson;
	private final Jedis jedis;
	private final int timeoutSeconds;
	private final ContentService contentService;
	
	public AOTAppExecutor(final AppConfig appConfig, final Class<? extends App> appClass)
	{
		super(appConfig, appClass);
		
		this.gson = new Gson();
		
		//building the redis connection object
		final String host = appConfig.getString(PARAM_DB_PATH);
		final int port = appConfig.getInteger(PARAM_DB_PORT);
		logger.trace("Building Redis connection on {}:{}", host, port);
		jedis = new Jedis(host, port);
		contentService = new ContentService(new RedisDBService(new PlaybooksAppConfig(getAppConfig()), jedis));
		
		//retrieve the timeout
		timeoutSeconds = appConfig.getInteger(AppConfig.TC_TERMINATE_SECONDS, DEFAULT_AOT_TIMEOUT_SECONDS);
	}
	
	@Override
	public int execute()
	{
		//make a blocking call to wait for the result from redis
		logger.trace("Waiting for message on channel: " + getAppConfig().getTcActionChannel());
		List<String> results = jedis.blpop(timeoutSeconds, getAppConfig().getTcActionChannel());
		
		//check to see if we got a valid response (no timeout)
		if (null != results && results.size() == 2)
		{
			logger.trace("Message received on channel: " + getAppConfig().getTcActionChannel());
			
			final String key = results.get(0);
			final String value = results.get(1);
			
			//handle the message and then terminate this app
			return handleMessage(value);
		}
		else
		{
			logger.warn("AOT timeout. App terminating without execution.");
			return ExitStatus.Failure.getExitCode();
		}
	}
	
	/**
	 * Handles the message and returns whether or not it was successful
	 *
	 * @param message
	 * @return
	 */
	private int handleMessage(final String message)
	{
		try
		{
			//make sure the message is not null
			if (null != message)
			{
				//deserialize the message
				logger.trace("Deserializing json...");
				logger.trace(message);
				AOTMessage aotMessage = gson.fromJson(message, AOTMessage.class);
				
				//make sure the type is not null
				if (null != aotMessage.getType())
				{
					//determine what type of message this is
					switch (aotMessage.getType())
					{
						case MESSAGE_TYPE_EXECUTE:
							//tell the listener that an execute instruction was received
							logger.trace("Execute Message Received");
							return runApp(aotMessage.getParams());
						case MESSAGE_TYPE_TERMINATE:
							//tell the listener that a terminate instruction was received
							logger.trace("Terminate Message Received");
							return ExitStatus.Success.getExitCode();
						default:
							logger.warn("Unknown message received: {}", message);
							return ExitStatus.Failure.getExitCode();
					}
				}
				else
				{
					logger.warn("Message was missing required field \"type\"");
					return ExitStatus.Failure.getExitCode();
				}
			}
			else
			{
				logger.warn("Null message received.");
				return ExitStatus.Failure.getExitCode();
			}
		}
		catch (JsonSyntaxException e)
		{
			logger.warn("Unknown message received: {}", message);
			return ExitStatus.Failure.getExitCode();
		}
	}
	
	/**
	 * Executes the app with the given map of parameters
	 *
	 * @param parameters parameters to add to the app config
	 * @return the exit code for the running app
	 */
	private int runApp(final Map<String, String> parameters)
	{
		//check to see if the parameters map is not null
		if (null != parameters)
		{
			//set all of the parameters on this app config
			logger.trace("Loading parameters from AOT source");
			getAppConfig().setAll(parameters);
		}
		
		//actually run the app now
		final int exitCode = super.execute();
		
		//send the exit code to redis
		sendExitCode(exitCode);
		
		return exitCode;
	}
	
	/**
	 * Pushes the exit code to the exit channel
	 *
	 * @param exitCode
	 */
	private void sendExitCode(final int exitCode)
	{
		logger.trace("Sending AOT Exit Code: " + exitCode);
		jedis.rpush(getAppConfig().getTcExitChannel(), Integer.toString(exitCode));
	}
	
	@Override
	protected void initializeApp(final App app)
	{
		//check to see if this app is a playbook
		if (app instanceof PlaybooksApp)
		{
			// initialize this playbook
			logger.trace("Initializing PlaybooksApp: " + getAppClass().getName());
			PlaybooksApp playbooksApp = (PlaybooksApp) app;
			playbooksApp.init(getAppConfig(), contentService);
			logger.trace("Initialization Complete");
		}
		else
		{
			super.initializeApp(app);
		}
	}
}
