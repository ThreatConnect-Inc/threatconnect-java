package com.threatconnect.sdk.server.response.entity.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.threatconnect.sdk.server.entity.CustomIndicator;
import com.threatconnect.sdk.util.CustomIndicatorListResponseDataDeserializer;


@XmlAccessorType(XmlAccessType.FIELD)
@JsonDeserialize(using = CustomIndicatorListResponseDataDeserializer.class)
public class CustomIndicatorListResponseData extends ApiEntityListResponseData<CustomIndicator>
{
	@JsonIgnore
	@XmlTransient
    private List<CustomIndicator> customIndicator;

    public CustomIndicatorListResponseData()
    {
    }

    
    public CustomIndicatorListResponseData(List<CustomIndicator> indicator)
    {
        this.customIndicator = indicator;
    }

    public List<CustomIndicator> getCustomIndicator()
    {
        return customIndicator;
    }

    public void setCustomIndicator(List<CustomIndicator> indicator)
    {
        this.customIndicator = indicator;
    }

    @Override
    @JsonIgnore
    public List<CustomIndicator> getData()
    {
        return getCustomIndicator();
    }

    @Override
    public void setData(List<CustomIndicator> data)
    {
        setCustomIndicator(data);
    }
}
