package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Owner;

import java.util.Date;

public class GroupBuilder
{
    private Integer id;
    private String name;
    private String type;
    private Owner owner;
    private String ownerName;
    private Date dateAdded;
    private String webLink;

    public GroupBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public GroupBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public GroupBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public GroupBuilder withOwner(Owner owner)
    {
        this.owner = owner;
        return this;
    }

    public GroupBuilder withOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return this;
    }

    public GroupBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public GroupBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public Group createGroup()
    {
        return new Group(id, name, type, owner, ownerName, dateAdded, webLink);
    }
}