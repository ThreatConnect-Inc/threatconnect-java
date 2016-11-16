package com.threatconnect.apps.playbooks.test.config;

import com.threatconnect.app.addons.util.config.install.Param;
import com.threatconnect.app.addons.util.config.install.ParamDataType;
import com.threatconnect.app.addons.util.config.install.PlaybookOutputVariable;
import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.app.playbooks.app.PlaybooksApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A builder object for configuring {@link PlaybookConfiguration} objects dynamically
 *
 * @author Greg Marut
 */
public class PlaybookConfigurationBuilder
{
	//holds the instance of the playbooks test configuration
	private final PlaybooksTestConfiguration playbooksTestConfiguration;
	
	//holds the app class to configure this playbook config for
	private final Class<? extends PlaybooksApp> playbookAppClass;
	
	//holds the list of configured params for this playbooks app
	private final List<Param> playbookParamList;
	private final List<PlaybookOutputVariable> playbookOutputVariableList;
	
	PlaybookConfigurationBuilder(final Class<? extends PlaybooksApp> playbookAppClass,
		final PlaybooksTestConfiguration playbooksTestConfiguration)
	{
		this.playbookAppClass = playbookAppClass;
		this.playbooksTestConfiguration = playbooksTestConfiguration;
		
		this.playbookParamList = new ArrayList<Param>();
		this.playbookOutputVariableList = new ArrayList<PlaybookOutputVariable>();
	}
	
	public PlaybookConfigurationBuilder addAppParam(final String name, final ParamDataType type)
	{
		//create the new param object and add it to the list
		Param param = new Param(name, type);
		playbookParamList.add(param);
		
		return this;
	}
	
	public PlaybookConfigurationBuilder addPlaybookParam(final String name, final ParamDataType type,
		final PlaybookVariableType... playbookVariableTypes)
	{
		//make sure the playbooks variable type is not null
		if (null == playbookVariableTypes || playbookVariableTypes.length == 0)
		{
			throw new IllegalArgumentException("playbookVariableTypes cannot be null or empty");
		}
		
		//create the new param object and add it to the list
		Param param = new Param(name, type, Arrays.asList(playbookVariableTypes));
		playbookParamList.add(param);
		
		return this;
	}
	
	public PlaybookConfigurationBuilder addOutputVariable(final String name, final PlaybookVariableType playbookVariableType)
	{
		PlaybookOutputVariable playbookOutputVariable = new PlaybookOutputVariable(name, playbookVariableType);
		playbookOutputVariableList.add(playbookOutputVariable);
		
		return this;
	}
	
	/**
	 * Builds this configuration and registers it with the test configuration
	 */
	public void build()
	{
		//create a new playbook config
		PlaybookConfiguration playbookConfiguration =
			new PlaybookConfiguration(playbookAppClass, playbookParamList, playbookOutputVariableList);
		
		//register this playbook config with the test configuration
		playbooksTestConfiguration.registerDynamicPlaybookConfiguration(playbookConfiguration);
	}
}
