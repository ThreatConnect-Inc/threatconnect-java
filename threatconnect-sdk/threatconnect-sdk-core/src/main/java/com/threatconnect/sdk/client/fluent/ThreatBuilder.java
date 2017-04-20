package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Threat;

public class ThreatBuilder extends AbstractGroupBuilder<ThreatBuilder>
{
    public Threat createThreat()
    {
        return new Threat(id, name, type, owner, ownerName, dateAdded, webLink);
    }
}