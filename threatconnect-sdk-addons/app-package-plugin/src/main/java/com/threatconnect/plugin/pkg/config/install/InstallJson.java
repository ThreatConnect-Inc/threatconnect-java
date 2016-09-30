package com.threatconnect.plugin.pkg.config.install;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.threatconnect.plugin.pkg.config.InvalidInstallJsonFileException;
import com.threatconnect.plugin.pkg.util.JsonUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class InstallJson
{
	private static final String APPLICATION_NAME = "applicationName";
	private static final String PROGRAM_VERSION = "programVersion";
	private static final String PROGRAM_MAIN = "programMain";
	private static final String RUNTIME_LEVEL = "runtimeLevel";
	private static final String PARAMS = "params";
	private static final String PLAYBOOKS_OUTPUT_VARIABLES = "blueprintOutputVariables";
	
	private static final String PLAYBOOK_RUN_LEVEL = "Blueprint";
	
	private final File installJsonFile;
	private final JsonObject root;
	
	public InstallJson(final File installJsonFile) throws InvalidInstallJsonFileException
	{
		// make sure the file is not null
		if (null == installJsonFile)
		{
			throw new IllegalArgumentException("installJsonFile cannot be null");
		}
		
		try
		{
			this.installJsonFile = installJsonFile;
			
			// parse the install file
			JsonParser jsonParser = new JsonParser();
			root = jsonParser.parse(new FileReader(installJsonFile)).getAsJsonObject();
		}
		catch (JsonIOException | JsonSyntaxException | FileNotFoundException e)
		{
			throw new InvalidInstallJsonFileException(installJsonFile, e);
		}
	}
	
	public String getApplicationName()
	{
		return getAsString(APPLICATION_NAME);
	}
	
	public String getProgramVersion()
	{
		return getAsString(PROGRAM_VERSION);
	}
	
	public String getProgramMain()
	{
		return getAsString(PROGRAM_MAIN);
	}
	
	public String getRuntimeLevel()
	{
		return getAsString(RUNTIME_LEVEL);
	}
	
	public boolean isPlaybookApp()
	{
		return PLAYBOOK_RUN_LEVEL.equals(getRuntimeLevel());
	}
	
	public List<PlaybookOutputVariable> getPlaybooksOutputVariables()
	{
		List<PlaybookOutputVariable> results = new ArrayList<PlaybookOutputVariable>();
		
		//retrieve the playbooks output variables and make sure it is not null
		JsonElement variablesElement = root.get(PLAYBOOKS_OUTPUT_VARIABLES);
		if (null != variablesElement)
		{
			//for each of the variables
			JsonArray array = variablesElement.getAsJsonArray();
			for (JsonElement element : array)
			{
				//convert this json object to a playbook variable and add it to the results list
				results.add(new PlaybookOutputVariable(element.getAsJsonObject()));
			}
		}
		
		return results;
	}
	
	public List<Param> getPlaybooksParams()
	{
		List<Param> results = new ArrayList<Param>();
		
		//retrieve the params and make sure it is not null
		JsonElement paramsElement = root.get(PARAMS);
		if (null != paramsElement)
		{
			//for each of the params
			JsonArray array = paramsElement.getAsJsonArray();
			for (JsonElement element : array)
			{
				//convert this json object to a param
				Param param = new Param(element.getAsJsonObject());
				
				//check to see if this is a playbook param
				if (param.isPlaybookParam())
				{
					results.add(param);
				}
			}
		}
		
		return results;
	}
	
	private String getAsString(final String... paths)
	{
		return JsonUtil.getAsString(root, paths);
	}
	
	public File getInstallJsonFile()
	{
		return installJsonFile;
	}
}
