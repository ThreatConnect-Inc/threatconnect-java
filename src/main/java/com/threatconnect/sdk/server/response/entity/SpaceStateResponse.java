package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.SpaceState;
import com.threatconnect.sdk.server.response.entity.data.SpaceStateResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by dtineo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "spaceState")
@XmlSeeAlso(SpaceStateResponse.class)
public class SpaceStateResponse extends ApiEntitySingleResponse<SpaceState, SpaceStateResponseData>
{
    public void setData(SpaceStateResponseData data) {
        super.setData(data);
    }

}
