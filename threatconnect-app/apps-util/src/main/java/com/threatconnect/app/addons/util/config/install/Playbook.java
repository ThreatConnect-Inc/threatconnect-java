package com.threatconnect.app.addons.util.config.install;

import java.util.List;

/**
 * @author Greg Marut
 */
public class Playbook
{
	private String type;
	private List<PlaybookOutputVariable> outputVariables;
	private Retry retry;
	
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
	
	public void setOutputVariables(
		final List<PlaybookOutputVariable> outputVariables)
	{
		this.outputVariables = outputVariables;
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
