package com.threatconnect.sdk.parser.util;

import com.threatconnect.sdk.parser.model.Item;

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
}
