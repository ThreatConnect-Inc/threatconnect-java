package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.VictimSocialNetwork;

public class VictimSocialNetworkBuilder
{
    private Integer id;
    private String name;
    private String type;
    private String webLink;
    private String account;
    private String network;

    public VictimSocialNetworkBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public VictimSocialNetworkBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public VictimSocialNetworkBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public VictimSocialNetworkBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public VictimSocialNetworkBuilder withAccount(String account)
    {
        this.account = account;
        return this;
    }

    public VictimSocialNetworkBuilder withNetwork(String network)
    {
        this.network = network;
        return this;
    }

    public VictimSocialNetwork createVictimSocialNetwork()
    {
        return new VictimSocialNetwork(id, name, type, webLink, account, network);
    }
}