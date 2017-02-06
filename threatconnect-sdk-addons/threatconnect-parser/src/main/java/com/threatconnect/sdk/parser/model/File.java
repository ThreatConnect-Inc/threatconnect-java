package com.threatconnect.sdk.parser.model;

import java.util.ArrayList;
import java.util.List;

public class File extends Indicator
{
	public static final String INDICATOR_TYPE = "File";
	
	public static final int MD5_LENGTH = 32;
	public static final int SHA1_LENGTH = 40;
	public static final int SHA256_LENGTH = 64;
	
	private String md5;
	private String sha1;
	private String sha256;
	private Integer size;
	
	private final List<FileOccurrence> fileOccurrences;
	
	public File()
	{
		super(INDICATOR_TYPE);
		fileOccurrences = new ArrayList<FileOccurrence>();
	}
	
	public String getMd5()
	{
		return md5;
	}
	
	public void setMd5(String md5)
	{
		this.md5 = md5;
	}
	
	public String getSha1()
	{
		return sha1;
	}
	
	public void setSha1(String sha1)
	{
		this.sha1 = sha1;
	}
	
	public String getSha256()
	{
		return sha256;
	}
	
	public void setSha256(String sha256)
	{
		this.sha256 = sha256;
	}
	
	public Integer getSize()
	{
		return size;
	}
	
	public void setSize(Integer size)
	{
		this.size = size;
	}
	
	public List<FileOccurrence> getFileOccurrences()
	{
		return fileOccurrences;
	}
	
	@Override
	public String getIdentifier()
	{
		return emptyIfNull(getMd5()) + ":" + emptyIfNull(getSha1()) + ":" + emptyIfNull(getSha256());
	}
	
	private String emptyIfNull(final String s)
	{
		return (null != s) ? s : "";
	}
}
