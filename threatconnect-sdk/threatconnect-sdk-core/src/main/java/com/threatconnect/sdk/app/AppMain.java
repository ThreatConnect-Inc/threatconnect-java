package com.threatconnect.sdk.app;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.AppLauncher;
import com.threatconnect.app.apps.SystemPropertiesAppConfig;
import com.threatconnect.sdk.app.exception.AppInitializationException;
import com.threatconnect.sdk.app.exception.MultipleAppClassFoundException;
import com.threatconnect.sdk.app.exception.NoAppClassFoundException;
import com.threatconnect.sdk.app.executor.AppExecutor;
import com.threatconnect.sdk.app.executor.AppExecutorFactory;
import com.threatconnect.sdk.log.ServerLogger;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class AppMain extends AppLauncher
{
	private static final Logger logger = LoggerFactory.getLogger(AppMain.class);
	
	public AppMain()
	{
		this(findAppConfig());
	}
	
	public AppMain(final AppConfig appConfig)
	{
		super(appConfig);
	}
	
	@Override
	public void launch() throws ClassNotFoundException, IOException, AppInitializationException
	{
		logger.info("Retrieving class to execute...");
		final Class<? extends App> appClass = getAppClassToExecute();
		
		// reconfigure the log file for this app
		logger.info("Configuring App Logging...");
		File logFile = new File(getAppConfig().getTcLogPath() + File.separator + appClass.getSimpleName() + ".log");
		LoggerUtil.reconfigureGlobalLogger(logFile, getAppConfig());
		
		// set whether or not api logging is enabled
		ServerLogger.getInstance(getAppConfig()).setEnabled(getAppConfig().isTcLogToApi());
		
		//holds the resulting exit code from the app execution
		final int exitCode;
		
		try
		{
			//retrieve the type of execution that this app should use
			AppExecutor appExecutor = AppExecutorFactory.create(getAppConfig(), appClass);
			
			//execute the app and retrieve the exit code
			exitCode = appExecutor.execute();
		}
		finally
		{
			// flush the logs to the server
			ServerLogger.getInstance(getAppConfig()).flushToServer();
		}
		
		// exit the app with this exit status
		System.exit(exitCode);
	}
	
	/**
	 * returns the list of app classes that will be instantiated and executed
	 *
	 * @return
	 * @throws ClassNotFoundException
	 */
	@Override
	public Class<? extends App> getAppClassToExecute() throws ClassNotFoundException
	{
		// check to see if there is an app class specified
		if (null != getAppConfig() && null != getAppConfig().getTcMainAppClass() && !getAppConfig().getTcMainAppClass().isEmpty())
		{
			// load the class by name
			Class<?> clazz = Class.forName(getAppConfig().getTcMainAppClass());
			
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
	
	private Set<Class<? extends App>> scanForAppClasses()
	{
		// find the set of all classes that extend the App class
		Reflections reflections = new Reflections();
		return reflections.getSubTypesOf(App.class);
	}
	
	private static AppConfig findAppConfig()
	{
		//create a new sdk app config to read the values
		AppConfig appConfig = new SystemPropertiesAppConfig();
		
		//check to see if secure params are enabled
		if (appConfig.isTcSecureParamsEnabled())
		{
			//replace the app config with a secure param app config instance
			System.out.println("Initializing SecureParams");
			appConfig = new SecureParamAppConfig();
		}
		
		return appConfig;
	}
	
	public static void main(String[] args) throws Exception
	{
		new AppMain().launch();
	}
}
