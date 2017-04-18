package com.threatconnect.sdk.parser;

import java.util.List;

import com.threatconnect.sdk.model.Item;

public interface Parser<I extends Item>
{
	/**
	 * Parses the data from the source and returns a list of items
	 * 
	 * @return the list of items that were parsed
	 * @throws ParserException
	 * indicates that a parser was unable to complete parsing the source data
	 */
	public List<I> parseData() throws ParserException;
	
	/**
	 * Returns a unique user-friendly name to identify this parser.
	 * 
	 * @return the unique name to identify this parser
	 */
	public String getUniqueName();
}
