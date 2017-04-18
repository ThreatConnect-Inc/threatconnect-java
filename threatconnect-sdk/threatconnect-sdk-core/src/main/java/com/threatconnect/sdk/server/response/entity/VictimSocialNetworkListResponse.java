/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.VictimSocialNetwork;
import com.threatconnect.sdk.server.response.entity.data.VictimSocialNetworkListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "victimSocialNetworksResponse")
@XmlSeeAlso(VictimSocialNetwork.class)
public class VictimSocialNetworkListResponse extends ApiEntityListResponse<VictimSocialNetwork, VictimSocialNetworkListResponseData>
{
    public void setData(VictimSocialNetworkListResponseData data) {
        super.setData(data); 
    }
}
