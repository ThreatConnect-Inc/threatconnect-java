/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.Threat;
import com.cyber2.api.lib.server.response.entity.data.ThreatResponseData;
import com.cyber2.api.lib.server.response.service.ApiServiceResponse;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "threatResponse")
@XmlSeeAlso(Threat.class)
public class ThreatResponse extends ApiEntitySingleResponse<Threat, ThreatResponseData>
{
    public void setData(ThreatResponseData data) {
        super.setData(data); 
    }
}
