package com.threatconnect.sdk.model;

import java.io.File;

public class Report extends Group
{
	private String fileName;
	private Long fileSize;
	private String status;
	private Boolean malware;
	private String password;
	private java.io.File file;
	
	public Report()
	{
		super(GroupType.REPORT);
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
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
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
	
	public java.io.File getFile()
	{
		return file;
	}
	
	public void setFile(File file)
	{
		this.file = file;
	}
}
