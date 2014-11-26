/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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
