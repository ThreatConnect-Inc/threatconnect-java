package com.threatconnect.app.apps.service.message;

import java.util.HashMap;
import java.util.Map;

public abstract class UpdateCommandConfig extends AbstractCommandConfig
{
	private final Map<String, Object> config;
	
	public UpdateCommandConfig()
	{
		super(CommandType.UpdateConfig);
		this.config = new HashMap<String, Object>();
	}
	
	public Map<String, Object> getConfig()
	{
		return config;
	}
}

