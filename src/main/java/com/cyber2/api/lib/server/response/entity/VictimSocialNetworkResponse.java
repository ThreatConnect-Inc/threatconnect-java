/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.VictimSocialNetwork;
import com.cyber2.api.lib.server.response.entity.data.VictimSocialNetworkResponseData;
import com.cyber2.api.lib.server.response.service.ApiServiceResponse;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "victimSocialNetworkResponse")
@XmlSeeAlso(VictimSocialNetwork.class)
public class VictimSocialNetworkResponse extends ApiEntitySingleResponse<VictimSocialNetwork, VictimSocialNetworkResponseData>
{
    public void setData(VictimSocialNetworkResponseData data) {
        super.setData(data); 
    }
}
