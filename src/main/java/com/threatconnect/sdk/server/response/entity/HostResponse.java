/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.response.entity.data.HostResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "hostResponse")
@XmlSeeAlso(Host.class)
public class HostResponse extends ApiEntitySingleResponse<Host, HostResponseData>
{
    public void setData(HostResponseData data) {
        super.setData(data); 
    }
}
