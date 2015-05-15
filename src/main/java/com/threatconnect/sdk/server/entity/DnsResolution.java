/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import com.threatconnect.sdk.server.entity.format.DateSerializer;

import java.util.Date;
import java.util.List;
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
@XmlSeeAlso({Address.class})
public class DnsResolution
{
    @JsonSerialize(using = DateSerializer.class)
    @XmlElement(name = "ResolutionDate", required = false)
    private Date resolutionDate;
    @XmlElement(name = "Addresses", required = false)
    private List<Address> addresses;

    protected DnsResolution()
    {
    }

    public Date getResolutionDate()
    {
        return resolutionDate;
    }

    public void setResolutionDate(Date resolutionDate)
    {
        this.resolutionDate = resolutionDate;
    }

    public List<Address> getAddresses()
    {
        return addresses;
    }

    public void setAddresses(List<Address> addresses)
    {
        this.addresses = addresses;
    }
    
}
