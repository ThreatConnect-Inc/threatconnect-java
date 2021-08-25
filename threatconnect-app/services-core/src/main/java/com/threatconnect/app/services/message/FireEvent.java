package com.threatconnect.app.services.message;

public class FireEvent extends AbstractCommandConfig
{
    private String sessionId;
    private String contextId;

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

    public String getContextId()
    {
        return contextId;
    }

    public void setContextId(String contextId)
    {
        this.contextId = contextId;
    }
}
