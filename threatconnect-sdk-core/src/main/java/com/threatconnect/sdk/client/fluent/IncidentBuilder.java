package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Incident;
import com.threatconnect.sdk.server.entity.Owner;

import java.util.Date;

public class IncidentBuilder
{
    private Integer id;
    private String name;
    private String type;
    private Owner owner;
    private String ownerName;
    private Date dateAdded;
    private String webLink;
    private Date eventDate;

    public IncidentBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public IncidentBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public IncidentBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public IncidentBuilder withOwner(Owner owner)
    {
        this.owner = owner;
        return this;
    }

    public IncidentBuilder withOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return this;
    }

    public IncidentBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public IncidentBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public IncidentBuilder withEventDate(Date eventDate)
    {
        this.eventDate = eventDate;
        return this;
    }

    public Incident createIncident()
    {
        return new Incident(id, name, type, owner, ownerName, dateAdded, webLink, eventDate);
    }
}