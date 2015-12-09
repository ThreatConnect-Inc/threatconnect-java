package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.entity.Owner;

import java.util.Date;

public class FileBuilder
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
    private String md5;
    private String sha1;
    private String sha256;
    private Integer size;

    public FileBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public FileBuilder withOwner(Owner owner)
    {
        this.owner = owner;
        return this;
    }

    public FileBuilder withOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return this;
    }

    public FileBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public FileBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public FileBuilder withLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
        return this;
    }

    public FileBuilder withRating(Double rating)
    {
        this.rating = rating;
        return this;
    }

    public FileBuilder withConfidence(Double confidence)
    {
        this.confidence = confidence;
        return this;
    }

    public FileBuilder withThreatAssessRating(Double threatAssessRating)
    {
        this.threatAssessRating = threatAssessRating;
        return this;
    }

    public FileBuilder withThreatAssessConfidence(Double threatAssessConfidence)
    {
        this.threatAssessConfidence = threatAssessConfidence;
        return this;
    }

    public FileBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public FileBuilder withSource(String source)
    {
        this.source = source;
        return this;
    }

    public FileBuilder withDescription(String description)
    {
        this.description = description;
        return this;
    }

    public FileBuilder withSummary(String summary)
    {
        this.summary = summary;
        return this;
    }

    public FileBuilder withMd5(String md5)
    {
        this.md5 = md5;
        return this;
    }

    public FileBuilder withSha1(String sha1)
    {
        this.sha1 = sha1;
        return this;
    }

    public FileBuilder withSha256(String sha256)
    {
        this.sha256 = sha256;
        return this;
    }

    public FileBuilder withSize(Integer size)
    {
        this.size = size;
        return this;
    }

    public File createFile()
    {
        return new File(id, owner, ownerName, type, dateAdded, lastModified, rating, confidence, threatAssessRating, threatAssessConfidence, webLink, source, description, summary, md5, sha1, sha256, size);
    }
}