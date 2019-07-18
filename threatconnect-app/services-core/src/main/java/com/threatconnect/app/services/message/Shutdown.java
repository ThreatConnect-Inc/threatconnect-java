package com.threatconnect.app.services.message;

public class Shutdown extends AbstractCommandConfig
{
    private String sessionId;
    private String reason;

    public Shutdown()
    {
        super(CommandType.Shutdown);
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }
    
    public String getReason()
    {
        return reason;
    }
    
    public void setReason(final String reason)
    {
        this.reason = reason;
    }
}

