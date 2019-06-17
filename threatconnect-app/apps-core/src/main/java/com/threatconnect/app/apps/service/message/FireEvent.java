package com.threatconnect.app.apps.service.message;

public class FireEvent extends AbstractCommandConfig
{
    private String sessionId;

    public FireEvent()
    {
        super(Command.FireEvent);
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
