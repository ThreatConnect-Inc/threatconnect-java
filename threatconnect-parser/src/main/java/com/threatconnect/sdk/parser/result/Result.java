package com.threatconnect.sdk.parser.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.threatconnect.sdk.parser.model.Item;

public class Result
{
	private final List<Item> items;
	
	public Result(final Item... items)
	{
		// holds the temp list of items
		List<Item> itemList = new ArrayList<Item>();
		
		// make sure the items array is not null
		if (null != items)
		{
			// for each of the items
			for (Item item : items)
			{
				// make sure this item is not null
				if (null != item)
				{
					itemList.add(item);
				}
			}
		}
		
		this.items = Collections.unmodifiableList(itemList);
	}
	
	public List<Item> getItems()
	{
		return items;
	}
}
