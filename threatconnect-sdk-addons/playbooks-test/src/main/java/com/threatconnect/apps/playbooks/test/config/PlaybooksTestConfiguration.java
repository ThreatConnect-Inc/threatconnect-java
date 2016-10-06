package com.threatconnect.apps.playbooks.test.config;

import com.threatconnect.apps.playbooks.test.db.EmbeddedMapDBService;
import com.threatconnect.sdk.addons.util.config.install.InvalidInstallJsonFileException;
import com.threatconnect.sdk.addons.util.config.install.InstallJson;
import com.threatconnect.sdk.app.App;
import com.threatconnect.sdk.app.AppConfig;
import com.threatconnect.sdk.app.AppMain;
import com.threatconnect.sdk.playbooks.app.PlaybooksApp;
import com.threatconnect.sdk.playbooks.app.PlaybooksAppConfig;
import com.threatconnect.sdk.playbooks.db.DBServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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
	public static final Pattern PATTERN_INSTALL_JSON = Pattern.compile("^(?:(.*)\\.)?install\\.json$");
	
	private static final Logger logger = LoggerFactory.getLogger(PlaybooksTestConfiguration.class);
	
	//holds the instance to this singleton
	private static PlaybooksTestConfiguration instance;
	private static final Object lock = new Object();
	
	//holds the playbook configuration map
	private final Map<Class<? extends PlaybooksApp>, PlaybookConfig> configurationMap;
	
	private PlaybooksTestConfiguration()
	{
		this.configurationMap = new HashMap<Class<? extends PlaybooksApp>, PlaybookConfig>();
		
		logger.info("Loading Playbooks Test Configuration");
		
		//find all of the install.json files
		List<File> files = findInstallJsonFiles();
		
		logger.info("Found {} install.json files", files.size());
		
		try
		{
			//for each of the files
			for (File file : files)
			{
				//read the json file
				InstallJson installJson = new InstallJson(file);
				
				//check to see if this is a playbooks app
				if (installJson.isPlaybookApp())
				{
					//configure the app for this install.json file
					configureApp(installJson);
				}
				else
				{
					logger.info("Skipping \"{}\" -- runtimeLevel indicates this config is not a playbooks app ",
						file.getAbsolutePath());
				}
			}
		}
		catch (UnsupposedPlaybookMainClassException | InvalidInstallJsonFileException | InvalidPlaybookAppException e)
		{
			throw new PlaybooksConfigurationException(e);
		}
	}
	
	public void registerEmbeddedDBService()
	{
		logger.info("Registering EmbeddedMapDBService as default database");
		
		//register the inmemory database
		DBServiceFactory.registerCustomDBService("Memory", new EmbeddedMapDBService());
		AppConfig.getInstance().set(PlaybooksAppConfig.PARAM_DB_TYPE, "Memory");
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
	
	/**
	 * Reads an install.json file and configures the playbooks app according to the config
	 *
	 * @param installJson
	 * @throws InvalidInstallJsonFileException
	 * @throws ClassNotFoundException
	 * @throws UnsupposedPlaybookMainClassException
	 */
	private void configureApp(final InstallJson installJson)
		throws InvalidInstallJsonFileException, UnsupposedPlaybookMainClassException, InvalidPlaybookAppException
	{
		try
		{
			//get the program main class
			Class<? extends AppMain> programMainClass =
				(Class<? extends AppMain>) Class.forName(installJson.getProgramMain());
			
			//instantiate this class
			AppMain appMain = (AppMain) programMainClass.newInstance();
			
			//retrieve the classes that are executed from this main
			List<Class<? extends App>> classes = appMain.getAppClassesToExecute(AppConfig.getInstance());
			
			//for each of the classes
			for (Class<? extends App> appClass : classes)
			{
				//configure this app
				configureApp(appClass, installJson);
			}
		}
		catch (ClassNotFoundException e)
		{
			throw new UnsupposedPlaybookMainClassException(
				installJson.getProgramMain() + " could not be found.");
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new UnsupposedPlaybookMainClassException(
				installJson.getProgramMain() + " must have a public no-arg constructor.");
		}
		catch (ClassCastException e)
		{
			throw new UnsupposedPlaybookMainClassException(installJson.getProgramMain() +
				" is not supported. Only classes that extend " + AppMain.class.getName() + " are currently supported.");
		}
	}
	
	private void configureApp(final Class<? extends App> appClass, final InstallJson installJson)
		throws InvalidPlaybookAppException
	{
		logger.info("Configuring playbook \"{}\", loaded from file \"{}\"", appClass.getName(),
			installJson.getInstallJsonFile().getAbsolutePath());
		
		//make sure this app is a playbooks app
		if (PlaybooksApp.class.isAssignableFrom(appClass))
		{
			//cast the class
			Class<? extends PlaybooksApp> playbooksAppClass = (Class<? extends PlaybooksApp>) appClass;
			
			//make sure this configuration does not already exist
			if (!configurationMap.containsKey(playbooksAppClass))
			{
				//create a new playbook configuration class
				PlaybookConfig playbookConfig = new PlaybookConfig(playbooksAppClass, installJson);
				
				//add this config to the map
				configurationMap.put(playbooksAppClass, playbookConfig);
			}
			else
			{
				//warn that this class was already loaded
				logger.warn(playbooksAppClass.getName() + " already configured. Skipping configuration.");
			}
		}
		else
		{
			throw new InvalidPlaybookAppException(
				appClass.getName() + ", loaded from " + installJson.getInstallJsonFile().getAbsolutePath()
					+ ", must extend from " + PlaybooksApp.class.getName());
		}
	}
	
	public Map<Class<? extends PlaybooksApp>, PlaybookConfig> getConfigurationMap()
	{
		return new HashMap<Class<? extends PlaybooksApp>, PlaybookConfig>(configurationMap);
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
