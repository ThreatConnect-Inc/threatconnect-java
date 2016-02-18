package com.threatconnect.sdk.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
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
		try
		{
			// load the url and read the xml as a string
			URL url = new URL(pageUrl);
			String xml = preProcessXML(IOUtils.toString(url.openStream()));
			
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Reader reader = new StringReader(xml);
			InputSource inputSource = new InputSource(reader);
			Document doc = builder.parse(inputSource);
			
			// process the rss feed
			return processXmlDocument(doc, pageUrl, startDate);
		}
		catch (IOException | ParserConfigurationException | SAXException | XPathExpressionException e)
		{
			throw new ParserException(e);
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
