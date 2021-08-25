package com.threatconnect.app.addons.util.config.install;

public class Docker
{
	private String image;
	private String entryPoint;
	private String debugEntryPoint;
	
	public String getImage()
	{
		return image;
	}
	
	public void setImage(final String image)
	{
		this.image = image;
	}
	
	public String getEntryPoint()
	{
		return entryPoint;
	}
	
	public void setEntryPoint(final String entryPoint)
	{
		this.entryPoint = entryPoint;
	}
	
	public String getDebugEntryPoint()
	{
		return debugEntryPoint;
	}
	
	public void setDebugEntryPoint(final String debugEntryPoint)
	{
		this.debugEntryPoint = debugEntryPoint;
	}
}
