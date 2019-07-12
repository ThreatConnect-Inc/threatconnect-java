package com.threatconnect.app.services.message;

public abstract class AbstractCommandConfig extends CommandMessage
{
	private Long triggerId;
	private String requestKey;
	
	public AbstractCommandConfig(final CommandType command)
	{
		super(command);
	}
	
	public Long getTriggerId()
	{
		return triggerId;
	}
	
	public void setTriggerId(final Long triggerId)
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
