package com.threatconnect.sdk.parser.util;

import com.threatconnect.sdk.parser.model.Item;

/**
 * @author Greg Marut
 */
public class AttributeUtil
{
	private AttributeUtil()
	{
	}
	
	/**
	 * Synchronizes the attributes between both items
	 *
	 * @param item1
	 * @param item2
	 */
	public static void synchronizeAttributes(final Item item1, final Item item2)
	{
		mergeAttributes(item1, item2);
		mergeAttributes(item2, item1);
	}
	
	/**
	 * Merges all non-duplicate attributes from the source item to the target item
	 *
	 * @param source
	 * @param target
	 */
	public static void mergeAttributes(final Item source, final Item target)
	{
		target.getAttributes().addAll(source.getAttributes());
	}
}
