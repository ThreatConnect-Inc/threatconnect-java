package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.EmailAddress;
import com.threatconnect.sdk.server.entity.Owner;

import java.util.Date;

public class EmailAddressBuilder
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
    private String address;

    public EmailAddressBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public EmailAddressBuilder withOwner(Owner owner)
    {
        this.owner = owner;
        return this;
    }

    public EmailAddressBuilder withOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return this;
    }

    public EmailAddressBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public EmailAddressBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public EmailAddressBuilder withLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
        return this;
    }

    public EmailAddressBuilder withRating(Double rating)
    {
        this.rating = rating;
        return this;
    }

    public EmailAddressBuilder withConfidence(Double confidence)
    {
        this.confidence = confidence;
        return this;
    }

    public EmailAddressBuilder withThreatAssessRating(Double threatAssessRating)
    {
        this.threatAssessRating = threatAssessRating;
        return this;
    }

    public EmailAddressBuilder withThreatAssessConfidence(Double threatAssessConfidence)
    {
        this.threatAssessConfidence = threatAssessConfidence;
        return this;
    }

    public EmailAddressBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public EmailAddressBuilder withSource(String source)
    {
        this.source = source;
        return this;
    }

    public EmailAddressBuilder withDescription(String description)
    {
        this.description = description;
        return this;
    }

    public EmailAddressBuilder withSummary(String summary)
    {
        this.summary = summary;
        return this;
    }

    public EmailAddressBuilder withAddress(String address)
    {
        this.address = address;
        return this;
    }

    public EmailAddress createEmailAddress()
    {
        return new EmailAddress(id, owner, ownerName, type, dateAdded, lastModified, rating, confidence, threatAssessRating, threatAssessConfidence, webLink, source, description, summary, address);
    }
}