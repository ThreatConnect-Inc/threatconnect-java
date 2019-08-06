package com.threatconnect.app.services.message;

public class FireEventAcknowledgedMessage extends AcknowledgedMessage
{
	public FireEventAcknowledgedMessage()
	{
		super(CommandType.FireEvent);
	}
}
