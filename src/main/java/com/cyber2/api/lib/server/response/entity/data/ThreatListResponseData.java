/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.Threat;
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
public class ThreatListResponseData extends ApiEntityListResponseData<Threat>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Threat", required = false)
    private List<Threat> threat;
    
    public List<Threat> getThreat()
    {
        return threat;
    }

    public void setThreat(List<Threat> threat)
    {
        this.threat = threat;
    }

    @Override
    @JsonIgnore
    public List<Threat> getData()
    {
        return getThreat();
    }

    @Override
    public void setData(List<Threat> data)
    {
        setThreat(data);
    }
}
