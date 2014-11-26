/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.VictimPhone;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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
