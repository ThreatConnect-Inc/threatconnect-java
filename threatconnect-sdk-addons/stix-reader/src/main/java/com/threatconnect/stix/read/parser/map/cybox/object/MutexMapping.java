package com.threatconnect.stix.read.parser.map.cybox.object;

import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.stix.read.indicator.Cidr;
import com.threatconnect.stix.read.indicator.Mutex;
import com.threatconnect.stix.read.parser.Constants;
import com.threatconnect.stix.read.parser.observer.ItemObserver;
import com.threatconnect.stix.read.parser.resolver.NodeResolver;
import com.threatconnect.stix.read.parser.resolver.Resolver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpressionException;
import java.util.Collections;
import java.util.List;

/**
 * @author Greg Marut
 */
public class MutexMapping extends CyboxObjectMapping
{
	public MutexMapping(final Double defaultRating, final Double defaultConfidence)
	{
		super(defaultRating, defaultConfidence);
	}
	
	@Override
	public List<? extends Item> map(final Node objectNode, final String observableNodeID, final Document document,
		final List<SecurityLabel> securityLabels, final NodeResolver nodeResolver,
		final Resolver<List<? extends Item>, ItemObserver> cyboxObjectResolver) throws XPathExpressionException
	{
		// get the properties node for this observable
		Node propertiesNode = Constants.XPATH_UTIL.getNode("Properties", objectNode);
		
		// extract the name value from the cybox object
		String value = Constants.XPATH_UTIL.getString("Name", propertiesNode).trim();
		
		Mutex mutex = new Mutex();
		mutex.getSecurityLabels().addAll(securityLabels);
		setDefaultRatingConfidence(mutex);
		addStixObservableIDAttribute(mutex, observableNodeID);
		mutex.setValue(value);
		
		return Collections.singletonList(mutex);
	}
}
