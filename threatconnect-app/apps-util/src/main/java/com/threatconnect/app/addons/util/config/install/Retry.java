package com.threatconnect.app.addons.util.config.install;

/**
 * @author Greg Marut
 */
public class Retry
{
	private boolean allowed;
	private Integer defaultDelayMinutes;
	private Integer defaultMaxRetries;
	
	public boolean isAllowed()
	{
		return allowed;
	}
	
	public void setAllowed(final boolean allowed)
	{
		this.allowed = allowed;
	}
	
	public Integer getDefaultDelayMinutes()
	{
		return defaultDelayMinutes;
	}
	
	public void setDefaultDelayMinutes(final Integer defaultDelayMinutes)
	{
		this.defaultDelayMinutes = defaultDelayMinutes;
	}
	
	public Integer getDefaultMaxRetries()
	{
		return defaultMaxRetries;
	}
	
	public void setDefaultMaxRetries(final Integer defaultMaxRetries)
	{
		this.defaultMaxRetries = defaultMaxRetries;
	}
}
