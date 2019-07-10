package com.threatconnect.app.apps.service.message;

public class AcknowledgeMessage extends AbstractCommandConfig
{
	private Command type;
	
	public AcknowledgeMessage(final Command type)
	{
		super(Command.Acknowledge);
		this.type = type;
	}
	
	public Command getType()
	{
		return type;
	}
	
	public void setType(final Command type)
	{
		this.type = type;
	}
}
