package com.threatconnect.app.apps.service.message;

import java.util.List;

public class CreateCommandConfig extends AbstractCommandConfig
{
	private List<NameValuePair<String, String>> config;
	private List<String> outputVariables;
	
	public CreateCommandConfig()
	{
		super(Command.CreateConfig);
	}
	
	public List<NameValuePair<String, String>> getConfig()
	{
		return config;
	}
	
	public void setConfig(List<NameValuePair<String, String>> config)
	{
		this.config = config;
	}
	
	public List<String> getOutputVariables()
	{
		return outputVariables;
	}
	
	public void setOutputVariables(List<String> outputVariables)
	{
		this.outputVariables = outputVariables;
	}
}
