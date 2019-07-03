package com.threatconnect.app.apps.service.message;

public abstract class AbstractCommandConfig extends CommandMessage
{
	private long configId;
	private String requestKey;
	
	public AbstractCommandConfig(final Command command)
	{
		super(command);
	}
	
	public long getConfigId()
	{
		return configId;
	}
	
	public void setConfigId(final long configId)
	{
		this.configId = configId;
	}
	
	public String getRequestKey()
	{
		return requestKey;
	}
	
	public void setRequestKey(final String requestKey)
	{
		this.requestKey = requestKey;
	}
}
