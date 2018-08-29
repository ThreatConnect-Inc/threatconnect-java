package com.threatconnect.sdk.app;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.AppExecutor;
import com.threatconnect.app.apps.ExitStatus;
import com.threatconnect.sdk.app.exception.AppInstantiationException;
import com.threatconnect.sdk.app.exception.MultipleAppClassFoundException;
import com.threatconnect.sdk.app.exception.NoAppClassFoundException;
import com.threatconnect.sdk.app.exception.PartialFailureException;
import com.threatconnect.sdk.app.exception.TCMessageException;
import com.threatconnect.sdk.log.ServerLogger;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AppMain implements AppExecutor
{
	private static final Logger logger = LoggerFactory.getLogger(AppMain.class);
	
	private final static AppConfig appConfig;
	
	static
	{
		AppConfig config = new SdkAppConfig();
		
		//check to see if secure params are enabled
		if (config.isTcSecureParamsEnabled())
		{
			//replace the app config with a secure param app config instance
			System.out.println("Using SecureParamAppConfig");
			appConfig = new SecureParamAppConfig();
		}
		else
		{
			System.out.println("Using SdkAppConfig");
			appConfig = config;
		}
	}
	
	protected void execute()
	{
		// holds the most recent exit status from the app
		ExitStatus exitStatus = null;
		
		try
		{
			// set whether or not api logging is enabled
			ServerLogger.getInstance(appConfig).setEnabled(appConfig.isTcLogToApi());
			
			//get the class to execute
			Class<? extends App> appClass = getAppClassToExecute(appConfig);
			
			// execute this app and save the status code
			exitStatus = configureAndExecuteApp(appClass, appConfig);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			LoggerUtil.logErr(e, e.getMessage());
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
			
			// flush the logs to the server
			ServerLogger.getInstance(appConfig).flushToServer();
		}
		
		// exit the app with this exit status
		System.exit(exitStatus.getExitCode());
	}
	
	/**
	 * returns the list of app classes that will be instantiated and executed
	 *
	 * @param appConfig
	 * @return
	 * @throws ClassNotFoundException
	 */
	@Override
	public Class<? extends App> getAppClassToExecute(final AppConfig appConfig) throws ClassNotFoundException
	{
		// check to see if there is an app class specified
		if (null != appConfig && null != appConfig.getTcMainAppClass() && !appConfig.getTcMainAppClass().isEmpty())
		{
			// load the class by name
			Class<?> clazz = Class.forName(appConfig.getTcMainAppClass());
			
			try
			{
				// cast the app class
				@SuppressWarnings("unchecked")
				Class<? extends App> appClass = (Class<? extends App>) clazz;
				
				// add this class to be executed
				return appClass;
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
			//holds the list of app classes that have been found
			final List<Class<? extends App>> appClasses = new ArrayList<Class<? extends App>>();
			
			// find the set of all classes that extend the App class
			Set<Class<? extends App>> subTypes = scanForAppClasses();
			logger.debug("Found {} classes of type {}", appClasses.size(), App.class.getName());
			
			// for each of the classes
			for (Class<? extends App> appClass : subTypes)
			{
				logger.trace("Checking {}", appClass.getName());
				
				// make sure that this is not an abstract class
				if (!Modifier.isAbstract(appClass.getModifiers()))
				{
					appClasses.add(appClass);
				}
				else
				{
					logger.warn("{} is abstract and cannot be executed", appClass.getName());
				}
			}
			
			//make sure only one class was found
			if (appClasses.size() == 1)
			{
				return appClasses.get(0);
			}
			else if (appClasses.size() > 1)
			{
				throw new MultipleAppClassFoundException();
			}
			else
			{
				throw new NoAppClassFoundException();
			}
		}
	}
	
	/**
	 * Instantiates and executes the app given
	 *
	 * @param appClass
	 * @return
	 * @throws Exception
	 */
	protected ExitStatus configureAndExecuteApp(final Class<? extends App> appClass, final AppConfig appConfig)
		throws Exception
	{
		try
		{
			// instantiate a new app class
			App app = appClass.newInstance();
			
			// initialize this app
			app.init(appConfig);
			
			// reconfigure the log file for this app
			LoggerUtil.reconfigureGlobalLogger(app.getAppLogFile(), appConfig);
			
			try
			{
				// execute this app
				return app.execute(appConfig);
			}
			catch (PartialFailureException e)
			{
				app.writeMessageTc(e.getMessage());
				logger.error(e.getMessage(), e);
				LoggerUtil.logErr(e, e.getMessage());
				return ExitStatus.Partial_Failure;
			}
			catch (TCMessageException e)
			{
				app.writeMessageTc(e.getMessage());
				logger.error(e.getMessage(), e);
				LoggerUtil.logErr(e, e.getMessage());
				return ExitStatus.Failure;
			}
		}
		catch (IllegalAccessException | InstantiationException e)
		{
			throw new AppInstantiationException(e, appClass);
		}
	}
	
	protected Set<Class<? extends App>> scanForAppClasses()
	{
		return scanForAppClasses(null);
	}
	
	protected Set<Class<? extends App>> scanForAppClasses(final String basePackage)
	{
		// find the set of all classes that extend the App class
		Reflections reflections = new Reflections(basePackage);
		return reflections.getSubTypesOf(App.class);
	}
	
	public static AppConfig getAppConfig()
	{
		return appConfig;
	}
	
	public static void main(String[] args)
	{
		new AppMain().execute();
	}
}
