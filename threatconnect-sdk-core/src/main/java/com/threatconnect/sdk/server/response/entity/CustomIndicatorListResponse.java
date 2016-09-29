package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.CustomIndicator;
import com.threatconnect.sdk.server.response.entity.data.CustomIndicatorListResponseData;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "customIndicatorsResponse")
@XmlSeeAlso(CustomIndicator.class)
public class CustomIndicatorListResponse extends ApiEntityListResponse<CustomIndicator, CustomIndicatorListResponseData>
{
    public void setData(CustomIndicatorListResponseData data) {
        super.setData(data); 
    }

}
