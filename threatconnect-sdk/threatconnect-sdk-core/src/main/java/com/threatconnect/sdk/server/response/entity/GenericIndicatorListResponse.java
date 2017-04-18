package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.CustomIndicator;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.response.entity.data.CustomIndicatorListResponseData;
import com.threatconnect.sdk.server.response.entity.data.GenericIndicatorListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "genericIndicatorListResponse")
@XmlSeeAlso(CustomIndicator.class)
public class GenericIndicatorListResponse extends ApiEntityListResponse<Indicator, GenericIndicatorListResponseData>
{
    public void setData(CustomIndicatorListResponseData data) {
        super.setData(data); 
    }

}
