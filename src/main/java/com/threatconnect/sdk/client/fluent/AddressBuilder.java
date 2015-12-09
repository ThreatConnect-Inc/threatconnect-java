package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.Owner;

import java.util.Date;

public class AddressBuilder
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
    private String ip;

    public AddressBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public AddressBuilder withOwner(Owner owner)
    {
        this.owner = owner;
        return this;
    }

    public AddressBuilder withOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return this;
    }

    public AddressBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public AddressBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public AddressBuilder withLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
        return this;
    }

    public AddressBuilder withRating(Double rating)
    {
        this.rating = rating;
        return this;
    }

    public AddressBuilder withConfidence(Double confidence)
    {
        this.confidence = confidence;
        return this;
    }

    public AddressBuilder withThreatAssessRating(Double threatAssessRating)
    {
        this.threatAssessRating = threatAssessRating;
        return this;
    }

    public AddressBuilder withThreatAssessConfidence(Double threatAssessConfidence)
    {
        this.threatAssessConfidence = threatAssessConfidence;
        return this;
    }

    public AddressBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public AddressBuilder withSource(String source)
    {
        this.source = source;
        return this;
    }

    public AddressBuilder withDescription(String description)
    {
        this.description = description;
        return this;
    }

    public AddressBuilder withSummary(String summary)
    {
        this.summary = summary;
        return this;
    }

    public AddressBuilder withIp(String ip)
    {
        this.ip = ip;
        return this;
    }

    public Address createAddress()
    {
        return new Address(id, owner, ownerName, type, dateAdded, lastModified, rating, confidence, threatAssessRating, threatAssessConfidence, webLink, source, description, summary, ip);
    }
}