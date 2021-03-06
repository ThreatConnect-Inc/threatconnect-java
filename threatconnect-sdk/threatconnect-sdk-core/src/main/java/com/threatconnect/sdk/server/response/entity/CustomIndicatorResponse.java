package com.threatconnect.sdk.server.response.entity;

import javax.xml.bind.annotation.XmlAccessorType;

import com.threatconnect.sdk.server.entity.CustomIndicator;
import com.threatconnect.sdk.server.response.entity.data.CustomIndicatorResponseData;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "customIndicatorResponse")
@XmlSeeAlso(CustomIndicator.class)
public class CustomIndicatorResponse extends ApiEntitySingleResponse<CustomIndicator, CustomIndicatorResponseData>
{
    public void setData(CustomIndicatorResponseData data) {
        super.setData(data); 
    }
}
