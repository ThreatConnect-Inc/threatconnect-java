/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.VictimWebSite;
import com.threatconnect.sdk.server.response.entity.data.VictimWebSiteResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "victimWebSiteResponse")
@XmlSeeAlso(VictimWebSite.class)
public class VictimWebSiteResponse extends ApiEntitySingleResponse<VictimWebSite, VictimWebSiteResponseData>
{
    public void setData(VictimWebSiteResponseData data) {
        super.setData(data); 
    }
}
