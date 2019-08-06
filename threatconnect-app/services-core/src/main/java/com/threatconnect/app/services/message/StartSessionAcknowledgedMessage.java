package com.threatconnect.app.services.message;

public class StartSessionAcknowledgedMessage extends AcknowledgedMessage
{
	private String sessionId;
	private String apiToken;
	
	public StartSessionAcknowledgedMessage()
	{
		super(CommandType.StartSession);
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
