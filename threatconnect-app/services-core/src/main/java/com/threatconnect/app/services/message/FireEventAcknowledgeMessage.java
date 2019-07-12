package com.threatconnect.app.services.message;

public class FireEventAcknowledgeMessage extends AcknowledgeMessage
{
	public FireEventAcknowledgeMessage()
	{
		super(CommandType.FireEvent);
	}
}
