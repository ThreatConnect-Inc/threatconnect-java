package com.threatconnect.app.services;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.db.DBService;
import com.threatconnect.app.apps.db.RedisDBService;
import com.threatconnect.app.services.message.AbstractCommandConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Service
{
	private static final Logger logger = LoggerFactory.getLogger(Service.class);
	
	private final List<Runnable> terminationRequestListeners;
	
	public Service()
	{
		this.terminationRequestListeners = new ArrayList<Runnable>();
	}
	
	// holds the reference to the app config object
	private AppConfig appConfig;
	
	public void init(final AppConfig appConfig)
	{
		this.appConfig = appConfig;
	}
	
	public AppConfig getAppConfig()
	{
		return appConfig;
	}
	
	public final void addTerminationRequestListener(final Runnable runnable)
	{
		this.terminationRequestListeners.add(runnable);
	}
	
	protected DBService getDBService(final AbstractCommandConfig commandConfig)
	{
		return new RedisDBService(getAppConfig().getString(AppConfig.PARAM_KVSTORE_HOST),
			getAppConfig().getInteger(AppConfig.PARAM_KVSTORE_PORT), commandConfig.getRequestKey());
	}
	
	/**
	 * Allows the service to request that itself be terminated and shutdown. This is usually in the event of an unrecoverable error
	 */
	protected void requestServiceTermination()
	{
		logger.info("Service requested termination.");
		
		for (Runnable runnable : terminationRequestListeners)
		{
			runnable.run();
		}
	}
	
	public abstract void onStartUp();
	
	public abstract void onShutdown();
	
	public List<String> getDiscoveryTypes()
	{
		return Collections.emptyList();
	}
}
