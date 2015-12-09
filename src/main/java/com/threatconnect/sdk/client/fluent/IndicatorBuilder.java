package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.entity.Owner;

import java.util.Date;

public class IndicatorBuilder
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

    public IndicatorBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public IndicatorBuilder withOwner(Owner owner)
    {
        this.owner = owner;
        return this;
    }

    public IndicatorBuilder withOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return this;
    }

    public IndicatorBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public IndicatorBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public IndicatorBuilder withLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
        return this;
    }

    public IndicatorBuilder withRating(Double rating)
    {
        this.rating = rating;
        return this;
    }

    public IndicatorBuilder withConfidence(Double confidence)
    {
        this.confidence = confidence;
        return this;
    }

    public IndicatorBuilder withThreatAssessRating(Double threatAssessRating)
    {
        this.threatAssessRating = threatAssessRating;
        return this;
    }

    public IndicatorBuilder withThreatAssessConfidence(Double threatAssessConfidence)
    {
        this.threatAssessConfidence = threatAssessConfidence;
        return this;
    }

    public IndicatorBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public IndicatorBuilder withSource(String source)
    {
        this.source = source;
        return this;
    }

    public IndicatorBuilder withDescription(String description)
    {
        this.description = description;
        return this;
    }

    public IndicatorBuilder withSummary(String summary)
    {
        this.summary = summary;
        return this;
    }

    public Indicator createIndicator()
    {
        return new Indicator(id, owner, ownerName, type, dateAdded, lastModified, rating, confidence, threatAssessRating, threatAssessConfidence, webLink, source, description, summary);
    }
}