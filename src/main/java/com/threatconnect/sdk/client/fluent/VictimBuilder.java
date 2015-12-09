package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Victim;

public class VictimBuilder
{
    private Integer id;
    private String name;
    private String description;
    private String org;
    private String suborg;
    private String workLocation;
    private String nationality;
    private String webLink;

    public VictimBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public VictimBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public VictimBuilder withDescription(String description)
    {
        this.description = description;
        return this;
    }

    public VictimBuilder withOrg(String org)
    {
        this.org = org;
        return this;
    }

    public VictimBuilder withSuborg(String suborg)
    {
        this.suborg = suborg;
        return this;
    }

    public VictimBuilder withWorkLocation(String workLocation)
    {
        this.workLocation = workLocation;
        return this;
    }

    public VictimBuilder withNationality(String nationality)
    {
        this.nationality = nationality;
        return this;
    }

    public VictimBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public Victim createVictim()
    {
        return new Victim(id, name, description, org, suborg, workLocation, nationality, webLink);
    }
}