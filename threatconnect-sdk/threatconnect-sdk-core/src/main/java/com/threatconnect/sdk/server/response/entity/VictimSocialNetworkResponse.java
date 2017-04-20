/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.VictimSocialNetwork;
import com.threatconnect.sdk.server.response.entity.data.VictimSocialNetworkResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
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
