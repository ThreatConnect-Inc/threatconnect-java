package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Community;

public class CommunityBuilder
{
    private Integer id;
    private String name;
    private String type;

    public CommunityBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public CommunityBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public CommunityBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public Community createCommunity()
    {
        return new Community(id, name, type);
    }
}