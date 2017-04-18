package com.threatconnect.sdk.model.util;

import com.threatconnect.sdk.model.Group;
import com.threatconnect.sdk.model.Item;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupUtil
{
	private GroupUtil()
	{
	
	}
	
	/**
	 * Associates all items with the group. If an item already exists, a unique set of tags and attributes will be
	 * merged with the duplicated items
	 *
	 * @param group the group to use
	 * @param items the list of items to associate with this group
	 */
	public static void mergeAssociatedItems(final Group group, final List<? extends Item> items)
	{
		//for each of the items in the list
		for (Item item : items)
		{
			//attempt to add this item to the list and check to see if it already exists
			if (!group.getAssociatedItems().add(item))
			{
				//lookup the duplicate from the set
				Item existingItem = extractExistingItemFromSet(group.getAssociatedItems(), item);
				
				//make sure these items are not the exact same object reference
				if (existingItem != item)
				{
					//merge the tags and attributes from this item to the item already in the set
					TagUtil.mergeTags(item, existingItem);
					AttributeUtil.mergeAttributes(item, existingItem);
				}
			}
		}
	}
	
	/**
	 * Finds and retrieves the concrete item that is a duplicate of the item given.
	 *
	 * @param set
	 * @param item
	 * @return
	 */
	private static Item extractExistingItemFromSet(final Set<Item> set, final Item item)
	{
		//make sure the set contains this item
		if (set.contains(item))
		{
			//duplicate the set and retain only the selected item
			Set<Item> tempSet = new HashSet<Item>(set);
			tempSet.retainAll(Collections.singletonList(item));
			
			//return the sole item from this list
			return tempSet.iterator().next();
		}
		else
		{
			throw new IllegalArgumentException("set does not contain this item");
		}
	}
}
