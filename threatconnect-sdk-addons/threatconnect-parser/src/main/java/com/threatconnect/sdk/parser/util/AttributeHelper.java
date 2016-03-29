package com.threatconnect.sdk.parser.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.threatconnect.sdk.parser.model.Attribute;
import com.threatconnect.sdk.parser.model.Group;
import com.threatconnect.sdk.parser.model.Item;

/**
 * A utility class that adds some of the commonly used attributes to an item
 * 
 * @author Greg Marut
 */
public class AttributeHelper
{
	public static final String ATTRIBUTE_AAC = "Additional Analysis and Context";
	public static final String ATTRIBUTE_DESCRIPTION = "Description";
	public static final String ATTRIBUTE_SOURCE = "Source";
	public static final String ATTRIBUTE_SOURCE_DATE_TIME = "Source Date Time";
	public static final String ATTRIBUTE_TTP = "Tactics, Techniques, and Procedures";
	
	public static final String SOURCE_DATE_TIME_FORMAT = "MM-dd-yyyy HH:mm zzz";
	
	/**
	 * Adds the source attribute to all of the items in the list
	 * 
	 * @param items
	 * the list of items to add the source attribute to
	 * @param source
	 * the source to use
	 * @param recursive
	 * whether or not this should be added to all associated items as
	 * well
	 */
	public static void addSourceAttributeToAll(final List<? extends Item> items, final String source,
		final boolean recursive)
	{
		// for each of the items in the list
		for (Item item : items)
		{
			// add the source attribute to this item
			addSourceAttribute(item, source, true);
			
			// check to see if this is recursive and that the item has
			// associated items
			if (recursive && item instanceof Group)
			{
				Group group = (Group) item;
				if (!group.getAssociatedItems().isEmpty())
				{
					// add the source to all child items as well
					addSourceAttributeToAll(group.getAssociatedItems(), source, recursive);
				}
			}
		}
	}
	
	/**
	 * Adds a description attribute with the given value to the item
	 * 
	 * @param item the item to add this attribute to
	 * @param value the value of the attribute
	 * @return the attribute that was created
	 */
	public static Attribute addDescriptionAttribute(final Item item, final String value)
	{
		return addDescriptionAttribute(item, value, null);
	}
	
	/**
	 * Adds a description attribute with the given value to the item
	 * 
	 * @param item the item to add this attribute to
	 * @param value the value of the attribute
	 * @param displayed determines where this attribute is displayed
	 * @return the attribute that was created
	 */
	public static Attribute addDescriptionAttribute(final Item item, final String value, final Boolean displayed)
	{
		return addAttribute(item, ATTRIBUTE_DESCRIPTION, value, displayed);
	}
	
	/**
	 * Adds a source attribute with the given value to the item
	 * 
	 * @param item the item to add this attribute to
	 * @param value the value of the attribute
	 * @return the attribute that was created
	 */
	public static Attribute addSourceAttribute(final Item item, final String value)
	{
		return addSourceAttribute(item, value, null);
	}
	
	/**
	 * Adds a source attribute with the given value to the item
	 * 
	 * @param item the item to add this attribute to
	 * @param value the value of the attribute
	 * @param displayed determines where this attribute is displayed
	 * @return the attribute that was created
	 */
	public static Attribute addSourceAttribute(final Item item, final String value, final Boolean displayed)
	{
		return addAttribute(item, ATTRIBUTE_SOURCE, value, displayed);
	}
	
	/**
	 * Adds a source date time attribute with the given value to the item
	 * 
	 * @param item the item to add this attribute to
	 * @param date the date object to use
	 * @return the attribute that was created
	 */
	public static Attribute addSourceDateTimeAttribute(final Item item, final Date date)
	{
		DateFormat dateFormat = new SimpleDateFormat(SOURCE_DATE_TIME_FORMAT);
		return addAttribute(item, ATTRIBUTE_SOURCE_DATE_TIME, dateFormat.format(date));
	}
	
	/**
	 * Adds an Additional Analysis and Context attribute to the item
	 * 
	 * @param item the item to add this attribute to
	 * @param value the value of the attribute
	 * @return the attribute that was created
	 */
	public static Attribute addAdditionalAnalysisAndContext(final Item item, final String value)
	{
		return addAttribute(item, ATTRIBUTE_AAC, value);
	}
	
	/**
	 * Adds a Tactics, Techniques, and Procedures attribute to the item
	 * 
	 * @param item the item to add this attribute to
	 * @param value the value of the attribute
	 * @return the attribute that was created
	 */
	public static Attribute addTacticsTechniquesProcedures(final Item item, final String value)
	{
		return addAttribute(item, ATTRIBUTE_TTP, value);
	}
	
	/**
	 * Adds an attribute to the item
	 * 
	 * @param item the item to add this attribute to
	 * @param type the type of attribute to add
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
	 * @param item the item to add this attribute to
	 * @param type the type of attribute to add
	 * @param value the value of the attribute
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
}
