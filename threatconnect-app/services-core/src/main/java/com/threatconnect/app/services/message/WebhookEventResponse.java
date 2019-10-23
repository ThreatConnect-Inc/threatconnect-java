package com.threatconnect.app.services.message;

import java.util.List;

public class WebhookEventResponse extends AbstractCommandConfig
{
    private String sessionId;
    private List<NameValuePair<String, String>> headers;
    private String bodyVariable;
    private int statusCode;
    
    public WebhookEventResponse()
    {
        super(CommandType.WebhookEvent);
    }
    
    public String getSessionId()
    {
        return sessionId;
    }
    
    public void setSessionId(final String sessionId)
    {
        this.sessionId = sessionId;
    }
    
    public List<NameValuePair<String, String>> getHeaders()
    {
        return headers;
    }
    
    public void setHeaders(final List<NameValuePair<String, String>> headers)
    {
        this.headers = headers;
    }
    
    public String getBodyVariable()
    {
        return bodyVariable;
    }
    
    public void setBodyVariable(final String bodyVariable)
    {
        this.bodyVariable = bodyVariable;
    }
    
    public int getStatusCode()
    {
        return statusCode;
    }
    
    public void setStatusCode(final int statusCode)
    {
        this.statusCode = statusCode;
    }
}

