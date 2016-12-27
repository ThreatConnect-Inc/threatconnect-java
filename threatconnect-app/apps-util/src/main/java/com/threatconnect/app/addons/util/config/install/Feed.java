package com.threatconnect.app.addons.util.config.install;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.threatconnect.app.addons.util.JsonUtil;
import com.threatconnect.app.addons.util.config.InvalidJsonFileException;

/**
 * @author Greg Marut
 */
public class Feed
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
	
	//holds the root json object for this feed object
	private final JsonObject root;
	
	//holds the deprecation object for the feed json file if one exists
	private final Deprecation deprecation;
	
	public Feed(final JsonObject root) throws InvalidJsonFileException
	{
		//make sure the root object is not null
		if(null == root)
		{
			throw new IllegalArgumentException("root cannot be null");
		}
		
		this.root = root;
		
		//retrieve the deprecation json object
		JsonElement deprecationElement = JsonUtil.get(root, DEPRECATION);
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
		return JsonUtil.getAsString(root, SOURCE_NAME);
	}
	
	public String getSourceCategory()
	{
		return JsonUtil.getAsString(root, SOURCE_CATEGORY);
	}
	
	public String getSourceDescription()
	{
		return JsonUtil.getAsString(root, SOURCE_DESCRIPTION);
	}
	
	public String getAttributesFile()
	{
		return JsonUtil.getAsString(root, ATTRIBUTES_FILE);
	}
	
	public boolean isEnableBulkJson()
	{
		return JsonUtil.getAsBoolean(root, ENABLE_BULK_JSON);
	}
	
	public String getIndicatorLimit()
	{
		return JsonUtil.getAsString(root, INDICATOR_LIMIT);
	}
	
	public String getDocumentStorageLimit()
	{
		return JsonUtil.getAsString(root, DOCUMENT_STORAGE_LIMIT);
	}
	
	public String getJobFile()
	{
		return JsonUtil.getAsString(root, JOB_FILE);
	}
	
	public Deprecation getDeprecation()
	{
		return deprecation;
	}
}
