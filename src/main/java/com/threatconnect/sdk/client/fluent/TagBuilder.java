package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Tag;

public class TagBuilder
{
    private String name;
    private String description;
    private String webLink;

    public TagBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public TagBuilder withDescription(String description)
    {
        this.description = description;
        return this;
    }

    public TagBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public Tag createTag()
    {
        return new Tag(name, description, webLink);
    }
}