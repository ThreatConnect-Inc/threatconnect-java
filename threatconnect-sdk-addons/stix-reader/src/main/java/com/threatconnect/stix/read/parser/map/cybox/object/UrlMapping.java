package com.threatconnect.stix.read.parser.map.cybox.object;

import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.sdk.model.Url;
import com.threatconnect.sdk.parser.util.AttributeHelper;
import com.threatconnect.sdk.parser.util.IndicatorUtil;
import com.threatconnect.sdk.parser.util.InvalidURLException;
import com.threatconnect.stix.read.parser.Constants;
import com.threatconnect.stix.read.parser.exception.InvalidObservableException;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpressionException;
import java.util.Collections;
import java.util.List;

public class UrlMapping extends CyboxObjectMapping
{
	private static final String ATTR_URI_LABEL = "URI Label";
	
	public UrlMapping(final Double defaultRating, final Double defaultConfidence)
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
		
		try
		{
			String value = Constants.XPATH_UTIL.getString("Value", propertiesNode).trim();
			Url url = IndicatorUtil.createUrl(value);
			url.getSecurityLabels().addAll(securityLabels);
			setDefaultRatingConfidence(url);
			addStixObservableIDAttribute(url, observableNodeID);
			
			String urlLabel = Constants.XPATH_UTIL.getString("URL_Label", propertiesNode).trim();
			if (StringUtils.isNoneBlank(urlLabel))
			{
				AttributeHelper.addAttribute(url, ATTR_URI_LABEL, urlLabel);
			}
			
			return Collections.singletonList(url);
		}
		catch (InvalidURLException e)
		{
			throw new InvalidObservableException(e.getMessage());
		}
	}
}
