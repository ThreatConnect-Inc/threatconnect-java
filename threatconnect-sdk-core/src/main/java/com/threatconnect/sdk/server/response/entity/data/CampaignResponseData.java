/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Campaign;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignResponseData extends ApiEntitySingleResponseData<Campaign>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Campaign", required = false)
    private Campaign campaign;
    
    public Campaign getCampaign()
    {
        return campaign;
    }
    
    public void setCampaign(final Campaign campaign)
    {
        this.campaign = campaign;
    }
    
    @Override
    @JsonIgnore
    public Campaign getData()
    {
        return getCampaign();
    }

    @Override
    public void setData(Campaign data)
    {
        setCampaign(data);
    }
}
