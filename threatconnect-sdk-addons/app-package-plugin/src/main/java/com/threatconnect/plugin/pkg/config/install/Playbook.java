package com.threatconnect.plugin.pkg.config.install;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.threatconnect.sdk.addons.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class Playbook
{
	private static final String TYPE = "type";
	private static final String OUTPUT_VARIABLES = "outputVariables";
	private static final String RETRY = "retry";
	private static final String ALLOWED = "allowed";
	
	private final JsonObject root;
	
	public Playbook(final JsonObject root)
	{
		this.root = root;
	}
	
	public String getType()
	{
		return JsonUtil.getAsString(root, TYPE);
	}
	
	public boolean isRetryAllowed()
	{
		JsonElement allowedElement = JsonUtil.get(root, RETRY, ALLOWED);
		if (null != allowedElement)
		{
			return allowedElement.getAsBoolean();
		}
		else
		{
			return false;
		}
	}
	
	public List<PlaybookOutputVariable> getPlaybooksOutputVariables()
	{
		List<PlaybookOutputVariable> results = new ArrayList<PlaybookOutputVariable>();
		
		//retrieve the playbooks output variables and make sure it is not null
		JsonElement variablesElement = root.get(OUTPUT_VARIABLES);
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
}
