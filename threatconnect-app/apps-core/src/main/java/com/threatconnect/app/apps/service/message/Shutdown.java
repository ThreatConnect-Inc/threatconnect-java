package com.threatconnect.app.apps.service.message;

public class Shutdown extends AbstractCommandConfig
{
    private String sessionId;

    public Shutdown()
    {
        super(Command.Shutdown);
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

