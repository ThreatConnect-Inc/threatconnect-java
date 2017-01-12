package com.threatconnect.app.addons.util.config.install;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class Playbook
{
	private String type;
	private Retry retry;
	
	private final List<PlaybookOutputVariable> outputVariables;
	
	public Playbook()
	{
		this.outputVariables = new ArrayList<PlaybookOutputVariable>();
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(final String type)
	{
		this.type = type;
	}
	
	public List<PlaybookOutputVariable> getOutputVariables()
	{
		return outputVariables;
	}
	
	public Retry getRetry()
	{
		return retry;
	}
	
	public void setRetry(final Retry retry)
	{
		this.retry = retry;
	}
}
