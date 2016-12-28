package com.threatconnect.app.addons.util.config.install;

/**
 * @author Greg Marut
 */
public class Feed
{
	private String sourceName;
	private String sourceCategory;
	private String sourceDescription;
	private String attributesFile;
	private boolean enableBulkJson;
	private Integer indicatorLimit;
	private Integer documentStorageLimitMb;
	private String jobFile;
	private Deprecation deprecation;
	
	public String getSourceName()
	{
		return sourceName;
	}
	
	public void setSourceName(final String sourceName)
	{
		this.sourceName = sourceName;
	}
	
	public String getSourceCategory()
	{
		return sourceCategory;
	}
	
	public void setSourceCategory(final String sourceCategory)
	{
		this.sourceCategory = sourceCategory;
	}
	
	public String getSourceDescription()
	{
		return sourceDescription;
	}
	
	public void setSourceDescription(final String sourceDescription)
	{
		this.sourceDescription = sourceDescription;
	}
	
	public String getAttributesFile()
	{
		return attributesFile;
	}
	
	public void setAttributesFile(final String attributesFile)
	{
		this.attributesFile = attributesFile;
	}
	
	public boolean isEnableBulkJson()
	{
		return enableBulkJson;
	}
	
	public void setEnableBulkJson(final boolean enableBulkJson)
	{
		this.enableBulkJson = enableBulkJson;
	}
	
	public Integer getIndicatorLimit()
	{
		return indicatorLimit;
	}
	
	public void setIndicatorLimit(final Integer indicatorLimit)
	{
		this.indicatorLimit = indicatorLimit;
	}
	
	public Integer getDocumentStorageLimitMb()
	{
		return documentStorageLimitMb;
	}
	
	public void setDocumentStorageLimitMb(final Integer documentStorageLimitMb)
	{
		this.documentStorageLimitMb = documentStorageLimitMb;
	}
	
	public String getJobFile()
	{
		return jobFile;
	}
	
	public void setJobFile(final String jobFile)
	{
		this.jobFile = jobFile;
	}
	
	public Deprecation getDeprecation()
	{
		return deprecation;
	}
	
	public void setDeprecation(final Deprecation deprecation)
	{
		this.deprecation = deprecation;
	}
}
