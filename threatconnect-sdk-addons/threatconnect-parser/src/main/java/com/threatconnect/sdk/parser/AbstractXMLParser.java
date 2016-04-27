package com.threatconnect.sdk.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.threatconnect.sdk.parser.model.Item;
import com.threatconnect.sdk.parser.result.Result;
import com.threatconnect.sdk.parser.source.DataSource;

public abstract class AbstractXMLParser<I extends Item> extends Parser<I>
{
	private Document document;
	
	public AbstractXMLParser(final DataSource dataSource)
	{
		super(dataSource);
	}
	
	@Override
	public List<I> parseData() throws ParserException
	{
		URLConnection connection = null;
		
		try
		{
			// read the xml as a string and allow any xml preproccessing if needed
			String rawXML = IOUtils.toString(getDataSource().read());
			String xml = preProcessXML(rawXML);
			
			// create a document from the processed xml
			document = createDocument(xml);
			
			// process the xml document
			return processXmlDocument(document).getItems();
		}
		catch (MalformedURLException | ParserConfigurationException | SAXException | XPathExpressionException e)
		{
			throw new ParserException(e);
		}
		catch (IOException e)
		{
			// notify that there was a connection error
			onConnectionError(connection, e);
			return null;
		}
	}
	
	/**
	 * Allows for any preprocessing of the xml string if needed before it is parsed
	 * 
	 * @param xml
	 * @return
	 */
	protected String preProcessXML(final String xml)
	{
		return xml;
	}
	
	protected void onConnectionError(final URLConnection connection, final IOException cause) throws ParserException
	{
		throw new ParserException(cause);
	}
	
	protected DocumentBuilder createDocumentBuilder() throws ParserConfigurationException
	{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		return domFactory.newDocumentBuilder();
	}
	
	protected Document createDocument(final String xml) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilder builder = createDocumentBuilder();
		Reader reader = new StringReader(xml);
		InputSource inputSource = new InputSource(reader);
		return builder.parse(inputSource);
	}
	
	protected Document getDocument()
	{
		return document;
	}
	
	/**
	 * Process the xml document
	 * 
	 * @param doc
	 * @param startDate
	 * @return
	 * @throws ParserException
	 */
	protected abstract Result<I> processXmlDocument(Document document)
		throws ParserException, XPathExpressionException;
}
