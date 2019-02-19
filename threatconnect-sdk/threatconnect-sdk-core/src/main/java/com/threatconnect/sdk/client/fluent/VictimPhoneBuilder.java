package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.VictimPhone;

public class VictimPhoneBuilder
{
    private Long id;
    private String name;
    private String type;
    private String webLink;
    private String phoneType;

    public VictimPhoneBuilder withId(Long id)
    {
        this.id = id;
        return this;
    }

    public VictimPhoneBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public VictimPhoneBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public VictimPhoneBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public VictimPhoneBuilder withPhoneType(String phoneType)
    {
        this.phoneType = phoneType;
        return this;
    }

    public VictimPhone createVictimPhone()
    {
        return new VictimPhone(id, name, type, webLink, phoneType);
    }
}