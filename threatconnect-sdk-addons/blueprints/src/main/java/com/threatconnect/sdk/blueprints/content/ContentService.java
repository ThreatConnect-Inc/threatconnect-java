package com.threatconnect.sdk.blueprints.content;

import com.threatconnect.sdk.blueprints.content.accumulator.ContentAccumulator;
import com.threatconnect.sdk.blueprints.content.accumulator.ContentException;
import com.threatconnect.sdk.blueprints.db.DBService;

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

		this.stringAccumulator = new ContentAccumulator<String>(dbService, StandardType.String);
		this.stringListAccumulator = new ContentAccumulator<List<String>>(dbService, StandardType.StringArray);
	}

	public String readStringContent(final String key) throws ContentException
	{
		return stringAccumulator.readContent(key);
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
