package com.threatconnect.sdk.app;

import com.threatconnect.sdk.app.exception.AppInstantiationException;
import com.threatconnect.sdk.app.exception.TCMessageException;
import com.threatconnect.sdk.log.ServerLogger;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AppMain
{
	private static final Logger logger = LoggerFactory.getLogger(AppMain.class);

	protected void execute()
	{
		// holds the most recent exit status from the app
		ExitStatus exitStatus = null;

		try
		{
			// create the app config object
			AppConfig appConfig = AppConfig.getInstance();

			// set whether or not api logging is enabled
			ServerLogger.getInstance().setEnabled(appConfig.isTcLogToApi());

			//get the list of classes to execute
			List<Class<? extends App>> appClasses = getAppClassesToExecute(appConfig);

			//make sure the list of app classes is not null
			if (null != appClasses)
			{
				//for each of the app classes
				for (Class<? extends App> appClass : appClasses)
				{
					// execute this app
					exitStatus = configureAndExecuteApp(appClass, appConfig);

					// check to see if this app was not successful
					if (exitStatus != ExitStatus.Success)
					{
						// stop executing the apps
						// exit the app with this exit status
						System.exit(exitStatus.getExitCode());
					}
				}
			}
			else
			{
				logger.error("No Apps were found to execute.");
			}
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
			ServerLogger.getInstance().flushToServer();
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
	protected List<Class<? extends App>> getAppClassesToExecute(final AppConfig appConfig) throws ClassNotFoundException
	{
		//holds the list of classes to execute
		final List<Class<? extends App>> appClasses = new ArrayList<Class<? extends App>>();

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

				// add this class to be executed
				appClasses.add(appClass);
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
					appClasses.add(appClass);
				}
				else
				{
					logger.error("{} is abstract and cannot be executed", appClass.getName());
				}
			}
		}

		return appClasses;
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

			try
			{
				// execute this app
				return app.execute(appConfig);
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

	public static void main(String[] args)
	{
		new AppMain().execute();
	}
}
