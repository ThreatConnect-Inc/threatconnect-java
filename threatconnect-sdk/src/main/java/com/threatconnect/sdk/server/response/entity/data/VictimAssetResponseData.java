/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.VictimAsset;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class VictimAssetResponseData extends ApiEntitySingleResponseData<VictimAsset>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "VictimAsset", required = false)
    private VictimAsset victimAsset;
    
    public VictimAsset getVictimAsset()
    {
        return victimAsset;
    }

    public void setVictimAsset(VictimAsset victimAsset)
    {
        this.victimAsset = victimAsset;
    }

    @Override
    public VictimAsset getData()
    {
        return getVictimAsset();
    }

    @Override
    public void setData(VictimAsset data)
    {
        setVictimAsset(data);
    }
}
