package com.threatconnect.app.apps.service.message;

public class Launch extends CommandMessage
{
    private Long appId;

    public Launch()
    {
        super(Command.Launch);
    }

    public Long getAppId()
    {
        return appId;
    }

    public void setAppId(Long appId)
    {
        this.appId = appId;
    }
}
