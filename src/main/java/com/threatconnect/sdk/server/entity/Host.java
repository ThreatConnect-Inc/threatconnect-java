/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 *
 * @author James
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement(name = "Host")
@XmlAccessorType(XmlAccessType.FIELD)
public class Host extends Indicator
{
    @XmlElement(name = "HostName", required = true)
    private String hostName;
    @XmlElement(name = "DnsActive", required = false)
    private String dnsActive = null;
    @XmlElement(name = "WhoisActive", required = false)
    private String whoisActive = null;

    public Host()
    {
        super();
    }

    public Host(Integer id, Owner owner, String ownerName, String type, Date dateAdded, Date lastModified, Double rating, Double confidence, Double threatAssessRating, Double threatAssessConfidence, String webLink, String source, String description, String summary, String hostName, String dnsActive, String whoisActive)
    {
        super(id, owner, ownerName, type, dateAdded, lastModified, rating, confidence, threatAssessRating, threatAssessConfidence, webLink, source, description, summary);
        this.hostName = hostName;
        this.dnsActive = dnsActive;
        this.whoisActive = whoisActive;
    }

    public String getHostName()
    {
        return hostName;
    }

    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }

    public String getDnsActive()
    {
        return dnsActive;
    }

    public void setDnsActive(String dnsActive)
    {
        this.dnsActive = dnsActive;
    }

    public String getWhoisActive()
    {
        return whoisActive;
    }

    public void setWhoisActive(String whoisActive)
    {
        this.whoisActive = whoisActive;
    }
    
}
