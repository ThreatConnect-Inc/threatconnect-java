package com.threatconnect.sdk.parser.util.attribute;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVReader;
import com.threatconnect.sdk.parser.model.GroupType;
import com.threatconnect.sdk.parser.model.IndicatorType;

public class AttributeDefinitionFileReader
{
	public static final String ATTRIBUTES_FILE = "attributes.csv";
	public static final String TYPE_DELIMITER = "|";
	public static final int COLUMNS = 5;
	
	private static final Logger logger = LoggerFactory.getLogger(AttributeDefinitionFileReader.class);
	
	private static AttributeDefinitionFileReader instance;
	
	// holds the map of attributes
	private final Map<String, AttributeDefinition> attributeDefinitions;
	
	private AttributeDefinitionFileReader()
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
							attributeDefinition.setMaxSize(Integer.parseInt(nextLine[3]));
							
							setTypes(attributeDefinition, nextLine[4]);
							
							// for each of the group types
							for (GroupType groupType : attributeDefinition.getGroupTypes())
							{
								// add an entry for this attribute and type combination
								final String key = getKey(attributeDefinition.getName(), groupType);
								attributeDefinitions.put(key, attributeDefinition);
							}
							
							// for each of the indicator types
							for (IndicatorType indicatorType : attributeDefinition.getIndicatorTypes())
							{
								// add an entry for this attribute and type combination
								final String key = getKey(attributeDefinition.getName(), indicatorType);
								attributeDefinitions.put(key, attributeDefinition);
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
		return getClass().getResourceAsStream(ATTRIBUTES_FILE);
	}
	
	private void setTypes(final AttributeDefinition attributeDefinition, final String types)
		throws InvalidAttributeDefinitionTypesException
	{
		String[] typesArray = types.split(Pattern.quote(TYPE_DELIMITER));
		
		// for each of the types
		for (String type : typesArray)
		{
			IndicatorType indicatorType = IndicatorType.valueOf(type.toUpperCase());
			GroupType groupType = GroupType.valueOf(type.toUpperCase());
			
			// check to see if the indicator type is not null
			if (null != indicatorType)
			{
				attributeDefinition.getIndicatorTypes().add(indicatorType);
			}
			// check to see if the group type is not null
			else if (null != groupType)
			{
				attributeDefinition.getGroupTypes().add(groupType);
			}
			// this is not a valid type
			else
			{
				throw new InvalidAttributeDefinitionTypesException(type + " is not a valid group or indicator type");
			}
		}
	}
	
	public boolean containsAttribute(final String attributeName, final IndicatorType indicatorType)
	{
		return (null != getAttribute(attributeName, indicatorType));
	}
	
	public boolean containsAttribute(final String attributeName, final GroupType groupType)
	{
		return (null != getAttribute(attributeName, groupType));
	}
	
	public AttributeDefinition getAttribute(final String attributeName, final IndicatorType indicatorType)
	{
		return attributeDefinitions.get(getKey(attributeName, indicatorType));
	}
	
	public AttributeDefinition getAttribute(final String attributeName, final GroupType groupType)
	{
		return attributeDefinitions.get(getKey(attributeName, groupType));
	}
	
	private String getKey(final String attributeName, final IndicatorType indicatorType)
	{
		return attributeName + "|I|" + indicatorType.toString();
	}
	
	private String getKey(final String attributeName, final GroupType groupType)
	{
		return attributeName + "|G|" + groupType.toString();
	}
	
	public synchronized AttributeDefinitionFileReader getInstance()
	{
		// check to see if this instance is null
		if (null == instance)
		{
			instance = new AttributeDefinitionFileReader();
		}
		
		return instance;
	}
}
