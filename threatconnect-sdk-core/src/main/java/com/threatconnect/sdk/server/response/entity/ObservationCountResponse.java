/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.ObservationCount;
import com.threatconnect.sdk.server.response.entity.data.ObservationCountResponseData;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mjimenez
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "observationCountResponse")
public class ObservationCountResponse extends ApiEntitySingleResponse<ObservationCount, ObservationCountResponseData>
{
    public void setData(ObservationCountResponseData data) {
        super.setData(data);
    }

}
