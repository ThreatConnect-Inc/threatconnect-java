/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Threat;
import com.threatconnect.sdk.server.response.entity.data.ThreatListResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
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
@XmlRootElement(name = "threatsResponse")
@XmlSeeAlso(Threat.class)
public class ThreatListResponse extends ApiEntityListResponse<Threat, ThreatListResponseData>
{
    public void setData(ThreatListResponseData data) {
        super.setData(data); 
    }
}
