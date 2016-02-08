package com.threatconnect.sdk.app.model;

public class Signature extends Group
{
	private String fileType;
	private String fileName;
	private String fileText;
	
	public Signature()
	{
		super(GroupType.SIGNATURE);
	}
	
	public String getFileType()
	{
		return fileType;
	}
	
	public void setFileType(String fileType)
	{
		this.fileType = fileType;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	public String getFileText()
	{
		return fileText;
	}
	
	public void setFileText(String fileText)
	{
		this.fileText = fileText;
	}
	
}
