package com.threatconnect.sdk.parser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.threatconnect.sdk.parser.model.Item;
import com.threatconnect.sdk.parser.result.PageResult;
import com.threatconnect.sdk.parser.result.Result;
import com.threatconnect.sdk.parser.source.DataSource;
import com.threatconnect.sdk.parser.util.XPathUtil;

public abstract class AbstractXMLNodeParser<I extends Item> extends AbstractPagedXMLParser<I>
{
	private final String nodeXPath;
	
	public AbstractXMLNodeParser(final DataSource dataSource, final String nodeXPath)
	{
		super(dataSource);
		this.nodeXPath = nodeXPath;
	}
	
	@Override
	protected PageResult<I> processXmlDocument(Document doc) throws ParserException, XPathExpressionException
	{
		// holds the list of items to return
		List<I> items = new ArrayList<I>();
		
		// retrieve the list of nodes
		NodeList nodes = new XPathUtil().getNodes(nodeXPath, doc);
		final int nodeLength = nodes.getLength();
		logger.trace("Found {} XML nodes to process", nodeLength);
		
		// for each of the nodes
		for (int i = 0; i < nodeLength; i++)
		{
			logger.trace("Processing node {}/{}", i + 1, nodeLength);
			
			try
			{
				Node node = nodes.item(i);
				Result<I> result = processNode(node);
				
				// make sure the result is not null
				if (null != result)
				{
					// add the items to the list
					items.addAll(result.getItems());
				}
			}
			catch (ParserException e)
			{
				logger.warn(e.getMessage(), e);
			}
		}
		
		return new PageResult<I>(items);
	}
	
	protected abstract Result<I> processNode(Node node) throws ParserException, XPathExpressionException;
}
