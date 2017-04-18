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
import java.util.List;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignListResponseData extends ApiEntityListResponseData<Campaign>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Campaign", required = false)
    private List<Campaign> campaign;
    
    public List<Campaign> getCampaign()
    {
        return campaign;
    }
    
    public void setCampaign(final List<Campaign> campaign)
    {
        this.campaign = campaign;
    }
    
    @Override
    @JsonIgnore
    public List<Campaign> getData()
    {
        return getCampaign();
    }

    @Override
    public void setData(List<Campaign> data)
    {
        setCampaign(data);
    }
}
