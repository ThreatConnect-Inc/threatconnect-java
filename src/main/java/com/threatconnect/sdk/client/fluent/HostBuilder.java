package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.entity.Owner;

import java.util.Date;

public class HostBuilder
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
    private String hostName;
    private String dnsActive;
    private String whoisActive;

    public HostBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public HostBuilder withOwner(Owner owner)
    {
        this.owner = owner;
        return this;
    }

    public HostBuilder withOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return this;
    }

    public HostBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public HostBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public HostBuilder withLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
        return this;
    }

    public HostBuilder withRating(Double rating)
    {
        this.rating = rating;
        return this;
    }

    public HostBuilder withConfidence(Double confidence)
    {
        this.confidence = confidence;
        return this;
    }

    public HostBuilder withThreatAssessRating(Double threatAssessRating)
    {
        this.threatAssessRating = threatAssessRating;
        return this;
    }

    public HostBuilder withThreatAssessConfidence(Double threatAssessConfidence)
    {
        this.threatAssessConfidence = threatAssessConfidence;
        return this;
    }

    public HostBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public HostBuilder withSource(String source)
    {
        this.source = source;
        return this;
    }

    public HostBuilder withDescription(String description)
    {
        this.description = description;
        return this;
    }

    public HostBuilder withSummary(String summary)
    {
        this.summary = summary;
        return this;
    }

    public HostBuilder withHostName(String hostName)
    {
        this.hostName = hostName;
        return this;
    }

    public HostBuilder withDnsActive(String dnsActive)
    {
        this.dnsActive = dnsActive;
        return this;
    }

    public HostBuilder withWhoisActive(String whoisActive)
    {
        this.whoisActive = whoisActive;
        return this;
    }

    public Host createHost()
    {
        return new Host(id, owner, ownerName, type, dateAdded, lastModified, rating, confidence, threatAssessRating, threatAssessConfidence, webLink, source, description, summary, hostName, dnsActive, whoisActive);
    }
}