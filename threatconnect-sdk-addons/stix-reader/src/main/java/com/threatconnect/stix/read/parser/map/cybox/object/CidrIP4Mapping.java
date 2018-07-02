package com.threatconnect.stix.read.parser.map.cybox.object;

import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.stix.read.indicator.Cidr;
import com.threatconnect.stix.read.parser.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpressionException;
import java.util.Collections;
import java.util.List;

/**
 * @author Greg Marut
 */
public class CidrIP4Mapping extends CyboxObjectMapping
{
	public CidrIP4Mapping(final Double defaultRating, final Double defaultConfidence)
	{
		super(defaultRating, defaultConfidence);
	}
	
	@Override
	public List<? extends Item> map(final Node objectNode, final String observableNodeID, final Document document,
		final List<SecurityLabel> securityLabels) throws XPathExpressionException
	{
		// get the properties node for this observable
		Node propertiesNode = Constants.XPATH_UTIL.getNode("Properties", objectNode);
		
		// extract the address value from the cybox object
		String value = Constants.XPATH_UTIL.getString("Address_Value", propertiesNode).trim();
		
		Cidr cidr = new Cidr();
		cidr.getSecurityLabels().addAll(securityLabels);
		setDefaultRatingConfidence(cidr);
		addStixObservableIDAttribute(cidr, observableNodeID);
		cidr.setValue(value);
		
		return Collections.singletonList(cidr);
	}
}
