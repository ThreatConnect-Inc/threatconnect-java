/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.VictimSocialNetwork;
import com.cyber2.api.lib.server.response.entity.data.VictimSocialNetworkListResponseData;
import com.cyber2.api.lib.server.response.service.ApiServiceResponse;
import java.util.List;
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
