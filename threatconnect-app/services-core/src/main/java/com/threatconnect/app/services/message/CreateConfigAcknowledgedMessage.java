package com.threatconnect.app.services.message;

public class CreateConfigAcknowledgedMessage extends AcknowledgedMessage
{
	public CreateConfigAcknowledgedMessage()
	{
		super(CommandType.CreateConfig);
	}
}
