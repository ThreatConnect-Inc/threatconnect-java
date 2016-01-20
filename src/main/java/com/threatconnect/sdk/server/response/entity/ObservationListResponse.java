/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;


import com.threatconnect.sdk.server.entity.Observation;
import com.threatconnect.sdk.server.response.entity.data.ObservationListResponseData;
import com.threatconnect.sdk.server.response.entity.data.OwnerListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mjimenez
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "observationsResponse")
public class ObservationListResponse extends ApiEntityListResponse<Observation, ObservationListResponseData>
{

    public void setData(OwnerListResponseData data)
    {
        super.setData(data);
    }

}
