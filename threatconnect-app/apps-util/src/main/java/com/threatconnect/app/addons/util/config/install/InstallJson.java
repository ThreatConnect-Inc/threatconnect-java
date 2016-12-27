package com.threatconnect.app.addons.util.config.install;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.threatconnect.app.addons.util.JsonUtil;
import com.threatconnect.app.addons.util.config.InvalidJsonFileException;
import com.threatconnect.app.addons.util.config.JsonFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InstallJson extends JsonFile
{
	private static final String APPLICATION_NAME = "applicationName";
	private static final String PROGRAM_VERSION = "programVersion";
	private static final String PROGRAM_MAIN = "programMain";
	private static final String RUNTIME_LEVEL = "runtimeLevel";
	private static final String PARAMS = "params";
	
	private static final String PLAYBOOK = "playbook";
	private static final String PLAYBOOK_RUN_LEVEL = "Playbook";
	
	private final List<Param> allParams;
	private final List<Param> playbookParams;
	
	public InstallJson(final File file) throws InvalidJsonFileException
	{
		super(file);
		
		this.allParams = new ArrayList<Param>();
		this.playbookParams = new ArrayList<Param>();
		
		try
		{
			//retrieve the params and make sure it is not null
			JsonElement paramsElement = getRoot().get(PARAMS);
			if (null != paramsElement)
			{
				//make sure this is an array
				if(!paramsElement.isJsonArray())
				{
					throw new InvalidJsonFileException(PARAMS + " must be an array");
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
		catch (JsonIOException | JsonSyntaxException e)
		{
			throw new InvalidJsonFileException(getFile(), e);
		}
	}
	
	public String getApplicationName()
	{
		return JsonUtil.getAsString(getRoot(), APPLICATION_NAME);
	}
	
	public String getProgramVersion()
	{
		return JsonUtil.getAsString(getRoot(), PROGRAM_VERSION);
	}
	
	public String getProgramMain()
	{
		return JsonUtil.getAsString(getRoot(), PROGRAM_MAIN);
	}
	
	public String getRuntimeLevel()
	{
		return JsonUtil.getAsString(getRoot(), RUNTIME_LEVEL);
	}
	
	public boolean isPlaybookApp()
	{
		return PLAYBOOK_RUN_LEVEL.equals(getRuntimeLevel());
	}
	
	public Playbook getPlaybook()
	{
		return new Playbook(getRoot().get(PLAYBOOK).getAsJsonObject());
	}
	
	public List<Param> getAllParams()
	{
		return allParams;
	}
	
	public List<Param> getPlaybooksParams()
	{
		return playbookParams;
	}
}
