/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.Victim;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
