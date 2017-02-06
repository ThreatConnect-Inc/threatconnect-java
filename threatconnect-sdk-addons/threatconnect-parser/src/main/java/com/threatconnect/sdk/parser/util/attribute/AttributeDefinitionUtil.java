package com.threatconnect.sdk.parser.util.attribute;

import com.opencsv.CSVReader;
import com.threatconnect.sdk.parser.model.Group;
import com.threatconnect.sdk.parser.model.GroupType;
import com.threatconnect.sdk.parser.model.Indicator;
import com.threatconnect.sdk.parser.model.Item;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AttributeDefinitionUtil
{
	public static final String ATTRIBUTES_FILE = "attributes.csv";
	public static final String TYPE_DELIMITER = "|";
	public static final int COLUMNS = 5;
	
	private static final Logger logger = LoggerFactory.getLogger(AttributeDefinitionUtil.class);
	
	private static AttributeDefinitionUtil instance;
	
	// holds the map of attributes
	private final Map<String, AttributeDefinition> attributeDefinitions;
	
	private AttributeDefinitionUtil()
	{
		this.attributeDefinitions = new HashMap<String, AttributeDefinition>();
		loadAttributesCSV();
	}
	
	private void loadAttributesCSV()
	{
		// holds the readers for the input stream
		InputStream inputStream = null;
		InputStreamReader isr = null;
		CSVReader reader = null;
		
		try
		{
			// load the csv file
			inputStream = findAttributesFile();
			
			// check to see if the input stream is not null
			if (null != inputStream)
			{
				logger.info("Loading {}", ATTRIBUTES_FILE);
				
				// load the file into reader objects
				isr = new InputStreamReader(inputStream);
				reader = new CSVReader(isr);
				
				String[] nextLine;
				while ((nextLine = reader.readNext()) != null)
				{
					// make sure that there are exactly 5 parts
					if (nextLine.length != COLUMNS)
					{
						final String message =
							"Expected " + COLUMNS + " columns, found " + nextLine.length + " - "
								+ Arrays.toString(nextLine);
						throw new InvalidAttributeDefinitionFileException(message);
					}
					else
					{
						// validate that no field is missing or empty
						for (int i = 0; i < COLUMNS; i++)
						{
							if (null == nextLine[i] || nextLine[i].trim().isEmpty())
							{
								throw new InvalidAttributeDefinitionFileException(
									"Expected column is either missing or empty - " + Arrays.toString(nextLine));
							}
						}
						
						// create the attribute object
						AttributeDefinition attributeDefinition = new AttributeDefinition();
						attributeDefinition.setName(nextLine[0]);
						attributeDefinition.setDescription(nextLine[1]);
						attributeDefinition.setErrorMessage(nextLine[2]);
						
						try
						{
							attributeDefinition.setMaxSize(Integer.parseInt(nextLine[3].trim()));
							
							setTypes(attributeDefinition, nextLine[4]);
							
							// for each of the group types
							for (GroupType groupType : attributeDefinition.getGroupTypes())
							{
								// add an entry for this attribute and type combination
								final String key = getKey(attributeDefinition.getName(), groupType);
								attributeDefinitions.put(key, attributeDefinition);
								logger.debug("Loaded attribute definition for {} - {}", attributeDefinition.getName(),
									groupType);
							}
							
							// for each of the indicator types
							for (String indicatorType : attributeDefinition.getIndicatorTypes())
							{
								// add an entry for this attribute and type combination
								final String key = getKey(attributeDefinition.getName(), indicatorType);
								attributeDefinitions.put(key, attributeDefinition);
								logger.debug("Loaded attribute definition for {} - {}", attributeDefinition.getName(),
									indicatorType);
							}
						}
						catch (NumberFormatException e)
						{
							throw new InvalidAttributeDefinitionFileException("Invalid max size number. " + nextLine[3]
								+ " is not a valid integer. - " + Arrays.toString(nextLine));
						}
						catch (InvalidAttributeDefinitionTypesException e)
						{
							throw new InvalidAttributeDefinitionFileException(
								e.getMessage() + " - " + Arrays.toString(nextLine));
						}
					}
				}
			}
			else
			{
				logger.info("No {} file was found.", ATTRIBUTES_FILE);
			}
		}
		catch (IOException e)
		{
			throw new InvalidAttributeDefinitionFileException(e);
		}
		finally
		{
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(isr);
			IOUtils.closeQuietly(reader);
		}
	}
	
	private InputStream findAttributesFile()
	{
		try
		{
			return new FileInputStream(new File(ATTRIBUTES_FILE));
		}
		catch (FileNotFoundException e)
		{
			return null;
		}
	}
	
	private void setTypes(final AttributeDefinition attributeDefinition, final String types)
		throws InvalidAttributeDefinitionTypesException
	{
		String[] typesArray = types.split(Pattern.quote(TYPE_DELIMITER));
		
		// for each of the types
		for (String type : typesArray)
		{
			GroupType groupType;
			
			try
			{
				// attempt to load this group type
				groupType = GroupType.valueOf(type.trim().toUpperCase());
			}
			catch (IllegalArgumentException e)
			{
				// its ok if it does not exist
				groupType = null;
			}
			
			// check to see if the group type is not null
			if (null != groupType)
			{
				attributeDefinition.getGroupTypes().add(groupType);
			}
			// this must be an indicator type (or custom indicator)
			else
			{
				String indicatorType = type.trim().toUpperCase();
				attributeDefinition.getIndicatorTypes().add(indicatorType);
			}
		}
	}
	
	public boolean containsAttribute(final String attributeName, final Item item)
	{
		// check to see if this item is a group
		if (item instanceof Group)
		{
			Group group = (Group) item;
			return containsAttribute(attributeName, group.getGroupType());
		}
		// check to see if this item is an indicator
		else if (item instanceof Indicator)
		{
			Indicator indicator = (Indicator) item;
			return containsAttribute(attributeName, indicator.getIndicatorType());
		}
		// this should technically never happen unless a new item type is created
		else
		{
			return false;
		}
	}
	
	public boolean containsAttribute(final String attributeName, final String indicatorType)
	{
		return (null != getAttribute(attributeName, indicatorType));
	}
	
	public boolean containsAttribute(final String attributeName, final GroupType groupType)
	{
		return (null != getAttribute(attributeName, groupType));
	}
	
	public AttributeDefinition getAttribute(final String attributeName, final Item item)
	{
		// check to see if this item is a group
		if (item instanceof Group)
		{
			Group group = (Group) item;
			return getAttribute(attributeName, group.getGroupType());
		}
		// check to see if this item is an indicator
		else if (item instanceof Indicator)
		{
			Indicator indicator = (Indicator) item;
			return getAttribute(attributeName, indicator.getIndicatorType());
		}
		// this should technically never happen unless a new item type is created
		else
		{
			return null;
		}
	}
	
	public AttributeDefinition getAttribute(final String attributeName, final String indicatorType)
	{
		return attributeDefinitions.get(getKey(attributeName, indicatorType));
	}
	
	public AttributeDefinition getAttribute(final String attributeName, final GroupType groupType)
	{
		return attributeDefinitions.get(getKey(attributeName, groupType));
	}
	
	private String getKey(final String attributeName, final String indicatorType)
	{
		return attributeName + "|I|" + indicatorType.toString();
	}
	
	private String getKey(final String attributeName, final GroupType groupType)
	{
		return attributeName + "|G|" + groupType.toString();
	}
	
	public static synchronized AttributeDefinitionUtil getInstance()
	{
		// check to see if this instance is null
		if (null == instance)
		{
			instance = new AttributeDefinitionUtil();
		}
		
		return instance;
	}
}
