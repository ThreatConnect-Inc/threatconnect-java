/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.VictimPhone;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class VictimPhoneResponseData extends ApiEntitySingleResponseData<VictimPhone>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "VictimPhone", required = false)
    private VictimPhone victimPhone;
    
    public VictimPhone getVictimPhone()
    {
        return victimPhone;
    }

    public void setVictimPhone(VictimPhone victimPhone)
    {
        this.victimPhone = victimPhone;
    }

    @Override
    public VictimPhone getData()
    {
        return getVictimPhone();
    }

    @Override
    public void setData(VictimPhone data)
    {
        setVictimPhone(data);
    }
}
