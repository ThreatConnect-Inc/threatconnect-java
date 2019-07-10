package com.threatconnect.app.apps.service.message;

import java.util.HashMap;
import java.util.Map;

public class CreateCommandConfig extends AbstractCommandConfig
{
	private final Map<String, Object> config;
	
	public CreateCommandConfig()
	{
		super(Command.CreateConfig);
		this.config = new HashMap<String, Object>();
	}
	
	public Map<String, Object> getConfig()
	{
		return config;
	}
}
