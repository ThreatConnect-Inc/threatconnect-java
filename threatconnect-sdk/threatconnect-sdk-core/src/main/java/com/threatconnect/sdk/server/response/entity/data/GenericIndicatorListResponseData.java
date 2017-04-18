package com.threatconnect.sdk.server.response.entity.data;

import java.util.ArrayList;
import java.util.List;


import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.util.GenericIndicatorListResponseDataDeserializer;


@JsonDeserialize(using = GenericIndicatorListResponseDataDeserializer.class)
public class GenericIndicatorListResponseData  extends ApiEntityListResponseData<Indicator>{

	@JsonIgnore
	@XmlTransient
    private List<Indicator> indicators = new ArrayList<Indicator>();

	@Override
	public List<Indicator> getData() {
		return indicators;
	}

	@Override
	public void setData(List<Indicator> data) {
		indicators = data; 
		
	}

}
