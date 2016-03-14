/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity.init;

/**
 *
 * @author James
 */
public abstract class ApiEntityInit
{
    private boolean detailed = false;
    private boolean generic = false;
    private String systemUrl = "";

    protected ApiEntityInit(boolean detailed, boolean generic, String systemUrl)
    {
        this.generic = generic;
        this.detailed = detailed;
        this.systemUrl = systemUrl;
    }

    protected ApiEntityInit(boolean detailed, String systemUrl)
    {
        this.detailed = detailed;
        this.systemUrl = systemUrl;
    }

    protected ApiEntityInit(String systemUrl)
    {
        this(false, systemUrl);
    }

    public boolean isDetailed()
    {
        return detailed;
    }

    public boolean isGeneric()
    {
        return generic;
    }

    public String getSystemUrl()
    {
        return systemUrl;
    }
}
