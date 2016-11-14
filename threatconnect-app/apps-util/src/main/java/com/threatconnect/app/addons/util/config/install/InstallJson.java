package com.threatconnect.app.addons.util.config.install;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.threatconnect.app.addons.util.JsonUtil;

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
	
	private static final String PLAYBOOK = "playbook";
	private static final String PLAYBOOK_RUN_LEVEL = "Playbook";
	
	private final File installJsonFile;
	private final JsonObject root;
	private final List<Param> allParams;
	private final List<Param> playbookParams;
	
	public InstallJson(final File installJsonFile) throws InvalidInstallJsonFileException
	{
		// make sure the file is not null
		if (null == installJsonFile)
		{
			throw new IllegalArgumentException("installJsonFile cannot be null");
		}
		
		this.installJsonFile = installJsonFile;
		this.allParams = new ArrayList<Param>();
		this.playbookParams = new ArrayList<Param>();
		
		try
		{
			// parse the install file
			JsonParser jsonParser = new JsonParser();
			root = jsonParser.parse(new FileReader(installJsonFile)).getAsJsonObject();
			
			//retrieve the params and make sure it is not null
			JsonElement paramsElement = root.get(PARAMS);
			if (null != paramsElement)
			{
				//make sure this is an array
				if(!paramsElement.isJsonArray())
				{
					throw new InvalidInstallJsonFileException(PARAMS + " must be an array");
				}
				
				//for each of the params
				JsonArray array = paramsElement.getAsJsonArray();
				for (JsonElement element : array)
				{
					//convert this json object to a param
					Param param = new Param(element.getAsJsonObject());
					
					//check to see if this is a playbook param
					if (param.isPlaybookParam())
					{
						//add this playbook param
						playbookParams.add(param);
					}
					
					//add this to all of the params
					allParams.add(param);
				}
			}
		}
		catch (JsonIOException | JsonSyntaxException | FileNotFoundException e)
		{
			throw new InvalidInstallJsonFileException(installJsonFile, e);
		}
	}
	
	public String getApplicationName()
	{
		return JsonUtil.getAsString(root, APPLICATION_NAME);
	}
	
	public String getProgramVersion()
	{
		return JsonUtil.getAsString(root, PROGRAM_VERSION);
	}
	
	public String getProgramMain()
	{
		return JsonUtil.getAsString(root, PROGRAM_MAIN);
	}
	
	public String getRuntimeLevel()
	{
		return JsonUtil.getAsString(root, RUNTIME_LEVEL);
	}
	
	public boolean isPlaybookApp()
	{
		return PLAYBOOK_RUN_LEVEL.equals(getRuntimeLevel());
	}
	
	public Playbook getPlaybook()
	{
		return new Playbook(root.get(PLAYBOOK).getAsJsonObject());
	}
	
	public List<Param> getAllParams()
	{
		return allParams;
	}
	
	public List<Param> getPlaybooksParams()
	{
		return playbookParams;
	}
	
	public File getInstallJsonFile()
	{
		return installJsonFile;
	}
}
