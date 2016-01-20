/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.ObservationCount;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author mjimenez
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ObservationCountResponseData extends ApiEntitySingleResponseData<ObservationCount>
{
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "ObservationCount", required = false)
    private ObservationCount observationCount;
    
    public ObservationCount getObservationCount()
    {
        return observationCount;
    }

    public void setObservationCount(ObservationCount observationCount)
    {
        this.observationCount = observationCount;
    }

    @Override
    @JsonIgnore
    public ObservationCount getData()
    {
        return getObservationCount();
    }

    @Override
    public void setData(ObservationCount data)
    {
        setObservationCount(data);
    }
}
