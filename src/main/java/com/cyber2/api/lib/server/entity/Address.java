package com.cyber2.api.lib.server.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.ToString;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author eric
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement(name = "Address")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
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
    
}
