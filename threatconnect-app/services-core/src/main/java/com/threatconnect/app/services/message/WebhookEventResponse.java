package com.threatconnect.app.services.message;

import java.util.List;

public class WebhookEventResponse extends AbstractCommandConfig
{
    private String method;
    private List<NameValuePair<String, String>> headers;
    private List<NameValuePair<String, String>> queryParams;
    private String bodyVariable;
    
    public WebhookEventResponse()
    {
        super(CommandType.WebhookEvent);
    }
    
    public String getMethod()
    {
        return method;
    }
    
    public void setMethod(final String method)
    {
        this.method = method;
    }
    
    public List<NameValuePair<String, String>> getHeaders()
    {
        return headers;
    }
    
    public void setHeaders(final List<NameValuePair<String, String>> headers)
    {
        this.headers = headers;
    }
    
    public List<NameValuePair<String, String>> getQueryParams()
    {
        return queryParams;
    }
    
    public void setQueryParams(final List<NameValuePair<String, String>> queryParams)
    {
        this.queryParams = queryParams;
    }
    
    public String getBodyVariable()
    {
        return bodyVariable;
    }
    
    public void setBodyVariable(final String bodyVariable)
    {
        this.bodyVariable = bodyVariable;
    }
}

