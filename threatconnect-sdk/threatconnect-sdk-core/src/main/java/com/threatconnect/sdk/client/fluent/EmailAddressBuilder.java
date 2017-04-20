package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.EmailAddress;

public class EmailAddressBuilder extends AbstractIndicatorBuilder<EmailAddressBuilder>
{
	private String address;
	
	public EmailAddressBuilder withAddress(String address)
	{
		this.address = address;
		return this;
	}
	
	public EmailAddress createEmailAddress()
	{
		return new EmailAddress(id, owner, ownerName, type, dateAdded, lastModified, rating, confidence,
			threatAssessRating, threatAssessConfidence, webLink, source, description, summary, address);
	}
}