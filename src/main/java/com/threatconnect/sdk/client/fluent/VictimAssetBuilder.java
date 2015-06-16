package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.VictimAsset;

public class VictimAssetBuilder
{
    private Integer id;
    private String name;
    private String type;
    private String webLink;

    public VictimAssetBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public VictimAssetBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public VictimAssetBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public VictimAssetBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public VictimAsset createVictimAsset()
    {
        return new VictimAsset(id, name, type, webLink);
    }
}