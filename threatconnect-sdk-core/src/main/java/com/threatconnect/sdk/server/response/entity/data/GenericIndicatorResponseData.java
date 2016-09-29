package com.threatconnect.sdk.server.response.entity.data;

import javax.xml.bind.annotation.XmlAccessType;
import  javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.threatconnect.sdk.server.entity.CustomIndicator;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.util.GenericIndicatorResponseDataDeserializer;

@JsonDeserialize(using = GenericIndicatorResponseDataDeserializer.class)
@JsonInclude(Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
public class GenericIndicatorResponseData  extends ApiEntitySingleResponseData<Indicator>
{ 
	   @JsonIgnore
	    @XmlTransient
	    private Indicator indicator;
	   

    public GenericIndicatorResponseData()
    {
    }

    public GenericIndicatorResponseData(Indicator indicator)
    {
        this.indicator = indicator;
    }

   

    @Override
    @JsonIgnore
    public Indicator getData()
    {
        return indicator;
    }

    @Override
    public void setData(Indicator data)
    {
        this.indicator = data;
    }
    

}
