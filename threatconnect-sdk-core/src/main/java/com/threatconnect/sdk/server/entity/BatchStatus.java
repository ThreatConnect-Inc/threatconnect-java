package com.threatconnect.sdk.server.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by cblades on 6/10/2015.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement(name = "BatchStatus")
@XmlAccessorType(XmlAccessType.FIELD)
public class BatchStatus
{
	@XmlElement(name = "status", required = false)
	private Status status;
	
	@XmlElement(name = "id", required = false)
	private Integer id;
	
	public enum Status
	{
		Created,
		Queued,
		Running,
		Completed
	}
	
	public BatchStatus()
	{
	}
	
	public BatchStatus(Status status, Integer id)
	{
		this.status = status;
		this.id = id;
	}
	
	public Status getStatus()
	{
		return status;
	}
	
	public void setStatus(Status status)
	{
		this.status = status;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(Integer id)
	{
		this.id = id;
	}
}
