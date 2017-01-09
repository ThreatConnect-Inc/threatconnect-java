package com.threatconnect.sdk.parser;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.threatconnect.sdk.parser.model.Item;
import com.threatconnect.sdk.parser.source.DataSource;

public abstract class AbstractJsonPathParser<I extends Item> extends AbstractParser<I>
{
	public AbstractJsonPathParser(final DataSource dataSource)
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
			DocumentContext context = JsonPath.parse(json);
			
			// process the json and retrieve the result
			return processJson(context);
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
	 * @return
	 * @throws ParserException
	 */
	protected abstract List<I> processJson(DocumentContext context) throws ParserException;
}
