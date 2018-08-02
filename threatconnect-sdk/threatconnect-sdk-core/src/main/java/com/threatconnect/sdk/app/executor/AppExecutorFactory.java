package com.threatconnect.sdk.app.executor;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.app.exception.AppInitializationException;

public class AppExecutorFactory
{
	/**
	 * Creates an AppExecutor object based on the supplied app config and app class
	 * @param appConfig
	 * @param appClass
	 * @return
	 * @throws AppInitializationException
	 */
	public static AppExecutor create(final AppConfig appConfig, final Class<? extends App> appClass) throws AppInitializationException
	{
		//check to see if AOT is enabled and that the channel is set
		if (appConfig.isAOTEnabled())
		{
			//make sure the action channel is set
			if (null != appConfig.getTcActionChannel())
			{
				//this app is to execute in AOT mode
				return new AOTAppExecutor(appConfig, appClass);
			}
			else
			{
				throw new AppInitializationException("Unable to launch app as AOT. Missing required parameter: " + AppConfig.TC_ACTION_CHANNEL);
			}
		}
		else
		{
			//this app is to execute in standard mode
			return new DefaultAppExecutor(appConfig, appClass);
		}
	}
}
