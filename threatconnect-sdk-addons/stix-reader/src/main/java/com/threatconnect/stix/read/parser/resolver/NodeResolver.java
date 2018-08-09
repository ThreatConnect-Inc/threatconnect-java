package com.threatconnect.stix.read.parser.resolver;

import com.threatconnect.stix.read.parser.Constants;
import com.threatconnect.stix.read.parser.observer.NodeObserver;
import com.threatconnect.stix.read.parser.util.StixNodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpressionException;

/**
 * Considering some nodes can either contain the data to parse or can simply be pointers to
 * another node, we may need to hunt down the node which contains the data. Using a
 * node as a start point, this method will follow the idrefs (pointers), if any, to
 * resolve the node that contains the content
 *
 * @author Greg Marut
 */
public class NodeResolver extends Resolver<Node, NodeObserver>
{
	private static final Logger logger = LoggerFactory.getLogger(NodeResolver.class);
	
	/**
	 * Considering observables can either contain the data to parse or can simply be pointers to
	 * another observable, we may need to hunt down the node which contains the data. Using an
	 * observableNode as a start point, this method will follow the idrefs (pointers), if any, to
	 * resolve the observable that contains the content
	 *
	 * @param node the observable node
	 * @throws XPathExpressionException
	 */
	public void resolveNode(final Document document, final Node node, final NodeObserver observer)
		throws XPathExpressionException
	{
		// make sure that the node is not null
		if (null == node)
		{
			throw new IllegalArgumentException("node cannot be null");
		}
		
		// check to see if this node has an idref to reference another location in the document
		String idRef = Constants.XPATH_UTIL.getString("@idref", node);
		
		// check to see if there is an id ref to follow
		if (null != idRef && !idRef.isEmpty())
		{
			//store this id and observable in the map to wait for it to be found in another document
			addObserver(idRef, observer);
		}
		else
		{
			nodeResolved(document, node, observer);
		}
	}
	
	protected NodeObserver wrapObserver(final NodeObserver... observers)
	{
		//create a new node observer to update this observer
		return object ->
		{
			//for each of the observers
			for (NodeObserver observer : observers)
			{
				observer.found(object);
			}
		};
	}
	
	/**
	 * Called when a node is resolved to a concrete object instead of having a pointer to another node
	 *
	 * @param document
	 * @param node
	 * @param observer
	 * @throws XPathExpressionException
	 */
	protected void nodeResolved(final Document document, final Node node, final NodeObserver observer)
		throws XPathExpressionException
	{
		//get the id of this node
		final String id = StixNodeUtil.getID(node);
		
		//make sure the id is not null or empty
		if (null != id && !id.isEmpty())
		{
			//add this node to the list of resolved nodes
			addObserver(id, observer);
			addResolvedObject(id, node);
		}
		else
		{
			logger.debug("Resolved node contains no id attribute");
		}
	}
}
