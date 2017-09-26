package com.threatconnect.sdk.model.util;

import com.threatconnect.sdk.model.Attribute;
import com.threatconnect.sdk.model.Item;

/**
 * @author Greg Marut
 */
public class AttributeUtil
{
	private AttributeUtil()
	{
	
	}
	
	/**
	 * Adds an attribute to the item provided that the value is not null or empty
	 *
	 * @param item  the item to add this attribute to
	 * @param type  the type of attribute to add
	 * @param value the value of the attribute
	 * @return the attribute that was created or null if the value was null or empty
	 */
	public static Attribute addAttributeIfExists(final Item item, final String type, final String value)
	{
		return addAttributeIfExists(item, type, value, null);
	}
	
	/**
	 * Adds an attribute to the item provided that the value is not null or empty
	 *
	 * @param item  the item to add this attribute to
	 * @param type  the type of attribute to add
	 * @param value the value of the attribute
	 * @return the attribute that was created or null if the value was null or empty
	 */
	public static Attribute addAttributeIfExists(final Item item, final String type, final String value,
		final Boolean displayed)
	{
		if (null != value && !value.trim().isEmpty())
		{
			return addAttribute(item, type, value, displayed);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Adds an attribute to the item
	 *
	 * @param item  the item to add this attribute to
	 * @param type  the type of attribute to add
	 * @param value the value of the attribute
	 * @return the attribute that was created
	 */
	public static Attribute addAttribute(final Item item, final String type, final String value)
	{
		return addAttribute(item, type, value, null);
	}
	
	/**
	 * Adds an attribute to the item
	 *
	 * @param item      the item to add this attribute to
	 * @param type      the type of attribute to add
	 * @param value     the value of the attribute
	 * @param displayed determines where this attribute is displayed
	 * @return the attribute that was created
	 */
	public static Attribute addAttribute(final Item item, final String type, final String value,
		final Boolean displayed)
	{
		// create a new attribute
		Attribute attribute = new Attribute();
		attribute.setType(type);
		attribute.setValue(value);
		attribute.setDisplayed(displayed);
		
		// add the attribute to the item
		item.getAttributes().add(attribute);
		
		return attribute;
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
