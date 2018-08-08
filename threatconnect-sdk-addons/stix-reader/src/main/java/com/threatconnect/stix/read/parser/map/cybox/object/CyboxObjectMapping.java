package com.threatconnect.stix.read.parser.map.cybox.object;

import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.sdk.parser.util.AttributeHelper;
import com.threatconnect.stix.read.parser.exception.InvalidObservableException;
import com.threatconnect.stix.read.parser.resolver.NodeResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpressionException;
import java.util.List;

public abstract class CyboxObjectMapping
{
	public static final String ATTR_STIX_OBSERVABLE_ID = "STIX Observable ID";
	
	private final Double defaultRating;
	private final Double defaultConfidence;
	
	public CyboxObjectMapping(final Double defaultRating, final Double defaultConfidence)
	{
		this.defaultRating = defaultRating;
		this.defaultConfidence = defaultConfidence;
	}
	
	public abstract List<? extends Item> map(final Node objectNode, final String observableNodeID,
		final Document document, final List<SecurityLabel> securityLabels, NodeResolver nodeResolver)
		throws XPathExpressionException, InvalidObservableException;
	
	protected void setDefaultRatingConfidence(final Indicator indicator)
	{
		indicator.setRating(defaultRating);
		indicator.setConfidence(defaultConfidence);
	}
	
	protected void addStixObservableIDAttribute(final Item item, final String observableNodeID)
	{
		AttributeHelper.addAttributeIfExists(item, ATTR_STIX_OBSERVABLE_ID, observableNodeID);
	}
}
