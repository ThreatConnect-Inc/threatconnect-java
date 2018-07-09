package com.threatconnect.stix.read.parser.resolver;

import com.threatconnect.stix.read.parser.Constants;
import com.threatconnect.stix.read.parser.observer.NodeObserver;
import com.threatconnect.stix.read.parser.util.StixNodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.util.List;

/**
 * Considering some nodes can either contain the data to parse or can simply be pointers to
 * another node, we may need to hunt down the node which contains the data. Using a
 * node as a start point, this method will follow the idrefs (pointers), if any, to
 * resolve the node that contains the content
 *
 * @author Greg Marut
 */
public class ObservableNodeResolver extends NodeResolver
{
	private static final Logger logger = LoggerFactory.getLogger(ObservableNodeResolver.class);
	
	@Override
	protected void nodeResolved(final Document document, final Node node, final NodeObserver observer)
		throws XPathExpressionException
	{
		// check to see if this observable is a composition of other observables
		Node observableComposition = Constants.XPATH_UTIL.getNode("Observable_Composition", node);
		if (null != observableComposition)
		{
			// retrieve the list of observables
			NodeList observableNodeList = Constants.XPATH_UTIL.getNodes("Observable", observableComposition);
			
			// make sure that the list is not empty
			if (null != observableNodeList && observableNodeList.getLength() > 0)
			{
				//get the ID for the parent node
				final String id = StixNodeUtil.getID(node);
				
				//find the list of all observers that were waiting on the parent's id to be found
				addNodeObserver(id, observer);
				List<NodeObserver> observers = nodeObserverMap.get(id);
				
				// for every observable object in the list
				for (int i = 0; i < observableNodeList.getLength(); i++)
				{
					//create a new node observer to update this observer
					NodeObserver childObserver = wrapObserver(observers.toArray(new NodeObserver[observers.size()]));
					
					// follow this observable
					Node nextNode = observableNodeList.item(i);
					resolveNode(document, nextNode, childObserver);
				}
			}
			else
			{
				logger.debug("Found an Observable_Composition without any observable objects");
			}
		}
		else
		{
			//there are no pointers to another observable so this must be the one we are looking for
			super.nodeResolved(document, node, observer);
		}
	}
}
