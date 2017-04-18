/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.VictimEmailAddress;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class VictimEmailAddressResponseData extends ApiEntitySingleResponseData<VictimEmailAddress>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "VictimEmailAddress", required = false)
    private VictimEmailAddress victimEmailAddress;
    
    public VictimEmailAddress getVictimEmailAddress()
    {
        return victimEmailAddress;
    }

    public void setVictimEmailAddress(VictimEmailAddress victimEmailAddress)
    {
        this.victimEmailAddress = victimEmailAddress;
    }

    @Override
    public VictimEmailAddress getData()
    {
        return getVictimEmailAddress();
    }

    @Override
    public void setData(VictimEmailAddress data)
    {
        setVictimEmailAddress(data);
    }
}
