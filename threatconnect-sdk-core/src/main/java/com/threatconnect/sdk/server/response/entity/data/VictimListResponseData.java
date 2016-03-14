/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Victim;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class VictimListResponseData extends ApiEntityListResponseData<Victim>
{
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Victim", required = false)
    private List<Victim> victim;
    
    public List<Victim> getVictim()
    {
        return victim;
    }

    public void setVictim(List<Victim> victim)
    {
        this.victim = victim;
    }

    @Override
    @JsonIgnore
    public List<Victim> getData()
    {
        return getVictim();
    }

    @Override
    public void setData(List<Victim> data)
    {
        setVictim(data);
    }
}
