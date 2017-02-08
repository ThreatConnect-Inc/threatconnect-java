package com.threatconnect.app.addons.util.config.install;

import java.util.ArrayList;
import java.util.List;

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
	private Long documentStorageLimitMb;
	private String jobFile;
	
	private final List<Deprecation> deprecation;
	private final List<ParamOverride> firstRunParams;
	
	public Feed()
	{
		this.deprecation = new ArrayList<Deprecation>();
		this.firstRunParams = new ArrayList<ParamOverride>();
	}
	
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
	
	public Long getDocumentStorageLimitMb()
	{
		return documentStorageLimitMb;
	}
	
	public void setDocumentStorageLimitMb(final Long documentStorageLimitMb)
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
	
	public List<Deprecation> getDeprecation()
	{
		return deprecation;
	}
	
	public List<ParamOverride> getFirstRunParams()
	{
		return firstRunParams;
	}
}
