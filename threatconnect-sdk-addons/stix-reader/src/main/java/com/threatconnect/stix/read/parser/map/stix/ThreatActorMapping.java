package com.threatconnect.stix.read.parser.map.stix;

import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.sdk.model.Threat;
import com.threatconnect.sdk.model.util.AttributeUtil;
import com.threatconnect.stix.read.parser.Constants;
import com.threatconnect.stix.read.parser.util.DebugUtil;
import com.threatconnect.stix.read.parser.util.StixNodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;

public class ThreatActorMapping
{
	private static final String ATTR_IDENTITY = "Identity";
	private static final String ATTR_TYPE = "Type";
	private static final String ATTR_MOTIVATION = "Motivation";
	private static final String ATTR_SOPHISTICATION = "Sophistication";
	private static final String ATTR_INTENDED_EFFECT = "Intended Effect";
	private static final String ATTR_PLANNING_OP_SUPPORT = "Planning and Operational Support";
	
	public List<Threat> map(final Node incidentNode, final Document document, final List<SecurityLabel> securityLabels) throws XPathExpressionException
	{
		List<Threat> items = new ArrayList<Threat>();
		
		Threat item = new Threat();
		item.getSecurityLabels().addAll(securityLabels);
		items.add(item);
		final String indicatorNodeID = StixNodeUtil.getID(incidentNode);
		item.setName(indicatorNodeID);
		
		// retrieve the two description fields
		final String description = Constants.XPATH_UTIL.getString("Description", incidentNode);
		if (StringUtils.isNotBlank(description))
		{
			AttributeUtil.addDescriptionAttribute(item, description);
		}
		
		// add all of the attributes for this object if they exist
		AttributeUtil.addAttributeIfExists(item, ATTR_IDENTITY,
			Constants.XPATH_UTIL.getString("Identity/Name", incidentNode));
		AttributeUtil.addAttributeIfExists(item, ATTR_TYPE,
			Constants.XPATH_UTIL.getString("Type/Value", incidentNode));
		AttributeUtil.addAttributeIfExists(item, ATTR_MOTIVATION,
			Constants.XPATH_UTIL.getString("Motivation/Value", incidentNode));
		AttributeUtil.addAttributeIfExists(item, ATTR_SOPHISTICATION,
			Constants.XPATH_UTIL.getString("Sophistication/Value", incidentNode));
		AttributeUtil.addAttributeIfExists(item, ATTR_INTENDED_EFFECT,
			Constants.XPATH_UTIL.getString("Intended_Effect/Value", incidentNode));
		AttributeUtil.addAttributeIfExists(item, ATTR_PLANNING_OP_SUPPORT,
			Constants.XPATH_UTIL.getString("Planning_And_Operational_Support/Value", incidentNode));
			
		final String sourceValue = Constants.XPATH_UTIL.getString("Information_Source/Description", incidentNode);
		if (null != sourceValue)
		{
			AttributeUtil.addSourceAttribute(item, sourceValue);
		}
		
		// :FIXME: handle the unknown mappings
		DebugUtil.logUnknownMapping("Observed_TTPs", incidentNode);
		DebugUtil.logUnknownMapping("Associated_Actors", incidentNode);
		
		return items;
	}
}
