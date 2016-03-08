package com.threatconnect.sdk.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

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

public abstract class AbstractXMLParser<I extends Item> extends AbstractPagedParser<I>
{
	public AbstractXMLParser(final String url)
	{
		super(url);
	}
	
	@Override
	protected PageResult<I> parsePage(String pageUrl, Date startDate) throws ParserException
	{
		URLConnection connection = null;
		
		try
		{
			// load the url and read the xml as a string
			URL url = new URL(pageUrl);
			connection = url.openConnection();
			String xml = preProcessXML(IOUtils.toString(connection.getInputStream()));
			
			Document doc = createDocument(xml);
			
			// process the rss feed
			return processXmlDocument(doc, pageUrl, startDate);
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
	
	/**
	 * Process the xml document
	 * 
	 * @param doc
	 * @param startDate
	 * @return
	 * @throws ParserException
	 */
	protected abstract PageResult<I> processXmlDocument(Document doc, String pageUrl, Date startDate)
		throws ParserException, XPathExpressionException;
}
