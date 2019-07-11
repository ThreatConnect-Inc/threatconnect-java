package com.threatconnect.app.apps.service.message;

import java.util.List;

public class MailEvent extends AbstractCommandConfig
{
	private String to;
	private String from;
	private List<NameValuePair<String, String>> headers;
	private String subject;
	private String body;
	private String htmlBody;
	private List<byte[]> attachments;
	private List<String> fileNames;
	
	public MailEvent()
	{
		super(CommandType.MailEvent);
	}
	
	public String getTo()
	{
		return to;
	}
	
	public void setTo(String to)
	{
		this.to = to;
	}
	
	public String getFrom()
	{
		return from;
	}
	
	public void setFrom(String from)
	{
		this.from = from;
	}
	
	public List<NameValuePair<String, String>> getHeaders()
	{
		return headers;
	}
	
	public void setHeaders(List<NameValuePair<String, String>> headers)
	{
		this.headers = headers;
	}
	
	public String getSubject()
	{
		return subject;
	}
	
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
	public String getBody()
	{
		return body;
	}
	
	public void setBody(String body)
	{
		this.body = body;
	}
	
	public String getHtmlBody()
	{
		return htmlBody;
	}
	
	public void setHtmlBody(String htmlBody)
	{
		this.htmlBody = htmlBody;
	}
	
	public List<byte[]> getAttachments()
	{
		return attachments;
	}
	
	public void setAttachments(List<byte[]> attachments)
	{
		this.attachments = attachments;
	}
	
	public List<String> getFileNames()
	{
		return fileNames;
	}
	
	public void setFileNames(List<String> fileNames)
	{
		this.fileNames = fileNames;
	}
}

