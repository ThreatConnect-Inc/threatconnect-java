package com.threatconnect.app.services.message;

public class AcknowledgeMessage extends AbstractCommandConfig
{
	private CommandType type;
	
	public AcknowledgeMessage(final CommandType type)
	{
		super(CommandType.Acknowledge);
		this.type = type;
	}
	
	public CommandType getType()
	{
		return type;
	}
	
	public void setType(final CommandType type)
	{
		this.type = type;
	}
}
