package com.threatconnect.sdk.app.model;

public class Email extends Group
{
	private String to;
	private String from;
	private String subject;
	private Integer score;
	private String header;
	private String body;
	
	public Email()
	{
		super(GroupType.EMAIL);
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
	
	public String getSubject()
	{
		return subject;
	}
	
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
	public Integer getScore()
	{
		return score;
	}
	
	public void setScore(Integer score)
	{
		this.score = score;
	}
	
	public String getHeader()
	{
		return header;
	}
	
	public void setHeader(String header)
	{
		this.header = header;
	}
	
	public String getBody()
	{
		return body;
	}
	
	public void setBody(String body)
	{
		this.body = body;
	}
}
