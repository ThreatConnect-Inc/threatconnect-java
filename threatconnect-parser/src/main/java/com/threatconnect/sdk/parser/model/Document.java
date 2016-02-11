package com.threatconnect.sdk.parser.model;

public class Document extends Group
{
	private String fileName;
	private Long fileSize;
	private String status;
	private Boolean malware;
	private String password;
	
	public Document()
	{
		super(GroupType.DOCUMENT);
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
}
