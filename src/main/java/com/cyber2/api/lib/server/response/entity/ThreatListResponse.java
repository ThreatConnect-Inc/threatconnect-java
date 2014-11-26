/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.Threat;
import com.cyber2.api.lib.server.response.entity.data.ThreatListResponseData;
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
@XmlRootElement(name = "threatsResponse")
@XmlSeeAlso(Threat.class)
public class ThreatListResponse extends ApiEntityListResponse<Threat, ThreatListResponseData>
{
    public void setData(ThreatListResponseData data) {
        super.setData(data); 
    }
}
