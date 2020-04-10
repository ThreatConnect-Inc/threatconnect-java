/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.format.DateSerializer;
import com.threatconnect.sdk.server.entity.format.DateTimeSerializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;


/**
 * @author James
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
        {
                Address.class, EmailAddress.class, File.class, Host.class, Url.class
        })
public class Indicator implements AttributeHolder
{

    public Indicator(Long id, Owner owner, String ownerName, String type, Date dateAdded, Date lastModified, Double rating, Double confidence, Double threatAssessRating, Double threatAssessConfidence, String webLink, String source, String description, String summary)
    {
        this.id = id;
        this.owner = owner;
        this.ownerName = ownerName;
        this.type = type;
        this.dateAdded = dateAdded;
        this.lastModified = lastModified;
        this.rating = rating;
        this.confidence = confidence;
        this.threatAssessRating = threatAssessRating;
        this.threatAssessConfidence = threatAssessConfidence;
        this.webLink = webLink;
        this.source = source;
        this.description = description;
        this.summary = summary;
    }



    public static enum Type
    {
        Address, EmailAddress, File, Host, Url
    }

    @XmlElement(name = "Id", required = false)
    private Long id;
    @XmlElement(name = "Owner", required = false)
    private Owner owner;
    @XmlElement(name = "OwnerName", required = false)
    private String ownerName;
    @XmlElement(name = "Type", required = false)
    private String type;
    @JsonSerialize(using = DateSerializer.class)
    @XmlElement(name = "DateAdded", required = false)
    private Date dateAdded;
    @JsonSerialize(using = DateSerializer.class)
    @XmlElement(name = "LastModified", required = false)
    private Date lastModified;
    @XmlElement(name = "Rating", required = false)
    private Double rating;
    @XmlElement(name = "Confidence", required = false)
    private Double confidence;
    @XmlElement(name = "ThreatAssessRating", required = false)
    private Double threatAssessRating;
    @XmlElement(name = "ThreatAssessConfidence", required = false)
    private Double threatAssessConfidence;
    @XmlElement(name = "ThreatAssessScore", required = false)
    private Integer threatAssessScore;
    @XmlElement(name = "WebLink", required = false)
    private String webLink;
    @XmlElement(name = "Source", required = false)
    private String source;
    @XmlElement(name = "Description", required = false)
    private String description;
    @XmlElement(name = "Summary", required = false)
    private String summary;

   /*
    @XmlElement(name = "Attributes", required = false)
    private List<Attribute> attribute = null;
    */

    @XmlElement(name = "observationCount", required = false)
    private Integer observationCount = null;

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "falsePositiveCount", required = false)
    private Integer falsePositiveCount;

    @JsonSerialize(using = DateTimeSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "falsePositiveLastReported", required = false)
    private Date falsePositiveLastReported;


    private Map<String, String> attributes = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public Indicator()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @JsonIgnore
    public Owner getOwner()
    {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(Owner owner)
    {
        this.owner = owner;
    }

    public String getOwnerName()
    {
        return (ownerName == null && this.owner != null) ? owner.getName() : ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getType()
    {
        return type == null ? getClass().getSimpleName().toLowerCase() : type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Date getDateAdded()
    {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
    }

    public Date getLastModified()
    {
        return lastModified;
    }

    public void setLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
    }

    public Double getRating()
    {
        return rating;
    }

    public void setRating(Double rating)
    {
        this.rating = rating;
    }

    public Double getConfidence()
    {
        return confidence;
    }

    public void setConfidence(Double confidence)
    {
        this.confidence = confidence;
    }

    public Double getThreatAssessRating()
    {
        return threatAssessRating;
    }

    public void setThreatAssessRating(Double threatAssessRating)
    {
        this.threatAssessRating = threatAssessRating;
    }

    public Double getThreatAssessConfidence()
    {
        return threatAssessConfidence;
    }

    public void setThreatAssessConfidence(Double threatAssessConfidence)
    {
        this.threatAssessConfidence = threatAssessConfidence;
    }
    
    public Integer getThreatAssessScore()
    {
        return threatAssessScore;
    }
    
    public void setThreatAssessScore(final Integer threatAssessScore)
    {
        this.threatAssessScore = threatAssessScore;
    }
    
    public String getWebLink()
    {
        return webLink;
    }

    public void setWebLink(String webLink)
    {
        this.webLink = webLink;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public Integer getObservationCount()
    {
        return observationCount;
    }

    public void setObservationCount(Integer observationCount)
    {
        this.observationCount = observationCount;
    }

    public Integer getFalsePositiveCount()
    {
        return falsePositiveCount;
    }

    public void setFalsePositiveCount(Integer falsePositiveCount)
    {
        this.falsePositiveCount = falsePositiveCount;
    }

    public Date getFalsePositiveLastReported()
    {
        return falsePositiveLastReported;
    }

    public void setFalsePositiveLastReported(Date falsePositiveLastReported)
    {
        this.falsePositiveLastReported = falsePositiveLastReported;
    }

    @Override
    @JsonIgnore
    public Map<String, String> getAttributes()
    {
        return attributes;
    }
}
