package com.threatconnect.stix.read.parser;

import com.threatconnect.sdk.parser.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class STIXPackageParser
{
	private static final Logger logger = LoggerFactory.getLogger(STIXPackageParser.class);
	
	public List<String> parsePackages(final XMLStreamReader xmlStreamReader) throws ParserException
	{
		//holds the list strings containing the xml for each stix package
		List<String> stixPackageXmlList = new ArrayList<String>();
		
		//create a new element reader
		ElementReader elementReader = new ElementReader();
		
		try
		{
			//while there are more elements to read
			while (xmlStreamReader.hasNext())
			{
				//read the next element
				xmlStreamReader.next();
				int eventType = xmlStreamReader.getEventType();
				
				//switch based on the event type
				switch (eventType)
				{
					case XMLStreamConstants.START_ELEMENT:
						if (Constants.ELEMENT_STIX_PACAKGE.equals(xmlStreamReader.getLocalName()))
						{
							final String xml = elementReader.writeToString(xmlStreamReader);
							stixPackageXmlList.add(xml);
						}
						break;
				}
			}
		}
		catch (XMLStreamException e)
		{
			throw new ParserException(e);
		}
		
		return stixPackageXmlList;
	}
}

