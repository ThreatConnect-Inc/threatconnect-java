package com.threatconnect.app.services.message;

public class FireEvent extends AbstractCommandConfig
{
    private String sessionId;

    public FireEvent()
    {
        super(CommandType.FireEvent);
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }
}
