package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Owner;
import com.threatconnect.sdk.server.entity.Url;

import java.util.Date;

public class UrlBuilder
{
    private Integer id;
    private Owner owner;
    private String ownerName;
    private String type;
    private Date dateAdded;
    private Date lastModified;
    private Double rating;
    private Double confidence;
    private Double threatAssessRating;
    private Double threatAssessConfidence;
    private String webLink;
    private String source;
    private String description;
    private String summary;
    private String text;

    public UrlBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public UrlBuilder withOwner(Owner owner)
    {
        this.owner = owner;
        return this;
    }

    public UrlBuilder withOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return this;
    }

    public UrlBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public UrlBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public UrlBuilder withLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
        return this;
    }

    public UrlBuilder withRating(Double rating)
    {
        this.rating = rating;
        return this;
    }

    public UrlBuilder withConfidence(Double confidence)
    {
        this.confidence = confidence;
        return this;
    }

    public UrlBuilder withThreatAssessRating(Double threatAssessRating)
    {
        this.threatAssessRating = threatAssessRating;
        return this;
    }

    public UrlBuilder withThreatAssessConfidence(Double threatAssessConfidence)
    {
        this.threatAssessConfidence = threatAssessConfidence;
        return this;
    }

    public UrlBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public UrlBuilder withSource(String source)
    {
        this.source = source;
        return this;
    }

    public UrlBuilder withDescription(String description)
    {
        this.description = description;
        return this;
    }

    public UrlBuilder withSummary(String summary)
    {
        this.summary = summary;
        return this;
    }

    public UrlBuilder withText(String text)
    {
        this.text = text;
        return this;
    }

    public Url createUrl()
    {
        return new Url(id, owner, ownerName, type, dateAdded, lastModified, rating, confidence, threatAssessRating, threatAssessConfidence, webLink, source, description, summary, text);
    }
}