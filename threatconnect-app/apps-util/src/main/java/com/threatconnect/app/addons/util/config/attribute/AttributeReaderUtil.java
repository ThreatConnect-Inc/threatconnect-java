package com.threatconnect.app.addons.util.config.attribute;

import com.threatconnect.app.addons.util.config.InvalidCsvLineException;
import com.threatconnect.app.addons.util.config.validation.AttributeValidator;
import com.threatconnect.app.addons.util.config.validation.ValidationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public class AttributeReaderUtil
{
	private static final int INDEX_NAME = 0;
	private static final int INDEX_DESCRIPTION = 1;
	private static final int INDEX_ERROR_MESSAGE = 2;
	private static final int INDEX_MAX_LENGTH = 3;
	private static final int INDEX_TYPES = 4;
	private static final int INDEX_ALLOW_MARKDOWN = 5;
	
	private static final String SPLIT_REGEX = ",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
	private static final String FIELD_DELIMITER = "|";
	
	public static List<Attribute> read(final File csv) throws InvalidCsvLineException, IOException, ValidationException
	{
		try (FileInputStream fileInputStream = new FileInputStream(csv))
		{
			return read(fileInputStream);
		}
	}
	
	public static List<Attribute> read(final InputStream csv) throws InvalidCsvLineException, ValidationException
	{
		//holds the list of attributes to return
		List<Attribute> attributes = new ArrayList<Attribute>();
		
		//create a new scanner object to scan the input
		Scanner in = new Scanner(csv);
		int rowNum = 1;
		
		//holds the attribute validator
		AttributeValidator attributeValidator = new AttributeValidator();
		
		//while there are more lines to parse
		while (in.hasNextLine())
		{
			//read the next line of the input
			String line = in.nextLine();
			
			//make sure the line is not empty
			if (!line.isEmpty())
			{
				//parse the attribute
				Attribute attribute = parseAttribute(line, rowNum);
				
				//validate this attribute
				attributeValidator.validate(attribute);
				
				//add it to the list
				attributes.add(attribute);
			}
			
			//increment the row number
			rowNum++;
		}
		
		return attributes;
	}
	
	private static Attribute parseAttribute(final String csvLine, final int rowNum) throws InvalidCsvLineException
	{
		//split the line into parts
		String[] parts = csvLine.split(SPLIT_REGEX);
		
		//create the new attribute object to return
		Attribute attribute = new Attribute();
		
		//read the name from the csv line
		final String name = get(parts, INDEX_NAME);
		if (null != name)
		{
			attribute.setName(name);
		}
		else
		{
			throw new InvalidCsvLineException("Missing required field \"name\" from line " + rowNum);
		}
		
		//read the description from the csv line
		final String description = get(parts, INDEX_DESCRIPTION);
		if (null != description)
		{
			attribute.setDescription(description);
		}
		else
		{
			throw new InvalidCsvLineException("Missing required field \"description\" from line " + rowNum);
		}
		
		//read the error message from the csv line
		final String errorMessage = get(parts, INDEX_ERROR_MESSAGE);
		if (null != errorMessage)
		{
			attribute.setErrorMessage(errorMessage);
		}
		else
		{
			throw new InvalidCsvLineException("Missing required field \"errorMessage\" from line " + rowNum);
		}
		
		//read the maxSize from the csv line
		final String maxSize = get(parts, INDEX_MAX_LENGTH);
		if (null != maxSize)
		{
			try
			{
				attribute.setMaxSize(Integer.parseInt(maxSize));
			}
			catch (NumberFormatException e)
			{
				throw new InvalidCsvLineException(
					"The value \"" + maxSize + "\" for \"maxSize\" from line " + rowNum + " is not a valid number.");
			}
		}
		else
		{
			throw new InvalidCsvLineException("Missing required field \"maxSize\" from line " + rowNum);
		}
		
		//read the types from the csv line
		final String types = get(parts, INDEX_TYPES);
		if (null != types)
		{
			//split based on the field delimiter and add all of the types
			String[] typesParts = types.split(Pattern.quote(FIELD_DELIMITER));
			attribute.getTypes().addAll(Arrays.asList(typesParts));
		}
		else
		{
			throw new InvalidCsvLineException("Missing required field \"types\" from line " + rowNum);
		}
		
		//read the optional field for allow markdown
		final String allowMarkdown = get(parts, INDEX_ALLOW_MARKDOWN);
		if (null != allowMarkdown)
		{
			//make sure the allow markdown field is either true or false
			if (allowMarkdown.equalsIgnoreCase("true") || allowMarkdown.equalsIgnoreCase("false"))
			{
				//parse the boolean value and assign it to the field
				attribute.setAllowMarkdown(Boolean.parseBoolean(allowMarkdown));
			}
			else
			{
				throw new InvalidCsvLineException(
					"Invalid value \"" + allowMarkdown + "\" for field \"allowMarkdown\" from line " + rowNum
						+ ". Valid values are \"true\" or \"false\"");
			}
		}
		
		return attribute;
	}
	
	private static String get(final String[] parts, final int index)
	{
		//make sure the parts has the required index
		if (parts.length >= index + 1)
		{
			return parts[index].trim();
		}
		else
		{
			return null;
		}
	}
}
