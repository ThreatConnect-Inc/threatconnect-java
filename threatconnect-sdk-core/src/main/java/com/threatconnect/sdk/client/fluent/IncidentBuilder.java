package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Incident;

import java.util.Date;

public class IncidentBuilder extends AbstractGroupBuilder<IncidentBuilder>
{
    private Date eventDate;

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