/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.response.entity.data.HostListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "hostsResponse")
@XmlSeeAlso(Host.class)
public class HostListResponse extends ApiEntityListResponse<Host, HostListResponseData>
{
    public void setData(HostListResponseData data) {
        super.setData(data); 
    }
}
