package com.threatconnect.apps.app.test.config;

import com.threatconnect.app.addons.util.config.install.InstallJson;
import com.threatconnect.app.addons.util.config.install.InvalidInstallJsonFileException;
import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.AppExecutor;
import com.threatconnect.app.apps.DefaultAppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public class AppTestConfiguration
{
	public static final Pattern PATTERN_INSTALL_JSON = Pattern.compile("^(?:(.*)\\.)?install\\.json$");
	
	private static final Logger logger = LoggerFactory.getLogger(AppTestConfiguration.class);
	
	//holds the instance to this singleton
	private static AppTestConfiguration instance;
	private static final Object lock = new Object();
	
	//holds the playbook configuration map
	private final Map<Class<? extends App>, AppConfiguration> configurationMap;
	
	//holds the default app config object
	private final AppConfig defaultAppConfig;
	
	private AppTestConfiguration()
	{
		this.configurationMap = new HashMap<Class<? extends App>, AppConfiguration>();
		this.defaultAppConfig = new DefaultAppConfig();
		
		logger.info("Loading Playbooks Test Configuration");
		
		//find all of the install.json files
		List<File> files = findInstallJsonFiles();
		
		logger.info("Found {} install.json files", files.size());
		
		//for each of the files
		for (File file : files)
		{
			loadFileAndConfigure(file);
		}
	}
	
	public void setGlobalAppParam(final String name, final String value)
	{
		defaultAppConfig.set(name, value);
	}
	
	private List<File> findInstallJsonFiles()
	{
		//holds the list of files to return
		List<File> files = new ArrayList<File>();
		
		// retrieve the base directory file
		final File root = new File("./");
		
		// for each file in the root
		for (File file : root.listFiles())
		{
			// make sure this is a file (not a directory)
			if (file.isFile())
			{
				// retrieve the matcher for this filename
				Matcher matcher = PATTERN_INSTALL_JSON.matcher(file.getName());
				
				// make sure this is an install file
				if (matcher.matches())
				{
					files.add(file);
				}
			}
		}
		
		return files;
	}
	
	public void loadFileAndConfigure(final File file)
	{
		try
		{
			logger.info("Loading {}", file.getAbsolutePath());
			
			//read the json file
			InstallJson installJson = new InstallJson(file);
			
			//configure the app for this install.json file
			configureApp(installJson);
		}
		catch (UnsupposedAppMainClassException | InvalidInstallJsonFileException | InvalidAppException e)
		{
			throw new AppConfigurationException(e);
		}
	}
	
	/**
	 * Reads an install.json file and configures the app according to the config
	 *
	 * @param installJson
	 * @throws InvalidInstallJsonFileException
	 * @throws ClassNotFoundException
	 * @throws UnsupposedAppMainClassException
	 * @throws InvalidAppException
	 */
	private void configureApp(final InstallJson installJson)
		throws InvalidInstallJsonFileException, UnsupposedAppMainClassException, InvalidAppException
	{
		try
		{
			//get the program main class
			Class<? extends AppExecutor> programMainClass =
				(Class<? extends AppExecutor>) Class.forName(installJson.getProgramMain());
			
			//instantiate this class
			AppExecutor appExecutor = programMainClass.newInstance();
			
			//retrieve the classes that are executed from this main
			AppConfig appConfig = new DefaultAppConfig().copyFrom(defaultAppConfig);
			Class<? extends App> appClass = appExecutor.getAppClassToExecute(appConfig);
			
			//ensure that this class has a declared static main method
			Method method = appExecutor.getClass().getDeclaredMethod("main", String[].class);
			if (!Modifier.isStatic(method.getModifiers()))
			{
				throw new UnsupposedAppMainClassException(
					installJson.getProgramMain() + " must have static main method.");
			}
			
			//configure this app
			configureApp(appClass, installJson);
		}
		catch (ClassNotFoundException e)
		{
			throw new UnsupposedAppMainClassException(
				installJson.getProgramMain() + " could not be found.");
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new UnsupposedAppMainClassException(
				installJson.getProgramMain() + " must have a public no-arg constructor.");
		}
		catch (ClassCastException e)
		{
			throw new UnsupposedAppMainClassException(installJson.getProgramMain() +
				" is not supported. Only classes that implement " + AppExecutor.class.getName()
				+ " are currently supported.");
		}
		catch (NoSuchMethodException e)
		{
			throw new UnsupposedAppMainClassException(
				installJson.getProgramMain() + " must have static main method.");
		}
	}
	
	private void configureApp(final Class<? extends App> appClass, final InstallJson installJson)
		throws InvalidAppException
	{
		logger.info("Configuring app \"{}\", loaded from file \"{}\"", appClass.getName(),
			installJson.getInstallJsonFile().getAbsolutePath());
		
		//make sure this app is an app
		if (App.class.isAssignableFrom(appClass))
		{
			//make sure this configuration does not already exist
			if (!configurationMap.containsKey(appClass))
			{
				//create a new playbook configuration class
				AppConfiguration appConfiguration = new AppConfiguration(appClass, installJson);
				
				//add this config to the map
				configurationMap.put(appClass, appConfiguration);
			}
			else
			{
				//warn that this class was already loaded
				logger.warn(appClass.getName() + " already configured. Skipping configuration.");
			}
		}
		else
		{
			throw new InvalidAppException(
				appClass.getName() + ", loaded from " + installJson.getInstallJsonFile().getAbsolutePath()
					+ ", must extend from " + App.class.getName());
		}
	}
	
	/**
	 * Creates a builder object for dynamically registering a playbook app config without a json file
	 *
	 * @param appClass
	 * @return
	 */
	public AppConfigurationBuilder createAppConfigurationBuilder(final Class<? extends App> appClass)
	{
		return new AppConfigurationBuilder(appClass, this);
	}
	
	/**
	 * Adds a playbook configuration to the map and overrides any existing configuration
	 *
	 * @param appConfiguration
	 */
	void registerDynamicPlaybookConfiguration(final AppConfiguration appConfiguration)
	{
		logger
			.info("Registering dynamic AppConfiguration for \"{}\"", appConfiguration.getAppClass().getName());
		
		//check to see if a playbooks configuration already exists for this class
		if (configurationMap.containsKey(appConfiguration.getAppClass()))
		{
			//notify the user via a warning
			logger
				.warn("Overriding existing configuration for \"{}\"", appConfiguration.getAppClass().getName());
		}
		
		//add this config to the map
		configurationMap.put(appConfiguration.getAppClass(), appConfiguration);
	}
	
	public Map<Class<? extends App>, AppConfiguration> getConfigurationMap()
	{
		return new HashMap<Class<? extends App>, AppConfiguration>(configurationMap);
	}
	
	public AppConfig getDefaultAppConfig()
	{
		return defaultAppConfig;
	}
	
	public static AppTestConfiguration getInstance()
	{
		//check to see if the instance is null
		if (null == instance)
		{
			//acquire a thread lock on this object to prevent the object from building twice
			synchronized (lock)
			{
				//now that a lock is in place, check again for null
				if (null == instance)
				{
					instance = new AppTestConfiguration();
				}
			}
		}
		
		return instance;
	}
}
