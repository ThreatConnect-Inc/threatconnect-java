/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Campaign;
import com.threatconnect.sdk.server.entity.Threat;
import com.threatconnect.sdk.server.response.entity.data.CampaignListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "campaignsResponse")
@XmlSeeAlso(Threat.class)
public class CampaignListResponse extends ApiEntityListResponse<Campaign, CampaignListResponseData>
{
    public void setData(CampaignListResponseData data) {
        super.setData(data); 
    }
}
