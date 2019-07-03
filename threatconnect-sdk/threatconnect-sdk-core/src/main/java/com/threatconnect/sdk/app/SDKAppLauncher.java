package com.threatconnect.sdk.app;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.AppConfigInitializationException;
import com.threatconnect.app.apps.AppLauncher;
import com.threatconnect.app.apps.ParamFileAppConfig;
import com.threatconnect.app.apps.SystemPropertiesAppConfig;
import com.threatconnect.app.apps.UnsupportedAppConfigException;
import com.threatconnect.sdk.app.exception.MultipleAppClassFoundException;
import com.threatconnect.sdk.app.exception.NoAppClassFoundException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class SDKAppLauncher<A> extends AppLauncher<A>
{
	private static final Logger logger = LoggerFactory.getLogger(SDKAppLauncher.class);
	
	private final Class<A> appClass;
	
	public SDKAppLauncher(final Class<A> appClass, final AppConfig appConfig)
	{
		super(appConfig);
		this.appClass = appClass;
	}
	
	public static AppConfig findAppConfig()
	{
		//create a new sdk app config to read the values
		AppConfig appConfig;
		
		try
		{
			appConfig = ParamFileAppConfig.attemptInitialization();
		}
		catch (UnsupportedAppConfigException e)
		{
			//fallback to reading the app config from the cli arguments
			appConfig = new SystemPropertiesAppConfig();
			
			//check to see if secure params are enabled
			if (appConfig.isTcSecureParamsEnabled())
			{
				//replace the app config with a secure param app config instance
				System.out.println("Initializing SecureParams");
				appConfig = new SecureParamAppConfig();
			}
		}
		catch (IOException | GeneralSecurityException e)
		{
			System.err.println("ERROR: Unable to initialize AppConfig.");
			throw new AppConfigInitializationException(e);
		}
		
		return appConfig;
	}
	
	/**
	 * returns the list of app classes that will be instantiated and executed
	 *
	 * @return
	 * @throws ClassNotFoundException
	 */
	@Override
	public Class<? extends A> getAppClassToExecute() throws ClassNotFoundException
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
				Class<? extends A> appClass = (Class<? extends A>) clazz;
				
				// add this class to be executed
				return appClass;
			}
			catch (ClassCastException e)
			{
				String message = "The main class " + clazz.getName() + " does not extend " + appClass + ".";
				throw new ClassCastException(message);
			}
		}
		// scan for all app classes and execute them while the status is successful
		else
		{
			//holds the list of app classes that have been found
			final List<Class<? extends A>> appClasses = new ArrayList<Class<? extends A>>();
			
			// find the set of all classes that extend the App class
			Set<Class<? extends A>> subTypes = scanForAppClasses();
			logger.debug("Found {} classes of type {}", appClasses.size(), appClass.getName());
			
			// for each of the classes
			for (Class<? extends A> appClass : subTypes)
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
	
	private Set<Class<? extends A>> scanForAppClasses()
	{
		// find the set of all classes that extend the App class
		Reflections reflections = new Reflections();
		return reflections.getSubTypesOf(appClass);
	}
}
