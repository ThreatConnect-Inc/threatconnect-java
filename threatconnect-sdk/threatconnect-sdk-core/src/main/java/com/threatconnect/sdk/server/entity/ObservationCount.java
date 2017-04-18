/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.format.DateTimeSerializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 *
 * @author mjimenez
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ObservationCount")
public class ObservationCount
{
    @XmlElement(name = "Count", required = true)
    private Integer count;
    @JsonSerialize(using = DateTimeSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "lastObserved", required = false)
    private Date lastObserved;
    @XmlElement(name = "YourCount", required = true)
    private Integer yourCount;
    @JsonSerialize(using = DateTimeSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "YourLastObserved", required = false)
    private Date yourLastObserved;

    public ObservationCount()
    {
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public Date getLastObserved()
    {
        return lastObserved;
    }

    public void setLastObserved(Date lastObserved)
    {
        this.lastObserved = lastObserved;
    }

    public Integer getYourCount()
    {
        return yourCount;
    }

    public void setYourCount(Integer yourCount)
    {
        this.yourCount = yourCount;
    }

    public Date getYourLastObserved()
    {
        return yourLastObserved;
    }

    public void setYourLastObserved(Date yourLastObserved)
    {
        this.yourLastObserved = yourLastObserved;
    }
}
