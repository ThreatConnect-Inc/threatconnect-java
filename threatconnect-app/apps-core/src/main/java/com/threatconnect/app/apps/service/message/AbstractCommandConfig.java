package com.threatconnect.app.apps.service.message;

public abstract class AbstractCommandConfig extends CommandMessage
{
	private long triggerId;
	private String requestKey;
	
	public AbstractCommandConfig(final Command command)
	{
		super(command);
	}
	
	public long getTriggerId()
	{
		return triggerId;
	}
	
	public void setTriggerId(final long triggerId)
	{
		this.triggerId = triggerId;
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
