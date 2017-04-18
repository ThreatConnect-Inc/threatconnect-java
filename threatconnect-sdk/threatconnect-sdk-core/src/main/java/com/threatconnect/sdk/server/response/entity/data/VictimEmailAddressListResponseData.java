/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.VictimEmailAddress;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class VictimEmailAddressListResponseData extends ApiEntityListResponseData<VictimEmailAddress>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "VictimEmailAddress", required = false)
    private List<VictimEmailAddress> victimEmailAddress;
    
    public List<VictimEmailAddress> getVictimEmailAddress()
    {
        return victimEmailAddress;
    }

    public void setVictimEmailAddress(List<VictimEmailAddress> victimEmailAddress)
    {
        this.victimEmailAddress = victimEmailAddress;
    }

    @Override
    @JsonIgnore
    public List<VictimEmailAddress> getData()
    {
        return getVictimEmailAddress();
    }

    @Override
    public void setData(List<VictimEmailAddress> data)
    {
        setVictimEmailAddress(data);
    }
}
