package com.threatconnect.sdk.parser.util;

import java.util.List;
import java.util.Set;

import com.threatconnect.sdk.parser.model.Group;
import com.threatconnect.sdk.parser.model.Indicator;
import com.threatconnect.sdk.parser.model.Item;
import com.threatconnect.sdk.parser.model.ItemType;

public class ItemUtil
{
	private ItemUtil()
	{
	
	}
	
	/**
	 * Given a list of items, this recursively follows the associated items looking for groups and
	 * indicators and assigns them to their respective sets
	 * 
	 * @param items
	 * the list of items to search
	 * @param groups
	 * the set that will be used to store the groups
	 * @param indicators
	 * the set that will be used to store the indicators
	 */
	public static void seperateGroupsAndIndicators(final List<? extends Item> items,
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
					seperateGroupsAndIndicators(item.getAssociatedItems(), groups, indicators);
				}
			}
			else if (ItemType.INDICATOR.equals(item.getItemType()))
			{
				final Indicator indicator = (Indicator) item;
				
				// add this indicator
				if (indicators.add(indicator))
				{
					// continue following the associated items
					seperateGroupsAndIndicators(item.getAssociatedItems(), groups, indicators);
				}
			}
		}
	}
}
