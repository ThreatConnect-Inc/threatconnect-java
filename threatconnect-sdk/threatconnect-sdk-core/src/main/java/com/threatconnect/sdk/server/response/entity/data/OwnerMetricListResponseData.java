/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.OwnerMetric;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OwnerMetricListResponseData extends ApiEntityListResponseData<OwnerMetric>
{
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "OwnerMetric", required = false)
    private List<OwnerMetric> ownerMetric;
    
    public List<OwnerMetric> getOwnerMetric()
    {
        return ownerMetric;
    }

    public void setOwnerMetric(List<OwnerMetric> ownerMetric)
    {
        this.ownerMetric = ownerMetric;
    }

    @Override
    @JsonIgnore
    public List<OwnerMetric> getData()
    {
        return getOwnerMetric();
    }

    @Override
    public void setData(List<OwnerMetric> data)
    {
        setOwnerMetric(data);
    }
}
