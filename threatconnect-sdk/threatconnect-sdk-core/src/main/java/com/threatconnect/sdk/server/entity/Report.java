package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Report")
public class Report extends Group
{
	@XmlElement(name = "fileName", required = false)
	private String fileName;
	@XmlElement(name = "fileSize", required = false)
	private Long fileSize;
	@XmlElement(name = "status", required = false)
	private String status;
	@XmlTransient
	private Boolean malware;
	@XmlTransient
	private String password;
	
	public Report()
	{
	}
	
	public Report(Integer id, String name, String type, Owner owner, String ownerName, Date dateAdded, String webLink,
		String fileName, Long fileSize, String status, Boolean malware, String password)
	{
		super(id, name, type, owner, ownerName, dateAdded, webLink);
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.status = status;
		this.malware = malware;
		this.password = password;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	public Long getFileSize()
	{
		return fileSize;
	}
	
	public void setFileSize(Long fileSize)
	{
		this.fileSize = fileSize;
	}
	
	public Boolean getMalware()
	{
		return malware;
	}
	
	public void setMalware(Boolean malware)
	{
		this.malware = malware;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
}

