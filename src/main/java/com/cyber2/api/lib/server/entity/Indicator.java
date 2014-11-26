/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.entity;

import com.cyber2.api.lib.server.entity.format.DateSerializer;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author James
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
        {
            Address.class, EmailAddress.class, File.class, Host.class, Url.class
        })
public class Indicator
{

    public static enum Type {
         Address
       , EmailAddress
       , File
       , Host
       , Url
    }

    @XmlElement(name = "Id", required = false)
    private Integer id;
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
    private BigDecimal rating;
    @XmlElement(name = "Confidence", required = false)
    private Short confidence;
    @XmlElement(name = "ThreatAssessRating", required = false)
    private Double threatAssessRating;
    @XmlElement(name = "ThreatAssessConfidence", required = false)
    private Double threatAssessConfidence;
    @XmlElement(name = "WebLink", required = false)
    private String webLink;
    @XmlElement(name = "Source", required = false)
    private String source;
    @XmlElement(name = "Description", required = false)
    private String description;
    @XmlElement(name = "Summary", required = false)
    private String summary;

    public Indicator()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public Owner getOwner()
    {
        return owner;
    }

    public void setOwner(Owner owner)
    {
        this.owner = owner;
    }

    public String getType()
    {
        return type;
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

    public BigDecimal getRating()
    {
        return rating;
    }

    public void setRating(BigDecimal rating)
    {
        this.rating = rating;
    }

    public Short getConfidence()
    {
        return confidence;
    }

    public void setConfidence(Short confidence)
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

}
