package com.threatconnect.stix.read.parser.util;

import com.threatconnect.stix.read.parser.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

public class DebugUtil
{
	public static void logUnknownMapping(final String xPath, final Node node) throws XPathExpressionException
	{
		Element element = (Element) Constants.XPATH_UTIL.getNode(xPath, node);
		if (null != element)
		{
			try
			{
				String xml = StixNodeUtil.toXMLString(element);
				
				StringBuilder sb = new StringBuilder();
				sb.append("Unknown Mapping ");
				sb.append(xPath);
				
				if (null != element.getParentNode() && null != element.getParentNode().getNodeName())
				{
					sb.append(" on object ");
					sb.append(element.getParentNode().getNodeName());
				}
				
				sb.append(":\n");
				sb.append(xml);
				
				getLoggerForCallingClass().debug(sb.toString());
			}
			catch (TransformerException e)
			{
				getLoggerForCallingClass().debug(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * Retrieves the logger of the class that called this
	 * 
	 * @return
	 */
	private static Logger getLoggerForCallingClass()
	{
		try
		{
			return LoggerFactory.getLogger(new Exception().getStackTrace()[2].getClassName());
		}
		catch (Exception e)
		{
			return LoggerFactory.getLogger(DebugUtil.class);
		}
	}
}
