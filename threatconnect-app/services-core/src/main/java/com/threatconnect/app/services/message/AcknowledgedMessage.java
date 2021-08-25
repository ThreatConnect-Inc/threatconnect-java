package com.threatconnect.app.services.message;

public class AcknowledgedMessage extends AbstractCommandConfig
{
	private CommandType type;
	private String message;
	private String status;
	
	public AcknowledgedMessage(final CommandType type)
	{
		super(CommandType.Acknowledged);
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
	
	public String getMessage()
	{
		return message;
	}
	
	public void setMessage(final String message)
	{
		this.message = message;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(final String status)
	{
		this.status = status;
	}
	
	@Override
	protected String generateRequestKey()
	{
		return getTriggerId() + type.toString();
	}
}
