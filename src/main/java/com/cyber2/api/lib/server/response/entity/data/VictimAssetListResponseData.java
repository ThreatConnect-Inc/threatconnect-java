/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity.data;

import com.cyber2.api.lib.server.entity.VictimAsset;
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
