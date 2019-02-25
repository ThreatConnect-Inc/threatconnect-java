package com.threatconnect.stix.read.parser;

import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.parser.AbstractParser;
import com.threatconnect.sdk.parser.ParserException;
import com.threatconnect.sdk.parser.source.DataSource;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.util.List;

/**
 * @author Greg Marut
 */
public abstract class AbstractXMLStreamParser<I extends Item> extends AbstractParser<I>
{
	public AbstractXMLStreamParser(final DataSource dataSource)
	{
		super(dataSource);
	}
	
	@Override
	public List<I> parseData() throws ParserException
	{
		//create a new XML input factory
		XMLInputFactory factory = XMLInputFactory.newInstance();
		
		factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
		factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
		
		try
		{
			//read the xml into the stream reader
			XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(getDataSource().read());
			
			//parse the xml and return the contents
			return parseXml(xmlStreamReader);
		}
		catch (IOException | XMLStreamException e)
		{
			throw new ParserException(e);
		}
	}
	
	/**
	 * Parse the XML and return the list of items
	 *
	 * @param xmlStreamReader
	 * @return
	 * @throws ParserException
	 */
	protected abstract List<I> parseXml(final XMLStreamReader xmlStreamReader) throws ParserException;
}
