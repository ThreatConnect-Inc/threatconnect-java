package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Adversary;
import com.threatconnect.sdk.server.entity.Owner;

import java.util.Date;

public class AdversaryBuilder
{
    private Integer id;
    private String name;
    private String type;
    private Owner owner;
    private String ownerName;
    private Date dateAdded;
    private String webLink;

    public AdversaryBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public AdversaryBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public AdversaryBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public AdversaryBuilder withOwner(Owner owner)
    {
        this.owner = owner;
        return this;
    }

    public AdversaryBuilder withOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return this;
    }

    public AdversaryBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public AdversaryBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public Adversary createAdversary()
    {
        return new Adversary(id, name, type, owner, ownerName, dateAdded, webLink);
    }
}