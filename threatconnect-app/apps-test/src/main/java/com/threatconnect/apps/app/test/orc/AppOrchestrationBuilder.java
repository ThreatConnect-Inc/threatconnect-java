package com.threatconnect.apps.app.test.orc;

import com.threatconnect.app.apps.App;
import com.threatconnect.apps.app.test.config.AppConfiguration;
import com.threatconnect.apps.app.test.config.AppTestConfiguration;

/**
 * @author Greg Marut
 */
public final class AppOrchestrationBuilder<A extends App>
{
	private AppOrchestrationBuilder()
	{
		
	}
	
	public AppOrchestration<A> createAppOrchestration(final Class<? extends A> appClass)
	{
		try
		{
			return createAppOrchestration(appClass.newInstance());
		}
		catch (IllegalAccessException | InstantiationException e)
		{
			throw new AppOrchestrationRuntimeException(e);
		}
	}
	
	public AppOrchestration<A> createAppOrchestration(final A app)
	{
		//look up the configuration for this
		AppConfiguration appConfiguration =
			AppTestConfiguration.getInstance().getConfigurationMap().get(app.getClass());
		
		//make sure the playbooks app is not null
		if (null != appConfiguration)
		{
			//create the new app orchestration
			return new AppOrchestration<A>(appConfiguration, app, this);
		}
		else
		{
			throw new IllegalArgumentException(app.getClass().getName() + " was not detected by the " +
				AppTestConfiguration.class.getSimpleName()
				+ ". Please make sure there is a valid install.json file configured for this app.");
		}
	}
	
	/**
	 * Given an appOrchestration, A new AppRunner is built using the root appOrchestration in the hierarchy
	 *
	 * @param appOrchestration
	 * @return AppRunner
	 */
	public AppRunner<A> build(final AppOrchestration<A> appOrchestration)
	{
		//holds the current app orchestration object
		AppOrchestration<A> currentAppOrchestration = appOrchestration;
		
		//while the current app orchestration has a parent
		while (null != appOrchestration.getParent())
		{
			//traverse up the tree until we find the root app orchestration object
			currentAppOrchestration = appOrchestration.getParent();
		}
		
		return new AppRunner<A>(currentAppOrchestration);
	}
	
	public static AppOrchestration<App> runApp(final Class<? extends App> appClass)
	{
		return new AppOrchestrationBuilder<App>().createAppOrchestration(appClass);
	}
	
	public static AppOrchestration<App> runApp(final App app)
	{
		return new AppOrchestrationBuilder<App>().createAppOrchestration(app);
	}
}
