/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Victim;
import com.threatconnect.sdk.server.response.entity.data.VictimListResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.entity.Victim;
import com.threatconnect.sdk.server.response.entity.data.VictimListResponseData;

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
@XmlRootElement(name = "victimsResponse")
@XmlSeeAlso(Victim.class)
public class VictimListResponse extends ApiEntityListResponse<Victim, VictimListResponseData>
{
    public void setData(VictimListResponseData data) {
        super.setData(data); 
    }
}
