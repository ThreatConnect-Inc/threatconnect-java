package com.threatconnect.app.apps.service.message;

public class StartSessionAcknowledgeMessage extends AcknowledgeMessage
{
	private String sessionId;
	private String apiToken;
	
	public StartSessionAcknowledgeMessage()
	{
		super(CommandMessage.Command.StartSession);
	}
	
	public String getSessionId()
	{
		return sessionId;
	}
	
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}
	
	public String getApiToken()
	{
		return apiToken;
	}
	
	public void setApiToken(String apiToken)
	{
		this.apiToken = apiToken;
	}
}
