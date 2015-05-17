/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.VictimAsset;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class VictimAssetListResponseData extends ApiEntityListResponseData<VictimAsset>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "VictimAsset", required = false)
    private List<VictimAsset> victimAsset;
    
    public List<VictimAsset> getVictimAsset()
    {
        return victimAsset;
    }

    public void setVictimAsset(List<VictimAsset> victimAsset)
    {
        this.victimAsset = victimAsset;
    }

    @Override
    @JsonIgnore
    public List<VictimAsset> getData()
    {
        return getVictimAsset();
    }

    @Override
    public void setData(List<VictimAsset> data)
    {
        setVictimAsset(data);
    }
}
