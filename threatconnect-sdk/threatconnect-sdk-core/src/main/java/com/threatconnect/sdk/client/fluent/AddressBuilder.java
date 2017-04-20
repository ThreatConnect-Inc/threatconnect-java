package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Address;

public class AddressBuilder extends AbstractIndicatorBuilder<AddressBuilder>
{
	private String ip;
	
	public AddressBuilder withIp(String ip)
	{
		this.ip = ip;
		return this;
	}
	
	public Address createAddress()
	{
		return new Address(id, owner, ownerName, type, dateAdded, lastModified, rating, confidence, threatAssessRating,
			threatAssessConfidence, webLink, source, description, summary, ip);
	}
}