/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.threatconnect.sdk.server.entity.Victim;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.threatconnect.sdk.server.entity.Victim;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class VictimResponseData extends ApiEntitySingleResponseData<Victim>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Victim", required = false)
    private Victim victim;
    
    public Victim getVictim()
    {
        return victim;
    }

    public void setVictim(Victim victim)
    {
        this.victim = victim;
    }

    @Override
    public Victim getData()
    {
        return getVictim();
    }

    @Override
    public void setData(Victim data)
    {
        setVictim(data);
    }
}
