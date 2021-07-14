package com.threatconnect.app.addons.util.config.install;

import java.util.List;

/**
 * @author Greg Marut
 */
public class Retry
{
	private boolean allowed;
	private List<String> actions;
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
	
	public List<String> getActions()
	{
		return actions;
	}
	
	public void setActions(final List<String> actions)
	{
		this.actions = actions;
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
