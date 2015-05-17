/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.VictimSocialNetwork;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class VictimSocialNetworkListResponseData extends ApiEntityListResponseData<VictimSocialNetwork>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "VictimSocialNetwork", required = false)
    private List<VictimSocialNetwork> victimSocialNetwork;
    
    public List<VictimSocialNetwork> getVictimSocialNetwork()
    {
        return victimSocialNetwork;
    }

    public void setVictimSocialNetwork(List<VictimSocialNetwork> victimSocialNetwork)
    {
        this.victimSocialNetwork = victimSocialNetwork;
    }

    @Override
    @JsonIgnore
    public List<VictimSocialNetwork> getData()
    {
        return getVictimSocialNetwork();
    }

    @Override
    public void setData(List<VictimSocialNetwork> data)
    {
        setVictimSocialNetwork(data);
    }
}
