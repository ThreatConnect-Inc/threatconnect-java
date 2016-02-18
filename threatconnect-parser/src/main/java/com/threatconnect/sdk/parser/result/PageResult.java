package com.threatconnect.sdk.parser.result;

import java.util.List;

import com.threatconnect.sdk.parser.model.Item;

public class PageResult<I extends Item> extends Result<I>
{
	// holds the url for the next page to parse
	private String nextPageUrl;
	
	@SafeVarargs
	public PageResult(final I... items)
	{
		super(items);
	}
	
	public PageResult(final List<I> items)
	{
		super(items);
	}
	
	public String getNextPageUrl()
	{
		return nextPageUrl;
	}
	
	public void setNextPageUrl(String nextPageUrl)
	{
		this.nextPageUrl = nextPageUrl;
	}
}
