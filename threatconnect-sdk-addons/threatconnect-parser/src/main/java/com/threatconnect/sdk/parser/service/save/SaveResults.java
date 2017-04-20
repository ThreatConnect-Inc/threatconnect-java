package com.threatconnect.sdk.parser.service.save;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.ItemType;

public class SaveResults
{
	// holds the map that counts the failed items by type
	private final Map<ItemType, Integer> failedItems;
	
	public SaveResults()
	{
		failedItems = new HashMap<ItemType, Integer>();
	}
	
	public Map<ItemType, Integer> getFailedItems()
	{
		return failedItems;
	}
	
	public void addFailedItems(final SaveResults saveResults)
	{
		addFailedItems(saveResults.getFailedItems());
	}
	
	public void addFailedItems(final Map<ItemType, Integer> failedItems)
	{
		synchronized (this.failedItems)
		{
			// for each of the failed items
			for (Entry<ItemType, Integer> failedItem : failedItems.entrySet())
			{
				// update the total number of failed items for this type
				Integer count = this.failedItems.get(failedItem.getKey());
				this.failedItems.put(failedItem.getKey(),
					(null != count ? count + failedItem.getValue() : failedItem.getValue()));
			}
		}
	}
	
	public void addFailedItems(final Item... items)
	{
		// for each of the items in the list
		for (Item item : items)
		{
			addFailedItems(item.getItemType(), 1);
		}
	}
	
	public void addFailedItems(final ItemType itemType, final int total)
	{
		synchronized (failedItems)
		{
			// update the total number of failed items for this type
			Integer count = failedItems.get(itemType);
			failedItems.put(itemType, (null != count ? count + total : total));
		}
	}
	
	public boolean isSuccessfully()
	{
		for (ItemType itemType : ItemType.values())
		{
			if (countFailedItems(itemType) > 0)
			{
				// this item had a count greater than 0 so therefore, it is not successful
				return false;
			}
		}
		
		// no items had a count greater than 0
		return true;
	}
	
	/**
	 * Counts all of the items of a specific type.
	 * 
	 * @param itemType
	 * the type of item to count
	 * @return the number of failed items
	 */
	public int countFailedItems(final ItemType itemType)
	{
		Integer count = failedItems.get(itemType);
		
		if (null != count)
		{
			return count;
		}
		else
		{
			return 0;
		}
	}
}
