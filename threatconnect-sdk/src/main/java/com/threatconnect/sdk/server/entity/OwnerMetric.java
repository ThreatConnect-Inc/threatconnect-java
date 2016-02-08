/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.format.DateSerializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 *
 * @author james
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "OwnerMetric")
public class OwnerMetric
{
    @JsonSerialize(using = DateSerializer.class)
    @XmlElement(name = "MetricDate", required = false)
    private Date metricDate;
    @XmlElement(name = "OwnerName", required = false)
    private String ownerName;
    @XmlElement(name = "TotalIndicator", required = false)
    private int totalIndicator;
    @XmlElement(name = "TotalHost", required = false)
    private int totalHost;
    @XmlElement(name = "TotalAddress", required = false)
    private int totalAddress;
    @XmlElement(name = "TotalEmailAddress", required = false)
    private int totalEmailAddress;
    @XmlElement(name = "TotalFile", required = false)
    private int totalFile;
    @XmlElement(name = "TotalUrl", required = false)
    private int totalUrl;
    @XmlElement(name = "TotalGroup", required = false)
    private int totalGroup;
    @XmlElement(name = "TotalThreat", required = false)
    private int totalThreat;
    @XmlElement(name = "TotalIncident", required = false)
    private int totalIncident;
    @XmlElement(name = "TotalEmail", required = false)
    private int totalEmail;
    @XmlElement(name = "TotalAdversary", required = false)
    private int totalAdversary;
    @XmlElement(name = "TotalSignature", required = false)
    private int totalSignature;
    @XmlElement(name = "TotalTask", required = false)
    private int totalTask;
    @XmlElement(name = "TotalDocument", required = false)
    private int totalDocument;
    @XmlElement(name = "TotalTag", required = false)
    private int totalTag;
    @XmlElement(name = "TotalTrack", required = false)
    private int totalTrack;
    @XmlElement(name = "TotalResult", required = false)
    private int totalResult;
    @XmlElement(name = "TotalIndicatorAttribute", required = false)
    private int totalIndicatorAttribute;
    @XmlElement(name = "TotalGroupAttribute", required = false)
    private int totalGroupAttribute;
    @XmlElement(name = "AverageIndicatorRating", required = false)
    private Double averageIndicatorRating;
    @XmlElement(name = "AverageIndicatorConfidence", required = false)
    private Double averageIndicatorConfidence;
    @XmlElement(name = "TotalEnrichedIndicator", required = false)
    private int totalEnrichedIndicator;
    @XmlElement(name = "TotalGroupIndicator", required = false)
    private int totalGroupIndicator;
    @XmlElement(name = "TotalObservationDaily", required = false)
    private int totalObservationDaily;
    @XmlElement(name = "TotalObservationIndicator", required = false)
    private int totalObservationIndicator;
    @XmlElement(name = "TotalObservationAddress", required = false)
    private int totalObservationAddress;
    @XmlElement(name = "TotalObservationEmailAddress", required = false)
    private int totalObservationEmailAddress;
    @XmlElement(name = "TotalObservationFile", required = false)
    private int totalObservationFile;
    @XmlElement(name = "TotalObservationHost", required = false)
    private int totalObservationHost;
    @XmlElement(name = "TotalObservationUrl", required = false)
    private int totalObservationUrl;

    public OwnerMetric()
    {
    }
    
    public Date getMetricDate()
    {
        return metricDate;
    }

    public void setMetricDate(Date metricDate)
    {
        this.metricDate = metricDate;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public int getTotalIndicator()
    {
        return totalIndicator;
    }

    public void setTotalIndicator(int totalIndicator)
    {
        this.totalIndicator = totalIndicator;
    }

    public int getTotalHost()
    {
        return totalHost;
    }

    public void setTotalHost(int totalHost)
    {
        this.totalHost = totalHost;
    }

    public int getTotalAddress()
    {
        return totalAddress;
    }

    public void setTotalAddress(int totalAddress)
    {
        this.totalAddress = totalAddress;
    }

    public int getTotalEmailAddress()
    {
        return totalEmailAddress;
    }

    public void setTotalEmailAddress(int totalEmailAddress)
    {
        this.totalEmailAddress = totalEmailAddress;
    }

    public int getTotalFile()
    {
        return totalFile;
    }

    public void setTotalFile(int totalFile)
    {
        this.totalFile = totalFile;
    }

    public int getTotalUrl()
    {
        return totalUrl;
    }

    public void setTotalUrl(int totalUrl)
    {
        this.totalUrl = totalUrl;
    }

    public int getTotalGroup()
    {
        return totalGroup;
    }

    public void setTotalGroup(int totalGroup)
    {
        this.totalGroup = totalGroup;
    }

    public int getTotalThreat()
    {
        return totalThreat;
    }

    public void setTotalThreat(int totalThreat)
    {
        this.totalThreat = totalThreat;
    }

    public int getTotalIncident()
    {
        return totalIncident;
    }

    public void setTotalIncident(int totalIncident)
    {
        this.totalIncident = totalIncident;
    }

    public int getTotalEmail()
    {
        return totalEmail;
    }

    public void setTotalEmail(int totalEmail)
    {
        this.totalEmail = totalEmail;
    }

    public int getTotalAdversary()
    {
        return totalAdversary;
    }

    public void setTotalAdversary(int totalAdversary)
    {
        this.totalAdversary = totalAdversary;
    }

    public int getTotalSignature()
    {
        return totalSignature;
    }

    public void setTotalSignature(int totalSignature)
    {
        this.totalSignature = totalSignature;
    }

    public int getTotalTask()
    {
        return totalTask;
    }

    public void setTotalTask(int totalTask)
    {
        this.totalTask = totalTask;
    }

    public int getTotalDocument()
    {
        return totalDocument;
    }

    public void setTotalDocument(int totalDocument)
    {
        this.totalDocument = totalDocument;
    }

    public int getTotalTag()
    {
        return totalTag;
    }

    public void setTotalTag(int totalTag)
    {
        this.totalTag = totalTag;
    }
    
    public int getTotalTrack()
    {
        return totalTrack;
    }

    public void setTotalTrack(int totalTrack)
    {
        this.totalTrack = totalTrack;
    }

    public int getTotalResult()
    {
        return totalResult;
    }

    public void setTotalResult(int totalResult)
    {
        this.totalResult = totalResult;
    }

    public int getTotalIndicatorAttribute()
    {
        return totalIndicatorAttribute;
    }

    public void setTotalIndicatorAttribute(int totalIndicatorAttribute)
    {
        this.totalIndicatorAttribute = totalIndicatorAttribute;
    }

    public int getTotalGroupAttribute()
    {
        return totalGroupAttribute;
    }

    public void setTotalGroupAttribute(int totalGroupAttribute)
    {
        this.totalGroupAttribute = totalGroupAttribute;
    }

    public Double getAverageIndicatorRating()
    {
        return averageIndicatorRating;
    }

    public void setAverageIndicatorRating(Double averageIndicatorRating)
    {
        this.averageIndicatorRating = averageIndicatorRating;
    }

    public Double getAverageIndicatorConfidence()
    {
        return averageIndicatorConfidence;
    }

    public void setAverageIndicatorConfidence(Double averageIndicatorConfidence)
    {
        this.averageIndicatorConfidence = averageIndicatorConfidence;
    }

    public int getTotalEnrichedIndicator()
    {
        return totalEnrichedIndicator;
    }

    public void setTotalEnrichedIndicator(int totalEnrichedIndicator)
    {
        this.totalEnrichedIndicator = totalEnrichedIndicator;
    }

    public int getTotalGroupIndicator()
    {
        return totalGroupIndicator;
    }

    public void setTotalGroupIndicator(int totalGroupIndicator)
    {
        this.totalGroupIndicator = totalGroupIndicator;
    }

    public int getTotalObservationDaily()
    {
        return totalObservationDaily;
    }

    public void setTotalObservationDaily(int totalObservationDaily)
    {
        this.totalObservationDaily = totalObservationDaily;
    }

    public int getTotalObservationIndicator()
    {
        return totalObservationIndicator;
    }

    public void setTotalObservationIndicator(int totalObservationIndicator)
    {
        this.totalObservationIndicator = totalObservationIndicator;
    }

    public int getTotalObservationAddress()
    {
        return totalObservationAddress;
    }

    public void setTotalObservationAddress(int totalObservationAddress)
    {
        this.totalObservationAddress = totalObservationAddress;
    }

    public int getTotalObservationEmailAddress()
    {
        return totalObservationEmailAddress;
    }

    public void setTotalObservationEmailAddress(int totalObservationEmailAddress)
    {
        this.totalObservationEmailAddress = totalObservationEmailAddress;
    }

    public int getTotalObservationFile()
    {
        return totalObservationFile;
    }

    public void setTotalObservationFile(int totalObservationFile)
    {
        this.totalObservationFile = totalObservationFile;
    }

    public int getTotalObservationHost()
    {
        return totalObservationHost;
    }

    public void setTotalObservationHost(int totalObservationHost)
    {
        this.totalObservationHost = totalObservationHost;
    }

    public int getTotalObservationUrl()
    {
        return totalObservationUrl;
    }

    public void setTotalObservationUrl(int totalObservationUrl)
    {
        this.totalObservationUrl = totalObservationUrl;
    }
}
