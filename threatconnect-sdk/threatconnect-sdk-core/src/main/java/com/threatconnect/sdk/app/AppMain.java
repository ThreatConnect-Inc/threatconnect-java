package com.threatconnect.sdk.app;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.AppExecutor;
import com.threatconnect.app.apps.ExitStatus;
import com.threatconnect.app.apps.SystemPropertiesAppConfig;
import com.threatconnect.sdk.app.aot.AOTHandler;
import com.threatconnect.sdk.app.aot.AOTListener;
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
import java.util.function.Consumer;
import java.util.function.Function;

public final class AppMain extends AppExecutor
{
	private static final Logger logger = LoggerFactory.getLogger(AppMain.class);
	
	public AppMain()
	{
		super(findAppConfig());
	}
	
	public AppMain(final AppConfig appConfig)
	{
		super(appConfig);
	}
	
	@Override
	public int execute()
	{
		logger.trace("AppMain execute()");
		
		// holds the most recent exit status from the app
		ExitStatus exitStatus = null;
		
		try
		{
			// set whether or not api logging is enabled
			ServerLogger.getInstance(getAppConfig()).setEnabled(getAppConfig().isTcLogToApi());
			
			//get the class to execute
			logger.trace("Retrieving app class to execute");
			Class<? extends App> appClass = getAppClassToExecute();
			
			// execute this app and save the status code
			exitStatus = configureAndExecuteApp(appClass, getAppConfig());
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
			ServerLogger.getInstance(getAppConfig()).flushToServer();
		}
		
		return exitStatus.getExitCode();
	}
	
	/**
	 * Executes the AOT logic to wait for further instructions
	 */
	private void executeAOT()
	{
		//create a new AOT Handler
		new AOTHandler(getAppConfig(), new AOTListener()
		{
			@Override
			public void execute(final AOTHandler aotHandler)
			{
				executeAndExit(AppMain.this, aotHandler::sendExitCode);
			}
			
			@Override
			public void terminate(final boolean timeout)
			{
				if (timeout)
				{
					// exit the app with this exit status
					logger.warn("AOT timeout. App terminating without execution.");
					System.exit(1);
				}
				else
				{
					System.exit(0);
				}
			}
		});
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
		if (null != getAppConfig() && null != getAppConfig().getTcMainAppClass() && !getAppConfig().getTcMainAppClass()
			.isEmpty())
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
	
	/**
	 * Instantiates and executes the app given
	 *
	 * @param appClass
	 * @return
	 * @throws Exception
	 */
	private ExitStatus configureAndExecuteApp(final Class<? extends App> appClass, final AppConfig appConfig)
		throws Exception
	{
		try
		{
			// instantiate a new app class
			logger.trace("Instantiating app class: " + appClass.getName());
			App app = appClass.newInstance();
			
			// initialize this app
			app.init(appConfig);
			
			// reconfigure the log file for this app
			logger.trace("Reconfiguring global logger for app");
			LoggerUtil.reconfigureGlobalLogger(app.getAppLogFile(), appConfig);
			
			try
			{
				// execute this app
				logger.trace("Executing app: " + appClass.getName());
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
	
	private Set<Class<? extends App>> scanForAppClasses()
	{
		return scanForAppClasses(null);
	}
	
	private Set<Class<? extends App>> scanForAppClasses(final String basePackage)
	{
		// find the set of all classes that extend the App class
		Reflections reflections = new Reflections(basePackage);
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
			appConfig = new SecureParamAppConfig();
		}
		
		return appConfig;
	}
	
	private static void executeAndExit(final AppMain appMain)
	{
		executeAndExit(appMain, null);
	}
	
	private static void executeAndExit(final AppMain appMain, final Consumer<Integer> afterExecute)
	{
		//execute the app and return the exit status
		int exitCode = appMain.execute();
		
		//make sure the after execute consumer is not null
		if (null != afterExecute)
		{
			afterExecute.accept(exitCode);
		}
		
		// exit the app with this exit status
		System.exit(exitCode);
	}
	
	public static void main(String[] args)
	{
		//retrieve the app config object
		AppConfig appConfig = findAppConfig();
		AppMain appMain = new AppMain(appConfig);
		
		//check to see if AOT is enabled and that the channel is set
		if (appConfig.isAOTEnabled())
		{
			//make sure the action channel is set
			if (null != appConfig.getTcActionChannel())
			{
				//run the aot logic
				appMain.executeAOT();
			}
			else
			{
				//need to use system.err over logger since logger is not yet configured
				System.err.println("Unable to launch app as AOT. Missing required parameter: " + AppConfig.TC_ACTION_CHANNEL);
			}
		}
		else
		{
			executeAndExit(appMain);
		}
	}
}
