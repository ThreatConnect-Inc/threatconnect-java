package com.threatconnect.sdk.app;

import com.threatconnect.sdk.app.exception.NoAppClassFoundException;
import com.threatconnect.sdk.log.ServerLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MultiAppMain extends AppMain
{
	private static final Logger logger = LoggerFactory.getLogger(MultiAppMain.class);
	
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
			List<Class<? extends App>> appClasses = getAppClassesToExecute();
			
			//for each of the app classes
			for (Class<? extends App> appClass : appClasses)
			{
				// execute this app
				exitStatus = configureAndExecuteApp(appClass, appConfig);
				
				// check to see if this app was not successful
				if (exitStatus != ExitStatus.Success)
				{
					return;
				}
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
	 * @return
	 * @throws ClassNotFoundException
	 */
	public List<Class<? extends App>> getAppClassesToExecute() throws ClassNotFoundException
	{
		//holds the list of classes to execute
		final List<Class<? extends App>> appClasses = new ArrayList<Class<? extends App>>();
		
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
		
		//make sure atleast 1 app was found
		if (appClasses.size() == 0)
		{
			throw new NoAppClassFoundException();
		}
		
		return appClasses;
	}
	
	public static void main(String[] args)
	{
		new MultiAppMain().execute();
	}
}
