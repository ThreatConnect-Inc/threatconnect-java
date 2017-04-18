package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.SpaceState;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by dtineo
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SpaceStateResponseData extends ApiEntitySingleResponseData<SpaceState>
{
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "spaceState", required = false)
    private SpaceState spaceState;

    @Override
    public SpaceState getData()
    {
        return getSpaceState();
    }

    @Override
    public void setData(SpaceState data)
    {
        setSpaceState(data);
    }

    public SpaceState getSpaceState()
    {
        return spaceState;
    }

    public void setSpaceState(SpaceState spaceState)
    {
        this.spaceState = spaceState;
    }

}
