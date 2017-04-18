package com.threatconnect.sdk.parser.util;

import com.threatconnect.sdk.model.Item;

public class TagUtil
{
	public static final int TAG_LENGTH = 35;
	
	public static void addTag(final String tag, final Item item)
	{
		// ensure that the tag is not null
		if (null != tag)
		{
			// check to see if the tag needs to be truncated
			final String t = (tag.length() > TAG_LENGTH ? tag.substring(0, TAG_LENGTH) : tag);
			item.getTags().add(t);
		}
	}
	
	/**
	 * Synchronizes the attributes between both items
	 *
	 * @param item1
	 * @param item2
	 */
	public static void synchronizeTags(final Item item1, final Item item2)
	{
		mergeTags(item1, item2);
		mergeTags(item2, item1);
	}
	
	/**
	 * Merges all non-duplicate tags from the source item to the target item
	 *
	 * @param source
	 * @param target
	 */
	public static void mergeTags(final Item source, final Item target)
	{
		target.getTags().addAll(source.getTags());
	}
}
