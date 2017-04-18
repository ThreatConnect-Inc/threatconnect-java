/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Observation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 *
 * @author mjimenez
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ObservationListResponseData extends ApiEntityListResponseData<Observation>
{
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Observation", required = false)
    private List<Observation> observation;

    public List<Observation> getObservation()
    {
        return observation;
    }

    public void setObservation(List<Observation> observation)
    {
        this.observation = observation;
    }

    @Override
    @JsonIgnore
    public List<Observation> getData()
    {
        return getObservation();
    }

    @Override
    public void setData(List<Observation> data)
    {
        setObservation(data);
    }
}
