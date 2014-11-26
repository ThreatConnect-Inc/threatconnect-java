/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.Incident;
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
