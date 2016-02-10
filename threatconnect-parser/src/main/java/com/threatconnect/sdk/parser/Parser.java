package com.threatconnect.sdk.parser;

import java.util.Date;
import java.util.List;

import com.threatconnect.sdk.parser.model.Item;

public interface Parser
{
	/**
	 * Parses the data from the source and returns a list of items
	 * 
	 * @param startDate
	 * If startDate is null, there is no date restriction, otherwise, only include records from the
	 * start date on.
	 * @return
	 * @throws ParserException
	 */
	public List<Item> parseData(Date startDate) throws ParserException;
	
	/**
	 * Returns a unique user-friendly name to identify this parser.
	 * 
	 * @return
	 */
	public String getUniqueName();
}
