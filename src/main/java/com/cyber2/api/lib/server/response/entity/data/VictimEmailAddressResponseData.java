/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.VictimEmailAddress;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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
