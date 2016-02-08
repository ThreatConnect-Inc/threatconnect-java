package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.SecurityLabel;

import java.util.Date;

public class SecurityLabelBuilder
{
    private String name;
    private String description;
    private Date dateAdded;

    public SecurityLabelBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public SecurityLabelBuilder withDescription(String description)
    {
        this.description = description;
        return this;
    }

    public SecurityLabelBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public SecurityLabel createSecurityLabel()
    {
        return new SecurityLabel(name, description, dateAdded);
    }
}