package com.threatconnect.stix.read.parser.util;

import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.util.AttributeUtil;
import com.threatconnect.stix.read.parser.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;
import java.io.StringWriter;

public class StixNodeUtil
{
	private static Logger logger = LoggerFactory.getLogger(StixNodeUtil.class);
	
	public static String getID(final Node node) throws XPathExpressionException
	{
		return Constants.XPATH_UTIL.getString("@id", node);
	}
	
	public static final void setElementAsAttribute(final Item item, final String attributeValue, final Node node)
	{
		setElementAsAttribute(item, attributeValue, (Element) node);
	}
	
	public static final void setElementAsAttribute(final Item item, final String attributeType,
		final Element element)
	{
		try
		{
			NamespaceUtil.addNamespacesToElement(element);
			AttributeUtil.addAttributeIfExists(item, attributeType, toXMLString(element));
		}
		catch (TransformerException e)
		{
			logger.warn(e.getMessage(), e);
		}
	}
	
	public static String toXMLString(final Element element) throws TransformerException
	{
		NamespaceUtil.addNamespacesToElement(element);
		StringWriter writer = new StringWriter();
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(new DOMSource(element), new StreamResult(writer));
		return writer.toString();
	}
}
