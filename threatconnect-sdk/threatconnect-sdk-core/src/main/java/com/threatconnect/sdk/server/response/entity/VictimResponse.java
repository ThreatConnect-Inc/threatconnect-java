/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Victim;
import com.threatconnect.sdk.server.response.entity.data.VictimResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.entity.Victim;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "victimResponse")
@XmlSeeAlso(Victim.class)
public class VictimResponse extends ApiEntitySingleResponse<Victim, VictimResponseData>
{
    public void setData(VictimResponseData data) {
        super.setData(data); 
    }
}
