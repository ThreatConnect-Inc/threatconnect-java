package com.threatconnect.sdk.parser.util;

import java.util.List;

import com.threatconnect.sdk.parser.model.Attribute;
import com.threatconnect.sdk.parser.model.Item;

/**
 * A utility class that adds some of the commonly used attributes to an item
 * 
 * @author Greg Marut
 */
public class AttributeHelper
{
	public static final String ATTRIBUTE_AAC = "Additional Analysis and Context";
	public static final String ATTRIBUTE_SOURCE = "Source";
	
	/**
	 * Adds the source attribute to all of the items in the list
	 * 
	 * @param items
	 * the list of items to add the source attribute to
	 * @param feedType
	 * the source feed type
	 * @param recursive
	 * whether or not this should be added to all associated items as
	 * well
	 */
	public static void addSourceAttributeToAll(final List<Item> items, final String source, final boolean recursive)
	{
		// for each of the items in the list
		for (Item item : items)
		{
			// add the source attribute to this item
			addSourceAttribute(item, source, true);
			
			// check to see if this is recursive and that the item has
			// associated items
			if (recursive && !item.getAssociatedItems().isEmpty())
			{
				// add the source to all child items as well
				addSourceAttributeToAll(item.getAssociatedItems(), source, recursive);
			}
		}
	}
	
	/**
	 * Adds a source attribute with the given value to the item
	 * 
	 * @param item
	 * @param value
	 */
	public static void addSourceAttribute(final Item item, final String value)
	{
		addSourceAttribute(item, value, null);
	}
	
	/**
	 * Adds a source attribute with the given value to the item
	 * 
	 * @param item
	 * @param value
	 */
	public static void addSourceAttribute(final Item item, final String value, final Boolean displayed)
	{
		// create a new attribute
		Attribute attribute = new Attribute();
		attribute.setKey(ATTRIBUTE_SOURCE);
		attribute.setValue(value);
		attribute.setDisplayed(displayed);
		
		// add the attribute to the item
		item.getAttributes().add(attribute);
	}
	
	/**
	 * Adds an Additional Analysis and Context attribute to the item
	 * 
	 * @param item
	 * @param value
	 */
	public static void addAdditionalAnalysisAndContext(final Item item, final String value)
	{
		// create a new attribute
		Attribute attribute = new Attribute();
		attribute.setKey(ATTRIBUTE_AAC);
		attribute.setValue(value);
		
		// add the attribute to the item
		item.getAttributes().add(attribute);
	}
}
