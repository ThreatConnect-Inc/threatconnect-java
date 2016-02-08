/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.VictimWebSite;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class VictimWebSiteResponseData extends ApiEntitySingleResponseData<VictimWebSite>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "VictimWebSite", required = false)
    private VictimWebSite victimWebSite;
    
    public VictimWebSite getVictimWebSite()
    {
        return victimWebSite;
    }

    public void setVictimWebSite(VictimWebSite victimWebSite)
    {
        this.victimWebSite = victimWebSite;
    }

    @Override
    public VictimWebSite getData()
    {
        return getVictimWebSite();
    }

    @Override
    public void setData(VictimWebSite data)
    {
        setVictimWebSite(data);
    }
}
