package com.threatconnect.app.apps.service.message;

public class FireEventAcknowledgeMessage extends AcknowledgeMessage
{
	public FireEventAcknowledgeMessage()
	{
		super(CommandType.FireEvent);
	}
}
