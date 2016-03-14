package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 *

 * @author eric
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement(name = "Url")
@XmlAccessorType(XmlAccessType.FIELD)
public class Url extends Indicator
{
    @XmlElement(name = "Text", required = true)
    private String text;
    
    public Url()
    {
        super();
    }

    public Url(Integer id, Owner owner, String ownerName, String type, Date dateAdded, Date lastModified, Double rating, Double confidence, Double threatAssessRating, Double threatAssessConfidence, String webLink, String source, String description, String summary, String text)
    {
        super(id, owner, ownerName, type, dateAdded, lastModified, rating, confidence, threatAssessRating, threatAssessConfidence, webLink, source, description, summary);
        this.text = text;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
    
}
