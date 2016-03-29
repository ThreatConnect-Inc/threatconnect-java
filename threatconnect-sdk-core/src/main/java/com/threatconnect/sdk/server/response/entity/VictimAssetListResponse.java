/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.VictimAsset;
import com.threatconnect.sdk.server.response.entity.data.VictimAssetListResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.entity.VictimAsset;
import com.threatconnect.sdk.server.response.entity.data.VictimAssetListResponseData;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "victimAssetsResponse")
@XmlSeeAlso(VictimAsset.class)
public class VictimAssetListResponse extends ApiEntityListResponse<VictimAsset, VictimAssetListResponseData>
{
    public void setData(VictimAssetListResponseData data) {
        super.setData(data); 
    }
}
