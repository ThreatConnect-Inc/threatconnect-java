package com.threatconnect.apps.app.test.config;

import com.threatconnect.app.addons.util.config.install.InstallJson;
import com.threatconnect.app.addons.util.config.install.Param;
import com.threatconnect.app.apps.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Greg Marut
 */
public class AppConfiguration
{
	private static final Logger logger = LoggerFactory.getLogger(AppConfiguration.class);
	
	private final Class<? extends App> appClass;
	
	//holds the maps to index the params
	private final Map<String, Param> appParams;
	
	public AppConfiguration(final Class<? extends App> appClass)
	{
		this.appClass = appClass;
		this.appParams = new HashMap<String, Param>();
	}
	
	public AppConfiguration(final Class<? extends App> appClass, final InstallJson installJson)
	{
		this(appClass, installJson.getAllParams());
	}
	
	public AppConfiguration(final Class<? extends App> playbookAppClass, final List<Param> allParamList)
	{
		this(playbookAppClass);
		
		logger.debug("Found {} params for \"{}\"", allParamList.size(), playbookAppClass.getName());
		
		//for each of the params
		for (Param param : allParamList)
		{
			//make sure this param does not already exist
			if (!appParams.containsKey(param.getName()))
			{
				//add this param to the map
				appParams.put(param.getName(), param);
			}
			else
			{
				//warn that this param was already added
				logger.warn("Duplicate param found: {}", param.getName());
			}
		}
	}
	
	public Class<? extends App> getAppClass()
	{
		return appClass;
	}
	
	public Collection<Param> getAllInputParams()
	{
		return appParams.values();
	}
	
	public boolean isValidAppParam(final String paramName)
	{
		return appParams.containsKey(paramName);
	}
}
