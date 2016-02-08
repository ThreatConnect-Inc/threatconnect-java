/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.VictimWebSite;
import com.threatconnect.sdk.server.response.entity.data.VictimWebSiteListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "victimEmailAddressesResponse")
@XmlSeeAlso(VictimWebSite.class)
public class VictimWebSiteListResponse extends ApiEntityListResponse<VictimWebSite, VictimWebSiteListResponseData>
{
    public void setData(VictimWebSiteListResponseData data) {
        super.setData(data); 
    }
}
