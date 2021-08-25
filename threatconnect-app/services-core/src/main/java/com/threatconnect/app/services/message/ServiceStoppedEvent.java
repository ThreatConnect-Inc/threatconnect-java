package com.threatconnect.app.services.message;

public class ServiceStoppedEvent extends AbstractCommandConfig
{
	private Integer exitValue;
	private String exitMessage;
	private String sessionId;
	
	public ServiceStoppedEvent()
	{
		super(CommandType.ServiceStoppedEvent);
	}
	
	public Integer getExitValue()
	{
		return exitValue;
	}
	
	public void setExitValue(final Integer exitValue)
	{
		this.exitValue = exitValue;
	}
	
	public String getExitMessage()
	{
		return exitMessage;
	}
	
	public void setExitMessage(final String exitMessage)
	{
		this.exitMessage = exitMessage;
	}
	
	public String getSessionId()
	{
		return sessionId;
	}
	
	public void setSessionId(final String sessionId)
	{
		this.sessionId = sessionId;
	}
}
