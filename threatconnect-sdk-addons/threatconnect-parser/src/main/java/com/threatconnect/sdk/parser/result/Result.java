package com.threatconnect.sdk.parser.result;

import java.util.ArrayList;
import java.util.List;

import com.threatconnect.sdk.parser.model.Item;

public class Result<I extends Item>
{
	private final List<I> items;
	
	@SafeVarargs
	public Result(final I... items)
	{
		// holds the temp list of items
		this.items = new ArrayList<I>();
		
		// make sure the items array is not null
		if (null != items)
		{
			// for each of the items
			for (I item : items)
			{
				// make sure this item is not null
				if (null != item)
				{
					this.items.add(item);
				}
			}
		}
	}
	
	public Result(List<I> items)
	{
		this.items = items;
	}
	
	public List<I> getItems()
	{
		return items;
	}
}
