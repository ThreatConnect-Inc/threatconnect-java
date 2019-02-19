package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Owner;

import java.util.Date;

public class AbstractIndicatorBuilder<T extends AbstractIndicatorBuilder<T>>
{
    protected Long id;
    protected Owner owner;
    protected String ownerName;
    protected String type;
    protected Date dateAdded;
    protected Date lastModified;
    protected Double rating;
    protected Double confidence;
    protected Double threatAssessRating;
    protected Double threatAssessConfidence;
    protected String webLink;
    protected String source;
    protected String description;
    protected String summary;

    public T withId(Long id)
    {
        this.id = id;
        return getThis();
    }

    public T withOwner(Owner owner)
    {
        this.owner = owner;
        return getThis();
    }

    public T withOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return getThis();
    }

    public T withType(String type)
    {
        this.type = type;
        return getThis();
    }

    public T withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return getThis();
    }

    public T withLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
        return getThis();
    }

    public T withRating(Double rating)
    {
        this.rating = rating;
        return getThis();
    }

    public T withConfidence(Double confidence)
    {
        this.confidence = confidence;
        return getThis();
    }

    public T withThreatAssessRating(Double threatAssessRating)
    {
        this.threatAssessRating = threatAssessRating;
        return getThis();
    }

    public T withThreatAssessConfidence(Double threatAssessConfidence)
    {
        this.threatAssessConfidence = threatAssessConfidence;
        return getThis();
    }

    public T withWebLink(String webLink)
    {
        this.webLink = webLink;
        return getThis();
    }

    public T withSource(String source)
    {
        this.source = source;
        return getThis();
    }

    public T withDescription(String description)
    {
        this.description = description;
        return getThis();
    }

    public T withSummary(String summary)
    {
        this.summary = summary;
        return getThis();
    }
    
    private T getThis()
    {
        return (T) this;
    }
}