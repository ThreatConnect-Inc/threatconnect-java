package com.threatconnect.app.apps.service.message;

public class AcknowledgeMessage extends AbstractCommandConfig
{
	private static final String STATUS_ACKNOWLEDGE = "Acknowledge";
	private String status;
	
	public AcknowledgeMessage(final Command command)
	{
		this(command, STATUS_ACKNOWLEDGE);
	}
	
	public AcknowledgeMessage(final Command command, final String status)
	{
		super(command);
		this.status = status;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
}
