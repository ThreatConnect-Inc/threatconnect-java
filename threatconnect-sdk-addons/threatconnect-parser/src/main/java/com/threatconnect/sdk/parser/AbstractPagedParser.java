package com.threatconnect.sdk.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.threatconnect.sdk.parser.model.Item;
import com.threatconnect.sdk.parser.result.PageResult;
import com.threatconnect.sdk.parser.source.DataSource;

/**
 * Represents a parser that can contain multiple pages of data to parse
 * 
 * @author Greg Marut
 */
public abstract class AbstractPagedParser<I extends Item> extends AbstractParser<I>
{
	public AbstractPagedParser(final DataSource dataSource)
	{
		super(dataSource);
	}
	
	@Override
	public List<I> parseData() throws ParserException
	{
		// holds the list of items to return
		List<I> items = new ArrayList<I>();
		
		// holds the set of all of the datasources that have been parsed so that we can ensure that
		// an infinite loop does not occur
		Set<DataSource> parsedDataSources = new HashSet<DataSource>();
		
		// holds the next data source to parse
		DataSource nextDataSource = getDataSource();
		
		// while the next datasource is not null and if the set does not contain this datasource
		while (null != nextDataSource && !parsedDataSources.contains(nextDataSource))
		{
			// hold onto the current datasource and set the next datasource to null
			DataSource currentDataSource = nextDataSource;
			nextDataSource = null;
			
			// parse the page
			getLogger().info("Parsing: {}", currentDataSource.toString());
			PageResult<I> pageResult = parsePage(currentDataSource);
			parsedDataSources.add(currentDataSource);
			
			// make sure the page is not null
			if (null != pageResult)
			{
				// add all the items to the list
				items.addAll(pageResult.getItems());
				
				// make sure the next data source is not null
				if (null != pageResult.getNextDataSource())
				{
					nextDataSource = pageResult.getNextDataSource();
				}
			}
		}
		
		return items;
	}
	
	protected abstract PageResult<I> parsePage(final DataSource dataSource) throws ParserException;
}
