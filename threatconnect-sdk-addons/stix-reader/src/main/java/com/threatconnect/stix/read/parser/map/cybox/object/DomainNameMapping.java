package com.threatconnect.stix.read.parser.map.cybox.object;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.stix.read.parser.Constants;
import com.threatconnect.stix.read.parser.exception.InvalidObservableException;
import com.threatconnect.stix.read.parser.util.StixNodeUtil;
import com.threatconnect.sdk.model.Host;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.sdk.parser.util.RegexUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpressionException;
import java.util.Collections;
import java.util.List;

public class DomainNameMapping extends CyboxObjectMapping
{
	public DomainNameMapping(final Double defaultRating, final Double defaultConfidence)
	{
		super(defaultRating, defaultConfidence);
	}
	
	@Override
	public List<? extends Item> map(final Node objectNode, final String observableNodeID, final Document document,
		final List<SecurityLabel> securityLabels)
		throws XPathExpressionException, InvalidObservableException
	{
		// get the properties node for this observable
		Node propertiesNode = Constants.XPATH_UTIL.getNode("Properties", objectNode);
		
		// extract the host address value
		String hostName = Constants.XPATH_UTIL.getString("Value", propertiesNode).trim();
		
		if (RegexUtil.REGEX_HOST.matcher(hostName).matches())
		{
			Host host = new Host();
			host.getSecurityLabels().addAll(securityLabels);
			setDefaultRatingConfidence(host);
			addStixObservableIDAttribute(host, observableNodeID);
			host.setHostName(hostName);
			return Collections.singletonList(host);
		}
		else
		{
			throw new InvalidObservableException(
				"Host \"" + hostName + "\" was not recognized for Observable \"" + StixNodeUtil.getID(objectNode)
					+ "\"");
		}
	}
}