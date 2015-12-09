package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.VictimWebSite;

public class VictimWebSiteBuilder
{
    private Integer id;
    private String name;
    private String type;
    private String webLink;
    private String webSite;

    public VictimWebSiteBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public VictimWebSiteBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public VictimWebSiteBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public VictimWebSiteBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public VictimWebSiteBuilder withWebSite(String webSite)
    {
        this.webSite = webSite;
        return this;
    }

    public VictimWebSite createVictimWebSite()
    {
        return new VictimWebSite(id, name, type, webLink, webSite);
    }
}