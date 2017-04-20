package com.threatconnect.sdk.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.parser.source.DataSource;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.List;

public abstract class AbstractGsonParser<I extends Item> extends AbstractParser<I>
{
	public AbstractGsonParser(final DataSource dataSource)
	{
		super(dataSource);
	}
	
	@Override
	public List<I> parseData() throws ParserException
	{
		try
		{
			// read the json as a string and allow it to be preprocessed if needed
			String rawJson = IOUtils.toString(getDataSource().read());
			String json = preProcessJson(rawJson);
			
			// parse the json into an element
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(json);
			
			// process the json and retrieve the result
			return processJson(element);
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
	 * @param element
	 * @return
	 * @throws ParserException
	 */
	protected abstract List<I> processJson(JsonElement element) throws ParserException;
}
