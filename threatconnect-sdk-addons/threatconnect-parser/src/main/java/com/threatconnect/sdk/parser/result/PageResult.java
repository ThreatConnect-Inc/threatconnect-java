package com.threatconnect.sdk.parser.result;

import java.util.List;

import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.parser.source.DataSource;

public class PageResult<I extends Item> extends Result<I>
{
	// holds the data source for the next page to parse
	private DataSource nextDataSource;
	
	@SafeVarargs
	public PageResult(final I... items)
	{
		super(items);
	}
	
	public PageResult(final List<I> items)
	{
		super(items);
	}
	
	public DataSource getNextDataSource()
	{
		return nextDataSource;
	}
	
	public void setNextDataSource(DataSource nextDataSource)
	{
		this.nextDataSource = nextDataSource;
	}
}
