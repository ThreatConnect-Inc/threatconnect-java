package com.threatconnect.app.services.message;

public class ServiceExitEvent extends AbstractCommandConfig
{
	private Integer exitValue;
	private String sessionId;
	private String launchId;
	
	public ServiceExitEvent()
	{
		super(CommandType.ServiceExitEvent);
	}
	
	public Integer getExitValue()
	{
		return exitValue;
	}
	
	public void setExitValue(final Integer exitValue)
	{
		this.exitValue = exitValue;
	}
	
	public String getSessionId()
	{
		return sessionId;
	}
	
	public void setSessionId(final String sessionId)
	{
		this.sessionId = sessionId;
	}
	
	public String getLaunchId()
	{
		return launchId;
	}
	
	public void setLaunchId(final String launchId)
	{
		this.launchId = launchId;
	}
}
