package com.threatconnect.app.services.message;

public class CreateConfigAcknowledgedMessage extends AcknowledgedMessage
{
	private String logFile;
	
	public CreateConfigAcknowledgedMessage()
	{
		super(CommandType.CreateConfig);
	}
	
	public String getLogFile()
	{
		return logFile;
	}
	
	public void setLogFile(final String logFile)
	{
		this.logFile = logFile;
	}
}
