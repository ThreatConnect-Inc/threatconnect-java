package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Adversary;

public class AdversaryBuilder extends AbstractGroupBuilder<AdversaryBuilder>
{
	public Adversary createAdversary()
	{
		return new Adversary(id, name, type, owner, ownerName, dateAdded, webLink);
	}
}