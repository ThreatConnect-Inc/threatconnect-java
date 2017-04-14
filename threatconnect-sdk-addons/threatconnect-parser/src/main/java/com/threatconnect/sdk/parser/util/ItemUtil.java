package com.threatconnect.sdk.parser.util;

import com.threatconnect.sdk.parser.model.Group;
import com.threatconnect.sdk.parser.model.Indicator;
import com.threatconnect.sdk.parser.model.Item;
import com.threatconnect.sdk.parser.model.ItemType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemUtil
{
	private ItemUtil()
	{
		
	}
	
	private static List<Item> getOrCreate(final Map<Item, List<Item>> duplicateMap, final Item item)
	{
		//retrieve the list of items from the duplicate map
		List<Item> duplicateItems = duplicateMap.get(item);
		
		//check to see if the list is null
		if (null == duplicateItems)
		{
			duplicateItems = new ArrayList<Item>();
			duplicateMap.put(item, duplicateItems);
		}
		
		return duplicateItems;
	}
	
	/**
	 * Given a list of items, this recursively follows the associated items looking for groups and
	 * indicators and assigns them to their respective sets
	 *
	 * @param items      the list of items to search
	 * @param groups     the set that will be used to store the groups
	 * @param indicators the set that will be used to store the indicators
	 */
	public static void separateGroupsAndIndicators(final Collection<? extends Item> items,
		final Set<Group> groups, final Set<Indicator> indicators)
	{
		// for every item in this list
		for (Item item : items)
		{
			if (ItemType.GROUP.equals(item.getItemType()))
			{
				final Group group = (Group) item;
				
				// add this group
				if (groups.add(group))
				{
					// continue following the associated items
					separateGroupsAndIndicators(item.getAssociatedItems(), groups, indicators);
				}
			}
			else if (ItemType.INDICATOR.equals(item.getItemType()))
			{
				final Indicator indicator = (Indicator) item;
				
				// add this indicator
				if (indicators.add(indicator))
				{
					// continue following the associated items
					separateGroupsAndIndicators(item.getAssociatedItems(), groups, indicators);
				}
			}
		}
	}
	
	/**
	 * Extracts a specific type of indicator from the set
	 *
	 * @param indicators
	 * @param clazz
	 */
	public static <T extends Indicator> Set<T> extractIndicatorSet(final Collection<Indicator> indicators,
		final Class<T> clazz)
	{
		final Set<T> results = new HashSet<T>();
		
		//for each of the indicators
		for (Indicator indicator : indicators)
		{
			//check to see if this indicator is of this type
			if (clazz.isAssignableFrom(indicator.getClass()))
			{
				results.add((T) indicator);
			}
		}
		
		return results;
	}
}
