package com.threatconnect.apps.app.test.config;

import com.threatconnect.app.addons.util.config.install.Param;
import com.threatconnect.app.addons.util.config.install.ParamDataType;
import com.threatconnect.app.apps.App;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder object for configuring {@link AppConfiguration} objects dynamically
 *
 * @author Greg Marut
 */
public class AppConfigurationBuilder
{
	//holds the instance of the app test configuration
	private final AppTestConfiguration appTestConfiguration;
	
	//holds the app class to configure this app config for
	private final Class<? extends App> appClass;
	
	//holds the list of configured params for this app
	private final List<Param> appParamList;
	
	AppConfigurationBuilder(final Class<? extends App> appClass,
		final AppTestConfiguration appTestConfiguration)
	{
		this.appClass = appClass;
		this.appTestConfiguration = appTestConfiguration;
		
		this.appParamList = new ArrayList<Param>();
	}
	
	public AppConfigurationBuilder addAppParam(final String name, final ParamDataType type)
	{
		//create the new param object and add it to the list
		Param param = new Param(name, type);
		appParamList.add(param);
		
		return this;
	}
	
	/**
	 * Builds this configuration and registers it with the test configuration
	 */
	public void build()
	{
		//create a new app configuration
		AppConfiguration appConfiguration = new AppConfiguration(appClass, appParamList);
		
		//register this app configuration with the test configuration
		appTestConfiguration.registerDynamicPlaybookConfiguration(appConfiguration);
	}
}
