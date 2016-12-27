package com.threatconnect.app.addons.util.config.feed;

import com.google.gson.JsonElement;
import com.threatconnect.app.addons.util.JsonUtil;
import com.threatconnect.app.addons.util.config.InvalidJsonFileException;
import com.threatconnect.app.addons.util.config.JsonFile;

import java.io.File;

/**
 * @author Greg Marut
 */
public class FeedJson extends JsonFile
{
	private static final String SOURCE_NAME = "sourceName";
	private static final String SOURCE_CATEGORY = "sourceCategory";
	private static final String SOURCE_DESCRIPTION = "sourceDescription";
	private static final String ATTRIBUTES_FILE = "attributesFile";
	private static final String ENABLE_BULK_JSON = "enableBulkJson";
	private static final String DEPRECATION = "deprecation";
	private static final String INDICATOR_LIMIT = "indicatorLimit";
	private static final String DOCUMENT_STORAGE_LIMIT = "documentStorageLimitMb";
	private static final String JOB_FILE = "jobFile";
	
	private static final String TRUE = "true";
	
	//holds the deprecation object for the feed json file if one exists
	private final Deprecation deprecation;
	
	public FeedJson(final File file) throws InvalidJsonFileException
	{
		super(file);
		
		//retrieve the deprecation json object
		JsonElement deprecationElement = JsonUtil.get(getRoot(), DEPRECATION);
		if (null != deprecationElement)
		{
			this.deprecation = new Deprecation(deprecationElement.getAsJsonObject());
		}
		else
		{
			//this feed json file does not contain any deprecation config so set the object to null
			this.deprecation = null;
		}
	}
	
	public String getSourceName()
	{
		return JsonUtil.getAsString(getRoot(), SOURCE_NAME);
	}
	
	public String getSourceCategory()
	{
		return JsonUtil.getAsString(getRoot(), SOURCE_CATEGORY);
	}
	
	public String getSourceDescription()
	{
		return JsonUtil.getAsString(getRoot(), SOURCE_DESCRIPTION);
	}
	
	public String getAttributesFile()
	{
		return JsonUtil.getAsString(getRoot(), ATTRIBUTES_FILE);
	}
	
	public boolean isEnableBulkJson()
	{
		return TRUE.equalsIgnoreCase(JsonUtil.getAsString(getRoot(), ENABLE_BULK_JSON));
	}
	
	public String getIndicatorLimit()
	{
		return JsonUtil.getAsString(getRoot(), INDICATOR_LIMIT);
	}
	
	public String getDocumentStorageLimit()
	{
		return JsonUtil.getAsString(getRoot(), DOCUMENT_STORAGE_LIMIT);
	}
	
	public String getJobFile()
	{
		return JsonUtil.getAsString(getRoot(), JOB_FILE);
	}
}
