package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Owner;
import com.threatconnect.sdk.server.entity.Threat;

import java.util.Date;

public class ThreatBuilder
{
    private Integer id;
    private String name;
    private String type;
    private Owner owner;
    private String ownerName;
    private Date dateAdded;
    private String webLink;

    public ThreatBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public ThreatBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public ThreatBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public ThreatBuilder withOwner(Owner owner)
    {
        this.owner = owner;
        return this;
    }

    public ThreatBuilder withOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return this;
    }

    public ThreatBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public ThreatBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public Threat createThreat()
    {
        return new Threat(id, name, type, owner, ownerName, dateAdded, webLink);
    }
}