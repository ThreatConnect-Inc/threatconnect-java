package com.threatconnect.stix.read.parser.map.cybox.object;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.stix.read.parser.Constants;
import com.threatconnect.stix.read.parser.exception.InvalidObservableException;
import com.threatconnect.stix.read.parser.resolver.NodeResolver;
import com.threatconnect.stix.read.parser.util.StixNodeUtil;
import com.threatconnect.sdk.model.Address;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.sdk.parser.util.AttributeHelper;
import com.threatconnect.sdk.parser.util.RegexUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpressionException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class IPv4Mapping extends CyboxObjectMapping
{
	private static final String ATTR_IS_SOURCE = "Is Source";
	private static final String ATTR_IS_DESTINATION = "Is Destination";
	private static final String ATTR_IS_SPOOFED = "Is Spoofed";
	private static final String ATTR_IS_VLAN_NAME = "VLAN Name";
	private static final String ATTR_IS_VLAN_NUMBER = "VLAN Number";
	
	public IPv4Mapping(final Double defaultRating, final Double defaultConfidence)
	{
		super(defaultRating, defaultConfidence);
	}
	
	@Override
	public List<? extends Item> map(final Node objectNode, final String observableNodeID, final Document document,
		final List<SecurityLabel> securityLabels, final NodeResolver nodeResolver) throws XPathExpressionException, InvalidObservableException
	{
		// get the properties node for this observable
		Node propertiesNode = Constants.XPATH_UTIL.getNode("Properties", objectNode);
		
		// strip off the port number if it exists
		String[] addressParts =
			Constants.XPATH_UTIL.getString("Address_Value", propertiesNode).trim().split(Pattern.quote(":"));
		
		// is this an ip address?
		if (RegexUtil.REGEX_IP_FORMAT.matcher(addressParts[0]).matches())
		{
			Address address = new Address();
			address.getSecurityLabels().addAll(securityLabels);
			setDefaultRatingConfidence(address);
			addStixObservableIDAttribute(address, observableNodeID);
			address.setIp(addressParts[0]);
			
			// add all of the attributes for this object if they exist
			AttributeHelper.addAttributeIfExists(address, ATTR_IS_SOURCE,
				Constants.XPATH_UTIL.getString("@is_source", propertiesNode));
			AttributeHelper.addAttributeIfExists(address, ATTR_IS_DESTINATION,
				Constants.XPATH_UTIL.getString("@is_destination", propertiesNode));
			AttributeHelper.addAttributeIfExists(address, ATTR_IS_SPOOFED,
				Constants.XPATH_UTIL.getString("@is_spoofed", propertiesNode));
			AttributeHelper.addAttributeIfExists(address, ATTR_IS_VLAN_NAME,
				Constants.XPATH_UTIL.getString("VLAN_Name", propertiesNode));
			AttributeHelper.addAttributeIfExists(address, ATTR_IS_VLAN_NUMBER,
				Constants.XPATH_UTIL.getString("VLAN_Num", propertiesNode));
			
			return Collections.singletonList(address);
		}
		else
		{
			throw new InvalidObservableException(
				"Address \"" + addressParts[0] + "\" was not recognized for Observable \""
					+ StixNodeUtil.getID(objectNode) + "\"");
		}
	}
}
