/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.entity;

import com.cyber2.api.lib.server.entity.Victim;
import com.cyber2.api.lib.server.response.entity.data.VictimResponseData;
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
@XmlRootElement(name = "victimResponse")
@XmlSeeAlso(Victim.class)
public class VictimResponse extends ApiEntitySingleResponse<Victim, VictimResponseData>
{
    public void setData(VictimResponseData data) {
        super.setData(data); 
    }
}
