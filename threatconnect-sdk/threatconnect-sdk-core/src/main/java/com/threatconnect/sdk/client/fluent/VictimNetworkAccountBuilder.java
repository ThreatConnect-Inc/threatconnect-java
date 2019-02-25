package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.VictimNetworkAccount;

public class VictimNetworkAccountBuilder
{
    private Long id;
    private String name;
    private String type;
    private String webLink;
    private String account;
    private String network;

    public VictimNetworkAccountBuilder withId(Long id)
    {
        this.id = id;
        return this;
    }

    public VictimNetworkAccountBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public VictimNetworkAccountBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public VictimNetworkAccountBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public VictimNetworkAccountBuilder withAccount(String account)
    {
        this.account = account;
        return this;
    }

    public VictimNetworkAccountBuilder withNetwork(String network)
    {
        this.network = network;
        return this;
    }

    public VictimNetworkAccount createVictimNetworkAccount()
    {
        return new VictimNetworkAccount(id, name, type, webLink, account, network);
    }
}