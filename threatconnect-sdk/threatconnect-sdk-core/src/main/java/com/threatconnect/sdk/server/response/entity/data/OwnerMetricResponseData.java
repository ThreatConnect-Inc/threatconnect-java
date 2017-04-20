/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.OwnerMetric;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OwnerMetricResponseData extends ApiEntitySingleResponseData<OwnerMetric>
{
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "OwnerMetric", required = false)
    private OwnerMetric ownerMetric;
    
    public OwnerMetricResponseData(OwnerMetric ownerMetric)
    {
        this.ownerMetric = ownerMetric;
    }

    public OwnerMetric getOwnerMetric()
    {
        return ownerMetric;
    }

    public void setOwnerMetric(OwnerMetric ownerMetric)
    {
        this.ownerMetric = ownerMetric;
    }

    @Override
    public OwnerMetric getData()
    {
        return getOwnerMetric();
    }

    @Override
    public void setData(OwnerMetric data)
    {
        setOwnerMetric(data);
    }
}
