/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Campaign;
import com.threatconnect.sdk.server.entity.Threat;
import com.threatconnect.sdk.server.response.entity.data.CampaignResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "campaignResponse")
@XmlSeeAlso(Threat.class)
public class CampaignResponse extends ApiEntitySingleResponse<Campaign, CampaignResponseData>
{
    public void setData(CampaignResponseData data) {
        super.setData(data); 
    }
}
