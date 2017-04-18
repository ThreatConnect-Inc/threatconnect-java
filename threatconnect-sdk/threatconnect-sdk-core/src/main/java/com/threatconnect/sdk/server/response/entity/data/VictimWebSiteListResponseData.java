/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.VictimWebSite;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class VictimWebSiteListResponseData extends ApiEntityListResponseData<VictimWebSite>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "VictimWebSite", required = false)
    private List<VictimWebSite> victimWebSite;
    
    public List<VictimWebSite> getVictimWebSite()
    {
        return victimWebSite;
    }

    public void setVictimWebSite(List<VictimWebSite> victimWebSite)
    {
        this.victimWebSite = victimWebSite;
    }

    @Override
    @JsonIgnore
    public List<VictimWebSite> getData()
    {
        return getVictimWebSite();
    }

    @Override
    public void setData(List<VictimWebSite> data)
    {
        setVictimWebSite(data);
    }
}
