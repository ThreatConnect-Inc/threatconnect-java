package com.threatconnect.sdk.parser.result;

import java.util.ArrayList;
import java.util.List;

import com.threatconnect.sdk.parser.model.Item;

public class PageResult
{
	// holds the list of items found on the document
	private final List<Item> items;
	
	// holds the url for the next page to parse
	private String nextPageUrl;
	
	public PageResult()
	{
		this.items = new ArrayList<Item>();
	}
	
	public PageResult(final List<Item> items)
	{
		this.items = new ArrayList<Item>(items);
	}
	
	public List<Item> getItems()
	{
		return items;
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
