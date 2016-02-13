package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.format.DateTimeSerializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * 
 * @author mangelo
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "FalsePositive")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FalsePositive
{
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Count", required = false)
    private Integer count;
    
    @JsonSerialize(using = DateTimeSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "LastReported", required=false)
    private Date lastReported;

    public FalsePositive()
    {
        this.count = 0;
    }
    
    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public Date getLastReported()
    {
        return lastReported;
    }

    public void setLastReported(Date lastReported)
    {
        this.lastReported = lastReported;
    }


    @Override
    public String toString()
    {
        return "FalsePositive{" +
                "count=" + count +
                ", lastReported=" + lastReported +
                '}';
    }
}
