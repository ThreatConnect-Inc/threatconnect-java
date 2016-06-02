package com.threatconnect.sdk.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.threatconnect.sdk.parser.model.Item;
import com.threatconnect.sdk.parser.result.PageResult;
import com.threatconnect.sdk.parser.source.DataSource;

public abstract class AbstractPagedXMLParser<I extends Item> extends AbstractPagedParser<I>
{
	private Document document;
	
	public AbstractPagedXMLParser(final DataSource dataSource)
	{
		super(dataSource);
	}
	
	@Override
	protected PageResult<I> parsePage(DataSource dataSource) throws ParserException
	{
		URLConnection connection = null;
		
		try
		{
			// read the xml as a string and allow any xml preproccessing if needed
			logger.trace("Loading XML DataSource as a string");
			String rawXML = IOUtils.toString(getDataSource().read());
			logger.trace("Preprocessing raw XML String");
			String xml = preProcessXML(rawXML);
			
			// create a document from the processed xml
			logger.trace("Converting the XML String to an XML Document object");
			document = createDocument(xml);
			
			// process the xml document
			logger.trace("Processing XML Document");
			return processXmlDocument(document);
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
	
	protected Document createDocument(final String xml) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
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
	protected abstract PageResult<I> processXmlDocument(Document document)
		throws ParserException, XPathExpressionException;
}
