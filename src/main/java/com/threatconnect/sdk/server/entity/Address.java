package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author eric
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement(name = "Address")
@XmlAccessorType(XmlAccessType.FIELD)
public class Address extends Indicator
{
    @XmlElement(name = "Address", required = true)
    private String ip;
    
    public Address()
    {
        super();
    }
    
    /**
     * @return the ip
     */
    public String getIp()
    {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip)
    {
        this.ip = ip;
    }

    @Override
    public String toString()
    {
        return "Address{" +
                "ip='" + ip + '\'' +
                '}';
    }
}
