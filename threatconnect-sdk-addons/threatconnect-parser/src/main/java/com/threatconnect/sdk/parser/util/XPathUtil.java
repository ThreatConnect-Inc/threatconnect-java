package com.threatconnect.sdk.parser.util;

import java.util.HashMap;
import java.util.Map;

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
	
	private final Map<String, XPathExpression> expressions;
	
	public XPathUtil()
	{
		this.expressions = new HashMap<String, XPathExpression>();
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
	public Node getNode(final String xPath, final Object item)
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
	public NodeList getNodes(final String xPath, final Object item)
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
	public String getString(final String xPath, final Object item)
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
	public Object evaluateXPath(final String xPath, final Object item, final QName returnType)
		throws XPathExpressionException
	{
		return getXPathExpression(xPath).evaluate(item, returnType);
	}
	
	private XPathExpression getXPathExpression(final String xPath) throws XPathExpressionException
	{
		// check to see if this xpath does not exist in the map
		if (!expressions.containsKey(xPath))
		{
			// create the xpath expression
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xpath.compile(xPath);
			
			// add this xpath expression to the list
			expressions.put(xPath, expr);
		}
		
		return expressions.get(xPath);
	}
}
