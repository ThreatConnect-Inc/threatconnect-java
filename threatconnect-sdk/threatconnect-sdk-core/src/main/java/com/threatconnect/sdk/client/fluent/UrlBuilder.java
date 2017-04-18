package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Url;

public class UrlBuilder extends AbstractIndicatorBuilder<UrlBuilder>
{
	private String text;
	
	public UrlBuilder withText(String text)
	{
		this.text = text;
		return this;
	}
	
	public Url createUrl()
	{
		return new Url(id, owner, ownerName, type, dateAdded, lastModified, rating, confidence, threatAssessRating,
			threatAssessConfidence, webLink, source, description, summary, text);
	}
}