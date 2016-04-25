package com.threatconnect.sdk.parser;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.threatconnect.sdk.parser.model.Item;
import com.threatconnect.sdk.parser.source.DataSource;

public abstract class Parser<I extends Item>
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final DataSource dataSource;
	
	public Parser(final DataSource dataSource)
	{
		// make sure the data source is not null
		if (null == dataSource)
		{
			throw new IllegalArgumentException("dataSource cannot be null");
		}
		
		this.dataSource = dataSource;
	}
	
	public DataSource getDataSource()
	{
		return dataSource;
	}
	
	protected Logger getLogger()
	{
		return logger;
	}
	
	/**
	 * Returns a unique user-friendly name to identify this parser.
	 * 
	 * @return the unique name to identify this parser
	 */
	public String getUniqueName()
	{
		return getClass().getSimpleName();
	}
	
	/**
	 * Parses the data from the source and returns a list of items
	 * 
	 * @return the list of items that were parsed
	 * @throws ParserException
	 */
	public abstract List<I> parseData() throws ParserException;
}
