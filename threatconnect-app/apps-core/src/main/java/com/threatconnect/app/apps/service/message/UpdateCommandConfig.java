package com.threatconnect.app.apps.service.message;

import java.util.List;

public abstract class UpdateCommandConfig extends AbstractCommandConfig
{
	private List<NameValuePair<String, String>> config;
	
	public UpdateCommandConfig()
	{
		super(Command.UpdateConfig);
	}
	
	public List<NameValuePair<String, String>> getConfig()
	{
		return config;
	}
	
	public void setConfig(List<NameValuePair<String, String>> config)
	{
		this.config = config;
	}
}

