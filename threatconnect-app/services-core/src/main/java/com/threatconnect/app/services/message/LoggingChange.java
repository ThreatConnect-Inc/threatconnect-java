package com.threatconnect.app.services.message;

public class LoggingChange extends CommandMessage
{
	private String level;
	
	public LoggingChange()
	{
		super(CommandType.LoggingChange);
	}
	
	public String getLevel()
	{
		return level;
	}
	
	public void setLevel(final String level)
	{
		this.level = level;
	}
}
