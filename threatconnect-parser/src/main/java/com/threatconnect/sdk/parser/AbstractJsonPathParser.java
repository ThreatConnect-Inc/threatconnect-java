package com.threatconnect.sdk.parser;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.threatconnect.sdk.parser.model.Item;

public abstract class AbstractJsonPathParser extends AbstractParser
{
	public AbstractJsonPathParser(final String url)
	{
		super(url);
	}
	
	@Override
	public List<Item> parseData(Date startDate) throws ParserException
	{
		try
		{
			// load the url and read the json as a string
			URL url = new URL(getUrl());
			String json = preProcessJson(IOUtils.toString(url.openStream()));
			
			// parse the json into an element
			DocumentContext context = JsonPath.parse(json);
			
			// process the json and retrieve the result
			return processJson(context, startDate);
		}
		catch (IOException e)
		{
			throw new ParserException(e);
		}
	}
	
	/**
	 * Allows for any preprocessing of the json string if needed before it is parsed
	 * 
	 * @param json
	 * @return
	 */
	protected String preProcessJson(final String json)
	{
		return json;
	}
	
	/**
	 * Process the json context
	 * 
	 * @param context
	 * @param startDate
	 * @return
	 * @throws ParserException
	 */
	protected abstract List<Item> processJson(DocumentContext context, Date startDate)
		throws ParserException;
}
