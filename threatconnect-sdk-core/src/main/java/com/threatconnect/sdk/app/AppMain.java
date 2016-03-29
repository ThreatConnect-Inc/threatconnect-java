package com.threatconnect.sdk.app;

import java.lang.reflect.Modifier;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.threatconnect.sdk.app.exception.AppInstantiationException;

public final class AppMain
{
	private static final Logger logger = LoggerFactory.getLogger(AppMain.class);
	
	public static void main(String[] args)
	{
		// holds the most recent exit status from the app
		ExitStatus exitStatus = null;
		
		try
		{
			// create the app config object
			AppConfig appConfig = AppConfig.getInstance();
			
			// check to see if there is an app class specified
			if (null != appConfig.getTcMainAppClass() && !appConfig.getTcMainAppClass().isEmpty())
			{
				// load the class by name
				Class<?> clazz = Class.forName(appConfig.getTcMainAppClass());
				
				try
				{
					// cast the app class
					@SuppressWarnings("unchecked")
					Class<? extends App> appClass = (Class<? extends App>) clazz;
					
					// execute this app
					exitStatus = configureAndExecuteApp(appClass, appConfig);
				}
				catch (ClassCastException e)
				{
					String message = "The main class " + clazz.getName() + " does not extend " + App.class + ".";
					throw new ClassCastException(message);
				}
			}
			// scan for all app classes and execute them while the status is successful
			else
			{
				// find the set of all classes that extend the App class
				Set<Class<? extends App>> subTypes = scanForAppClasses();
				
				// for each of the classes
				for (Class<? extends App> appClass : subTypes)
				{
					// make sure that this is not an abstract class
					if (!Modifier.isAbstract(appClass.getModifiers()))
					{
						// execute this app
						exitStatus = configureAndExecuteApp(appClass, appConfig);
						
						// check to see if this app was not successful
						if (exitStatus != ExitStatus.Success)
						{
							// stop executing the apps
							return;
						}
					}
				}
			}
		}
		catch (AppInstantiationException e)
		{
			logger.error(e.getMessage(), e);
			LoggerUtil.logErr(e.getMessage());
			exitStatus = ExitStatus.Failure;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			LoggerUtil.logErr(e.getMessage());
			exitStatus = ExitStatus.Failure;
		}
		finally
		{
			// ensure that the exit status is not null. This should not normally happen
			if (null == exitStatus)
			{
				LoggerUtil.logErr("Exit status is null.");
				exitStatus = ExitStatus.Failure;
			}
			
			// exit the app with this exit status
			System.exit(exitStatus.getExitCode());
		}
	}
	
	/**
	 * Instantiates and executes the app given
	 * 
	 * @param appClass
	 * @return
	 * @throws Exception
	 */
	private static ExitStatus configureAndExecuteApp(final Class<? extends App> appClass, final AppConfig appConfig)
		throws Exception
	{
		try
		{
			// instantiate a new app class
			App app = appClass.newInstance();
			
			// add the app config for this app
			app.setAppConfig(appConfig);
			
			// reconfigure the log file for this app
			LoggerUtil.reconfigureGlobalLogger(app.getAppLogFile(), appConfig);
			
			// execute this app
			return app.execute(appConfig);
		}
		catch (IllegalAccessException | InstantiationException e)
		{
			throw new AppInstantiationException(e, appClass);
		}
	}
	
	private static Set<Class<? extends App>> scanForAppClasses()
	{
		return scanForAppClasses(null);
	}
	
	private static Set<Class<? extends App>> scanForAppClasses(final String basePackage)
	{
		// find the set of all classes that extend the App class
		Reflections reflections = new Reflections(basePackage);
		Set<Class<? extends App>> subTypes = reflections.getSubTypesOf(App.class);
		
		return subTypes;
	}
}
