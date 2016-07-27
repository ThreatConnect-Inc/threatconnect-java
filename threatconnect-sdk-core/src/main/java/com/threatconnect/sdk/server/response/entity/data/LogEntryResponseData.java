package com.threatconnect.sdk.server.response.entity.data;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class LogEntryResponseData extends ApiEntitySingleResponseData<Integer>
{
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	@XmlElement(name = "count", required = false)
	private Integer count;
	
	public Integer getCount()
	{
		return count;
	}
	
	public void setCount(Integer count)
	{
		this.count = count;
	}
	
	@Override
	@JsonIgnore
	public Integer getData()
	{
		return getCount();
	}
	
	@Override
	public void setData(Integer data)
	{
		setCount(data);
	}
}
