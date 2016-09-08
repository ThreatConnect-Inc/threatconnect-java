package com.threatconnect.sdk.blueprints.content;

import com.threatconnect.sdk.blueprints.content.accumulator.ContentAccumulator;
import com.threatconnect.sdk.blueprints.content.accumulator.ContentException;
import com.threatconnect.sdk.blueprints.content.converter.StringConverter;
import com.threatconnect.sdk.blueprints.content.converter.StringListConverter;
import com.threatconnect.sdk.blueprints.db.DBService;
import com.threatconnect.sdk.blueprints.util.BlueprintVariableUtil;

import java.util.List;

/**
 * @author Greg Marut
 */
public class ContentService
{
	//holds the database service object
	private final DBService dbService;

	private final ContentAccumulator<String> stringAccumulator;
	private final ContentAccumulator<List<String>> stringListAccumulator;

	public ContentService(final DBService dbService)
	{
		this.dbService = dbService;

		this.stringAccumulator = new ContentAccumulator<String>(dbService, StandardType.String, new StringConverter());
		this.stringListAccumulator = new ContentAccumulator<List<String>>(dbService, StandardType.StringArray, new
			StringListConverter());
	}

	public String readStringContent(final String content) throws ContentException
	{
		//check to see if this string input is a variable
		if(BlueprintVariableUtil.isVariable(content))
		{
			//read the content from the database
			return stringAccumulator.readContent(content);
		}
		else
		{
			//this is a literal value
			return content;
		}
	}

	public void writeStringContent(final String key, final String value) throws ContentException
	{
		stringAccumulator.writeContent(key, value);
	}

	public List<String> readStringListContent(final String key) throws ContentException
	{
		return stringListAccumulator.readContent(key);
	}

	public void writeStringListContent(final String key, final List<String> value) throws ContentException
	{
		stringListAccumulator.writeContent(key, value);
	}

	public DBService getDbService()
	{
		return dbService;
	}
}
