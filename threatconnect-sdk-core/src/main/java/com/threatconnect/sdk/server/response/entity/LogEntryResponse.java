package com.threatconnect.sdk.server.response.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.threatconnect.sdk.server.response.entity.data.LogEntryResponseData;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "logEntryResponse")
@XmlSeeAlso(Integer.class)
public class LogEntryResponse extends ApiEntitySingleResponse<Integer, LogEntryResponseData>
{
	public void setData(LogEntryResponseData data)
	{
		super.setData(data);
	}
}
