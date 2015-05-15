/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.VictimAsset;
import com.threatconnect.sdk.server.response.entity.data.VictimAssetResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "victimAssetResponse")
@XmlSeeAlso(VictimAsset.class)
public class VictimAssetResponse extends ApiEntitySingleResponse<VictimAsset, VictimAssetResponseData>
{
    public void setData(VictimAssetResponseData data) {
        super.setData(data); 
    }
}
