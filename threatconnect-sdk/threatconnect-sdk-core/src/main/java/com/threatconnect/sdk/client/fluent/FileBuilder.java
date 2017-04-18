package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.File;

public class FileBuilder extends AbstractIndicatorBuilder<FileBuilder>
{
	private String md5;
	private String sha1;
	private String sha256;
	private Integer size;
	
	public FileBuilder withMd5(String md5)
	{
		this.md5 = md5;
		return this;
	}
	
	public FileBuilder withSha1(String sha1)
	{
		this.sha1 = sha1;
		return this;
	}
	
	public FileBuilder withSha256(String sha256)
	{
		this.sha256 = sha256;
		return this;
	}
	
	public FileBuilder withSize(Integer size)
	{
		this.size = size;
		return this;
	}
	
	public File createFile()
	{
		return new File(id, owner, ownerName, type, dateAdded, lastModified, rating, confidence, threatAssessRating,
			threatAssessConfidence, webLink, source, description, summary, md5, sha1, sha256, size);
	}
}