/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.threatconnect.sdk.server.entity.VictimSocialNetwork;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class VictimSocialNetworkResponseData extends ApiEntitySingleResponseData<VictimSocialNetwork>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "VictimSocialNetwork", required = false)
    private VictimSocialNetwork victimSocialNetwork;
    
    public VictimSocialNetwork getVictimSocialNetwork()
    {
        return victimSocialNetwork;
    }

    public void setVictimSocialNetwork(VictimSocialNetwork victimSocialNetwork)
    {
        this.victimSocialNetwork = victimSocialNetwork;
    }

    @Override
    public VictimSocialNetwork getData()
    {
        return getVictimSocialNetwork();
    }

    @Override
    public void setData(VictimSocialNetwork data)
    {
        setVictimSocialNetwork(data);
    }
}
