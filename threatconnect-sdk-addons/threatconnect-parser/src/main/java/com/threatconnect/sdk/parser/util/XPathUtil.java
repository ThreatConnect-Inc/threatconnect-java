package com.threatconnect.sdk.parser.util;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathUtil
{
	public static final String RSS_ITEM_XPATH = "//rss/channel/item";
	
	private XPathUtil()
	{
	
	}
	
	/**
	 * Evaluates the xpath on a node
	 * 
	 * @param xPath
	 * the xpath expression to use
	 * @param item
	 * the item to perform the xpath on
	 * @return the evaluated object
	 * @throws XPathExpressionException
	 * represents an error in an XPath expression
	 */
	public static Node getNode(final String xPath, final Object item)
		throws XPathExpressionException
	{
		return (Node) evaluateXPath(xPath, item, XPathConstants.NODE);
	}
	
	/**
	 * Evaluates the xpath on a node list
	 * 
	 * @param xPath
	 * the xpath expression to use
	 * @param item
	 * the item to perform the xpath on
	 * @return the evaluated object
	 * @throws XPathExpressionException
	 * represents an error in an XPath expression
	 */
	public static NodeList getNodes(final String xPath, final Object item)
		throws XPathExpressionException
	{
		return (NodeList) evaluateXPath(xPath, item, XPathConstants.NODESET);
	}
	
	/**
	 * Evaluates the xpath on an object
	 * 
	 * @param xPath
	 * the xpath expression to use
	 * @param item
	 * the item to perform the xpath on
	 * @return the evaluated object
	 * @throws XPathExpressionException
	 * represents an error in an XPath expression
	 */
	public static String getString(final String xPath, final Object item)
		throws XPathExpressionException
	{
		return (String) evaluateXPath(xPath, item, XPathConstants.STRING);
	}
	
	/**
	 * Evaluates the xpath on an object
	 * 
	 * @param xPath
	 * the xpath expression to use
	 * @param item
	 * the item to perform the xpath on
	 * @param returnType
	 * the expected returntype of the evaluated expression
	 * @return the evaluated object
	 * @throws XPathExpressionException
	 * represents an error in an XPath expression
	 */
	public static Object evaluateXPath(final String xPath, final Object item, final QName returnType)
		throws XPathExpressionException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile(xPath);
		return expr.evaluate(item, returnType);
	}
}
