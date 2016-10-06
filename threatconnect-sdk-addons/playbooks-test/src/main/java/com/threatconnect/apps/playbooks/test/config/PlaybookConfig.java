package com.threatconnect.apps.playbooks.test.config;

import com.threatconnect.sdk.addons.util.config.install.InstallJson;
import com.threatconnect.sdk.addons.util.config.install.Param;
import com.threatconnect.sdk.addons.util.config.install.PlaybookOutputVariable;
import com.threatconnect.sdk.playbooks.app.PlaybooksApp;
import com.threatconnect.sdk.playbooks.content.StandardType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Greg Marut
 */
public class PlaybookConfig
{
	private static final Logger logger = LoggerFactory.getLogger(PlaybookConfig.class);
	
	//holds the counter for the playbook app id
	private static final AtomicInteger counter = new AtomicInteger();
	
	private final int appID;
	private final Class<? extends PlaybooksApp> playbookAppClass;
	
	//holds the maps to index the params and variables
	private final Map<String, Param> playbookParams;
	private final Map<String, PlaybookOutputVariable> playbookOutputVariables;
	
	public PlaybookConfig(final Class<? extends PlaybooksApp> playbookAppClass)
	{
		this.appID = counter.getAndIncrement();
		this.playbookAppClass = playbookAppClass;
		this.playbookParams = new HashMap<String, Param>();
		this.playbookOutputVariables = new HashMap<String, PlaybookOutputVariable>();
	}
	
	public PlaybookConfig(final Class<? extends PlaybooksApp> playbookAppClass, final InstallJson installJson)
	{
		this(playbookAppClass);
		
		List<Param> playbookParamList = installJson.getPlaybooksParams();
		logger.debug("Found {} playbook params for \"{}\"", playbookParamList.size(), playbookAppClass.getName());
		
		List<PlaybookOutputVariable> playbookOutputVariableList =
			installJson.getPlaybook().getPlaybooksOutputVariables();
		logger.debug("Found {} playbook output variables for \"{}\"", playbookOutputVariableList.size(),
			playbookAppClass.getName());
		
		//for each of the playbook params
		for (Param param : playbookParamList)
		{
			//make sure this param does not already exist
			if (!playbookParams.containsKey(param.getName()))
			{
				//add this param to the map
				playbookParams.put(param.getName(), param);
			}
			else
			{
				//warn that this param was already added
				logger.warn("Duplicate playbook param found: {}", param.getName());
			}
		}
		
		//for each of the output variables
		for (PlaybookOutputVariable playbookOutputVariable : playbookOutputVariableList)
		{
			//make sure this variable does not already exist
			if (!playbookOutputVariables.containsKey(playbookOutputVariable.getName()))
			{
				//add this variable to the map
				playbookOutputVariables.put(playbookOutputVariable.getName(), playbookOutputVariable);
			}
			else
			{
				//warn that this variable was already added
				logger.warn("Duplicate playbook param found: {}", playbookOutputVariable.getName());
			}
		}
	}
	
	public int getAppID()
	{
		return appID;
	}
	
	public Class<? extends PlaybooksApp> getPlaybookAppClass()
	{
		return playbookAppClass;
	}
	
	public String[] createVariablesForInputParam(final String paramName)
	{
		//holds the list of strings
		List<String> results = new ArrayList<String>();
		
		Param param = getInputParam(paramName);
		
		//for each of the blueprint types
		for (String dataType : param.getPlaybookDataTypes())
		{
			String variable = buildParam(this.appID, param.getName(), dataType);
			results.add(variable);
		}
		
		return results.toArray(new String[] {});
	}
	
	public String createVariableForInputParam(final String paramName, final StandardType type)
	{
		Param param = getInputParam(paramName);
		
		//for each of the blueprint types
		for (String dataType : param.getPlaybookDataTypes())
		{
			//check to see if this datatype matches
			if (type.toString().equals(dataType))
			{
				return buildParam(this.appID, param.getName(), dataType);
			}
		}
		
		throw new IllegalArgumentException(
			"Cannot create input param variable. Invalid combination of paramName and type");
	}
	
	public String createVariableForOutputVariable(final String outputVariable)
	{
		PlaybookOutputVariable playbookOutputVariable = getOutputVariable(outputVariable);
		return buildParam(this.appID, playbookOutputVariable.getName(), playbookOutputVariable.getType());
	}
	
	public Param getInputParam(final String paramName)
	{
		//make sure this param name exists
		if (isValidInputParam(paramName))
		{
			return playbookParams.get(paramName);
		}
		else
		{
			throw new InvalidParamException(
				"\"" + paramName + "\" is not a valid input parameter for playbook app \"" + getPlaybookAppClass()
					.getName() + "\"");
		}
	}
	
	public PlaybookOutputVariable getOutputVariable(final String outputVariable)
	{
		//make sure this output param name exists
		if (isValidOutputVariable(outputVariable))
		{
			return playbookOutputVariables.get(outputVariable);
		}
		else
		{
			throw new InvalidParamException(
				"\"" + outputVariable + "\" is not a valid output variable for playbook app \"" + getPlaybookAppClass()
					.getName() + "\"");
		}
	}
	
	public Collection<Param> getAllInputParams()
	{
		return playbookParams.values();
	}
	
	public Collection<PlaybookOutputVariable> getAllOutputVariables()
	{
		return playbookOutputVariables.values();
	}
	
	public boolean isValidInputParam(final String paramName)
	{
		return playbookParams.containsKey(paramName);
	}
	
	public boolean isValidOutputVariable(final String outputVariable)
	{
		return playbookOutputVariables.containsKey(outputVariable);
	}
	
	private String buildParam(final int appID, final String paramName, final String paramType)
	{
		return "#App:" + appID + ":" + paramName + "!" + paramType;
	}
}
