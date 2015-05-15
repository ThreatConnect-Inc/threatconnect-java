/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.threatconnect.sdk.server.entity.Incident;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class IncidentListResponseData extends ApiEntityListResponseData<Incident>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Incident", required = false)
    private List<Incident> incident;
    
    public List<Incident> getIncident()
    {
        return incident;
    }

    public void setIncident(List<Incident> incident)
    {
        this.incident = incident;
    }

    @Override
    @JsonIgnore
    public List<Incident> getData()
    {
        return getIncident();
    }

    @Override
    public void setData(List<Incident> data)
    {
        setIncident(data);
    }
}
