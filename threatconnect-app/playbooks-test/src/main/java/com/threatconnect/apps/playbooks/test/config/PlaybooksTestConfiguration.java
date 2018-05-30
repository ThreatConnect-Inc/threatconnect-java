package com.threatconnect.apps.playbooks.test.config;

import com.threatconnect.app.addons.util.config.InvalidJsonFileException;
import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.install.InstallUtil;
import com.threatconnect.app.addons.util.config.validation.InstallValidator;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.AppExecutor;
import com.threatconnect.app.apps.DefaultAppConfig;
import com.threatconnect.app.playbooks.app.PlaybooksApp;
import com.threatconnect.app.playbooks.app.PlaybooksAppConfig;
import com.threatconnect.app.playbooks.db.DBServiceFactory;
import com.threatconnect.apps.playbooks.test.db.EmbeddedMapDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
public class PlaybooksTestConfiguration
{
	private static final Logger logger = LoggerFactory.getLogger(PlaybooksTestConfiguration.class);
	
	public static final Pattern PATTERN_INSTALL_JSON = Pattern.compile("^(?:(.*)\\.)?install\\.json$");
	
	private static final String APP_MAIN_CLASSNAME = "com.threatconnect.sdk.app.AppMain";
	
	//holds the instance to this singleton
	private static PlaybooksTestConfiguration instance;
	private static final Object lock = new Object();
	
	//holds the playbook configuration map
	private final Map<Class<? extends PlaybooksApp>, PlaybookConfig> configurationMap;
	
	//holds the default app config object
	private final AppConfig globalAppConfig;
	
	private PlaybooksTestConfiguration()
	{
		this.configurationMap = new HashMap<Class<? extends PlaybooksApp>, PlaybookConfig>();
		this.globalAppConfig = new DefaultAppConfig();
		
		loadConfigurationFiles();
	}
	
	public void loadConfigurationFiles()
	{
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
	
	public void registerEmbeddedDBService()
	{
		logger.info("Registering EmbeddedMapDBService as default database");
		
		//register the inmemory database
		DBServiceFactory.registerCustomDBService("Memory", new EmbeddedMapDBService());
		globalAppConfig.set(PlaybooksAppConfig.PARAM_DB_TYPE, "Memory");
	}
	
	public void setGlobalAppParam(final String name, final String value)
	{
		globalAppConfig.set(name, value);
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
			Install install = InstallUtil.load(file, new InstallValidator()
				{
					@Override
					public void validate(final Install object) throws ValidationException
					{
						//check to see if the program main is null
						if (null == object.getProgramMain())
						{
							object.setProgramMain(APP_MAIN_CLASSNAME);
						}
						
						super.validate(object);
					}
				}
			);
			
			//check to see if this is a playbooks app
			if (install.isPlaybookApp())
			{
				//configure the app for this install.json file
				PlaybookConfig playbookConfig = buildPlaybookConfig(install, file);
				registerPlaybookConfig(playbookConfig);
			}
			else
			{
				logger.info("Skipping \"{}\" -- runtimeLevel indicates this config is not a playbooks app ",
					file.getAbsolutePath());
			}
		}
		catch (UnsupportedPlaybookMainClassException | IOException | InvalidJsonFileException | ValidationException | InvalidPlaybookAppException e)
		{
			throw new PlaybooksConfigurationException(e);
		}
	}
	
	/**
	 * Reads an install.json file and configures the playbooks app according to the config
	 *
	 * @param install
	 * @throws InvalidJsonFileException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedPlaybookMainClassException
	 */
	private PlaybookConfig buildPlaybookConfig(final Install install, final File file)
		throws InvalidJsonFileException, UnsupportedPlaybookMainClassException, InvalidPlaybookAppException
	{
		try
		{
			//get the program main class
			@SuppressWarnings("unchecked")
			Class<? extends AppExecutor> programMainClass =
				(Class<? extends AppExecutor>) Class.forName(install.getProgramMain());
			
			//retrieve the classes that are executed from this main
			AppConfig appConfig = new DefaultAppConfig().copyFrom(globalAppConfig);
			appConfig.set(AppConfig.TC_MAIN_APP_CLASS, install.getMainAppClass());
			
			//retrieve the default constructor for this class and make sure it is accessible
			Constructor<? extends AppExecutor> constructor = programMainClass.getConstructor(AppConfig.class);
			constructor.setAccessible(true);
			
			//instantiate this class
			AppExecutor appExecutor = constructor.newInstance(appConfig);
			Class<? extends App> appClass = appExecutor.getAppClassToExecute();
			
			//ensure that this class has a declared static main method
			Method method = appExecutor.getClass().getDeclaredMethod("main", String[].class);
			if (!Modifier.isStatic(method.getModifiers()))
			{
				throw new UnsupportedPlaybookMainClassException(
					install.getProgramMain() + " must have static main method.");
			}
			
			//configure this app
			return buildPlaybookConfig(appClass, install, file);
		}
		catch (ClassNotFoundException e)
		{
			throw new UnsupportedPlaybookMainClassException(
				install.getProgramMain() + " could not be found.");
		}
		catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
		{
			throw new UnsupportedPlaybookMainClassException("Unable to instantiate " + install.getProgramMain());
		}
		catch (ClassCastException e)
		{
			throw new UnsupportedPlaybookMainClassException(install.getProgramMain() +
				" is not supported. Only classes that implement " + AppExecutor.class.getName()
				+ " are currently supported.");
		}
		catch (NoSuchMethodException e)
		{
			throw new UnsupportedPlaybookMainClassException(
				install.getProgramMain() + " must have static main method.");
		}
	}
	
	private PlaybookConfig buildPlaybookConfig(final Class<? extends App> appClass, final Install install,
		final File file)
		throws InvalidPlaybookAppException
	{
		logger.info("Configuring playbook \"{}\", loaded from file \"{}\"", appClass.getName(),
			file.getAbsolutePath());
		
		//make sure this app is a playbooks app
		if (PlaybooksApp.class.isAssignableFrom(appClass))
		{
			//cast the class
			Class<? extends PlaybooksApp> playbooksAppClass = (Class<? extends PlaybooksApp>) appClass;
			
			//create a new playbook configuration class
			return new PlaybookConfig(playbooksAppClass, install);
		}
		else
		{
			throw new InvalidPlaybookAppException(
				appClass.getName() + ", loaded from " + file.getAbsolutePath()
					+ ", must extend from " + PlaybooksApp.class.getName());
		}
	}
	
	private void registerPlaybookConfig(final PlaybookConfig playbookConfig)
	{
		//make sure this configuration does not already exist
		if (!configurationMap.containsKey(playbookConfig.getPlaybookAppClass()))
		{
			//add this config to the map
			configurationMap.put(playbookConfig.getPlaybookAppClass(), playbookConfig);
		}
		else
		{
			//warn that this class was already loaded
			logger
				.warn(playbookConfig.getPlaybookAppClass().getName() + " already configured. Skipping configuration.");
		}
	}
	
	/**
	 * Creates a builder object for dynamically registering a playbook app config without a json file
	 *
	 * @param playbookAppClass
	 * @return
	 */
	public PlaybookConfigBuilder createPlaybookConfigBuilder(final Class<? extends PlaybooksApp> playbookAppClass)
	{
		return new PlaybookConfigBuilder(playbookAppClass, this);
	}
	
	public PlaybookConfigBuilder createPlaybookConfigBuilder(final File file)
	{
		try
		{
			logger.info("Loading {}", file.getAbsolutePath());
			
			//read the json file
			Install install = InstallUtil.load(file, new InstallValidator()
				{
					@Override
					public void validate(final Install object) throws ValidationException
					{
						//check to see if the program main is null
						if (null == object.getProgramMain())
						{
							object.setProgramMain(APP_MAIN_CLASSNAME);
						}
						
						super.validate(object);
					}
				}
			);
			
			//check to see if this is a playbooks app
			if (install.isPlaybookApp())
			{
				//configure the app for this install.json file
				PlaybookConfig playbookConfig = buildPlaybookConfig(install, file);
				return new PlaybookConfigBuilder(playbookConfig, this);
			}
			else
			{
				throw new PlaybooksConfigurationException("Skipping \"" + file.getAbsolutePath()
					+ "\" -- runtimeLevel indicates this config is not a playbooks app ");
			}
		}
		catch (UnsupportedPlaybookMainClassException | IOException | InvalidJsonFileException | ValidationException | InvalidPlaybookAppException e)
		{
			throw new PlaybooksConfigurationException(e);
		}
	}
	
	/**
	 * Adds a playbook configuration to the map and overrides any existing configuration
	 *
	 * @param playbookConfig
	 */
	void registerDynamicPlaybookConfiguration(final PlaybookConfig playbookConfig)
	{
		logger.info("Registering dynamic PlaybookConfig for \"{}\"", playbookConfig.getPlaybookAppClass().getName());
		
		//check to see if a playbooks configuration already exists for this class
		if (configurationMap.containsKey(playbookConfig.getPlaybookAppClass()))
		{
			//notify the user via a warning
			logger.warn("Overriding existing configuration for \"{}\"", playbookConfig.getPlaybookAppClass().getName());
		}
		
		//add this config to the map
		configurationMap.put(playbookConfig.getPlaybookAppClass(), playbookConfig);
	}
	
	public Map<Class<? extends PlaybooksApp>, PlaybookConfig> getConfigurationMap()
	{
		return new HashMap<Class<? extends PlaybooksApp>, PlaybookConfig>(configurationMap);
	}
	
	public AppConfig getGlobalAppConfig()
	{
		return globalAppConfig;
	}
	
	public static PlaybooksTestConfiguration getInstance()
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
					instance = new PlaybooksTestConfiguration();
				}
			}
		}
		
		return instance;
	}
}
