/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Incident;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class IncidentResponseData extends ApiEntitySingleResponseData<Incident>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Incident", required = false)
    private Incident incident;
    
    public Incident getIncident()
    {
        return incident;
    }

    public void setIncident(Incident incident)
    {
        this.incident = incident;
    }

    @Override
    @JsonIgnore
    public Incident getData()
    {
        return getIncident();
    }

    @Override
    public void setData(Incident data)
    {
        setIncident(data);
    }
}
