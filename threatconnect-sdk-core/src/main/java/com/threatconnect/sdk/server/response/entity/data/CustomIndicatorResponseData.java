package com.threatconnect.sdk.server.response.entity.data;

import javax.xml.bind.annotation.XmlAccessType;
import  javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.threatconnect.sdk.server.entity.CustomIndicator;
import com.threatconnect.sdk.util.CustomIndicatorResponseDataDeserializer;

@JsonDeserialize(using = CustomIndicatorResponseDataDeserializer.class)
@JsonInclude(Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomIndicatorResponseData  extends ApiEntitySingleResponseData<CustomIndicator>
{ 
	   @JsonIgnore
	    @XmlTransient
	    private CustomIndicator customIndicator;
	    @JsonIgnore
	    @XmlTransient
	    private String type;

    public CustomIndicatorResponseData()
    {
    }

    public CustomIndicatorResponseData(CustomIndicator indicator)
    {
        this.customIndicator = indicator;
    }

    public CustomIndicator getCustomIndicator()
    {
        return customIndicator;
    }

    public void setCustomIndicator(CustomIndicator indicator)
    {
        this.customIndicator = indicator;
    }

    @Override
    @JsonIgnore
    public CustomIndicator getData()
    {
        return getCustomIndicator();
    }

    @Override
    public void setData(CustomIndicator data)
    {
        setCustomIndicator(data);
    }
    
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
