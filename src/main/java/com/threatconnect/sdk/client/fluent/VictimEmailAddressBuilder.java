package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.VictimEmailAddress;

public class VictimEmailAddressBuilder
{
    private Integer id;
    private String name;
    private String type;
    private String webLink;
    private String address;
    private String addressType;

    public VictimEmailAddressBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public VictimEmailAddressBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public VictimEmailAddressBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public VictimEmailAddressBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public VictimEmailAddressBuilder withAddress(String address)
    {
        this.address = address;
        return this;
    }

    public VictimEmailAddressBuilder withAddressType(String addressType)
    {
        this.addressType = addressType;
        return this;
    }

    public VictimEmailAddress createVictimEmailAddress()
    {
        return new VictimEmailAddress(id, name, type, webLink, address, addressType);
    }
}