package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Source;

public class SourceBuilder
{
    private Long id;
    private String name;
    private String type;

    public SourceBuilder withId(Long id)
    {
        this.id = id;
        return this;
    }

    public SourceBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public SourceBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public Source createSource()
    {
        return new Source(id, name, type);
    }
}