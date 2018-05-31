package com.threatconnect.stix.read.parser.resolver;

import com.threatconnect.stix.read.parser.Constants;
import com.threatconnect.stix.read.parser.observer.NodeObserver;
import com.threatconnect.stix.read.parser.util.StixNodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Considering some nodes can either contain the data to parse or can simply be pointers to
 * another node, we may need to hunt down the node which contains the data. Using a
 * node as a start point, this method will follow the idrefs (pointers), if any, to
 * resolve the node that contains the content
 *
 * @author Greg Marut
 */
public class NodeResolver
{
	private static final Logger logger = LoggerFactory.getLogger(NodeResolver.class);
	
	//holds the map of ids and the node observers to fire when they are found
	protected final Map<String, List<NodeObserver>> nodeObserverMap;
	protected final Map<String, List<Node>> resolvedNodeMap;
	
	public NodeResolver()
	{
		this.nodeObserverMap = new HashMap<String, List<NodeObserver>>();
		this.resolvedNodeMap = new HashMap<String, List<Node>>();
	}
	
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
			addNodeObserver(idRef, observer);
		}
		else
		{
			nodeResolved(document, node, observer);
		}
	}
	
	public void flushAll()
	{
		//for each of the resolved nodes
		for (Map.Entry<String, List<Node>> resolvedNodeEntry : resolvedNodeMap.entrySet())
		{
			//retrieve the list of observers waiting for this id
			List<NodeObserver> observers = nodeObserverMap.get(resolvedNodeEntry.getKey());
			
			//make sure the list of observers is not null
			if (null != observers)
			{
				//for each of the resolved nodes
				for (Node node : resolvedNodeEntry.getValue())
				{
					//for each of the observers
					for (NodeObserver observer : observers)
					{
						//notify the observer
						observer.found(node);
					}
				}
			}
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
	 * Called when a node is resolved to a concreate object instead of having a pointer to another node
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
			addNodeObserver(id, observer);
			addResolvedNode(id, node);
		}
		else
		{
			logger.debug("Resolved node contains no id attribute");
		}
	}
	
	protected void addNodeObserver(final String id, final NodeObserver nodeObserver)
	{
		//check to see if the map contains this id
		List<NodeObserver> nodeObserverList = nodeObserverMap.computeIfAbsent(id, k -> new ArrayList<NodeObserver>());
		
		nodeObserverList.add(nodeObserver);
	}
	
	protected void addResolvedNode(final String id, final Node resolvedNode)
	{
		//check to see if the map contains this id
		List<Node> resolvedNodeList = resolvedNodeMap.computeIfAbsent(id, k -> new ArrayList<Node>());
		
		resolvedNodeList.add(resolvedNode);
	}
}
