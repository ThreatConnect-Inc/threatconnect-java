package com.threatconnect.app.addons.util.config.layout;

import java.util.ArrayList;
import java.util.List;

public class LayoutInput
{
	private final List<Parameter> parameters;
	
	private String title;
	private int sequence;
	
	public LayoutInput()
	{
		this.parameters = new ArrayList<Parameter>();
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(final String title)
	{
		this.title = title;
	}
	
	public int getSequence()
	{
		return sequence;
	}
	
	public void setSequence(final int sequence)
	{
		this.sequence = sequence;
	}
	
	public List<Parameter> getParameters()
	{
		return parameters;
	}
}
