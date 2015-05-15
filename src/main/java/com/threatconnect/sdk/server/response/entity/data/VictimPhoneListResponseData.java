/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.threatconnect.sdk.server.entity.VictimPhone;
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
public class VictimPhoneListResponseData extends ApiEntityListResponseData<VictimPhone>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "VictimPhone", required = false)
    private List<VictimPhone> victimPhone;
    
    public List<VictimPhone> getVictimPhone()
    {
        return victimPhone;
    }

    public void setVictimPhone(List<VictimPhone> victimPhone)
    {
        this.victimPhone = victimPhone;
    }

    @Override
    @JsonIgnore
    public List<VictimPhone> getData()
    {
        return getVictimPhone();
    }

    @Override
    public void setData(List<VictimPhone> data)
    {
        setVictimPhone(data);
    }
}
