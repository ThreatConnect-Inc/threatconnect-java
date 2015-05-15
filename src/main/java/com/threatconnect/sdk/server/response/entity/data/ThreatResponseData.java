/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.threatconnect.sdk.server.entity.Threat;
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
public class ThreatResponseData extends ApiEntitySingleResponseData<Threat>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Threat", required = false)
    private Threat threat;
    
    public Threat getThreat()
    {
        return threat;
    }

    public void setThreat(Threat threat)
    {
        this.threat = threat;
    }

    @Override
    @JsonIgnore
    public Threat getData()
    {
        return getThreat();
    }

    @Override
    public void setData(Threat data)
    {
        setThreat(data);
    }
}
