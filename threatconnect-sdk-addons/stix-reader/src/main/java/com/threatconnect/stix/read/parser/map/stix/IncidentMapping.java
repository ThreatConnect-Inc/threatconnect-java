package com.threatconnect.stix.read.parser.map.stix;

import com.threatconnect.stix.read.parser.Constants;
import com.threatconnect.stix.read.parser.util.DebugUtil;
import com.threatconnect.stix.read.parser.util.StixNodeUtil;
import com.threatconnect.sdk.model.Incident;
import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.sdk.parser.util.AttributeHelper;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;

public class IncidentMapping
{
	private static final String ATTR_TITLE = "Title";
	private static final String ATTR_EXTERNAL_ID = "External ID";
	private static final String ATTR_TIME = "Time";
	private static final String ATTR_REPORTER = "Reporter";
	private static final String ATTR_RESPONDER = "Responder";
	private static final String ATTR_COORDINATOR = "Coordinator";
	private static final String ATTR_AFFECTED_ASSETS = "Affected Assets";
	private static final String ATTR_IMPACT_ASSESSMENT = "Impact Assessment";
	private static final String ATTR_INTENDED_EFFECT = "Intended Effect";
	private static final String ATTR_SECURITY_COMPROMISE = "Security Compromise";
	private static final String ATTR_DISCOVER_METHOD = "Discovery Method";
	private static final String ATTR_COA_REQUESTED = "COA Requested";
	private static final String ATTR_COA_TAKEN = "COA Taken";
	private static final String ATTR_CONTACT = "Contact";
	private static final String ATTR_HISTORY = "History";
	
	public List<Incident> map(final Node incidentNode, final Document document,
		final List<SecurityLabel> securityLabels) throws XPathExpressionException
	{
		List<Incident> incidents = new ArrayList<Incident>();
		
		Incident item = new Incident();
		item.getSecurityLabels().addAll(securityLabels);
		incidents.add(item);
		
		final String title = Constants.XPATH_UTIL.getString("Title", incidentNode);
		if (null != title && !title.isEmpty())
		{
			item.setName(title);
		}
		else
		{
			final String indicatorNodeID = StixNodeUtil.getID(incidentNode);
			item.setName(indicatorNodeID);
		}
		
		// add all of the attributes for this object if they exist
		AttributeHelper.addAttributeIfExists(item, ATTR_TITLE, title);
		AttributeHelper.addAttributeIfExists(item, ATTR_EXTERNAL_ID,
			Constants.XPATH_UTIL.getString("External_ID", incidentNode));
		AttributeHelper.addAttributeIfExists(item, ATTR_TIME,
			Constants.XPATH_UTIL.getString("Time/Incident_Reported", incidentNode));
		AttributeHelper.addAttributeIfExists(item, ATTR_REPORTER,
			Constants.XPATH_UTIL.getString("Reporter/Identity/Name", incidentNode));
		AttributeHelper.addAttributeIfExists(item, ATTR_RESPONDER,
			Constants.XPATH_UTIL.getString("Responder/Identity/Name", incidentNode));
		AttributeHelper.addAttributeIfExists(item, ATTR_COORDINATOR,
			Constants.XPATH_UTIL.getString("Coordinator/Identity/Name", incidentNode));
		AttributeHelper.addAttributeIfExists(item, ATTR_CONTACT,
			Constants.XPATH_UTIL.getString("Contact/Description", incidentNode));
		
		// retrieve the two description fields
		final String description = Constants.XPATH_UTIL.getString("Description", incidentNode);
		if (StringUtils.isNotBlank(description))
		{
			AttributeHelper.addDescriptionAttribute(item, description);
		}
		
		final String sourceValue = Constants.XPATH_UTIL.getString("Information_Source/Description", incidentNode);
		if (null != sourceValue)
		{
			AttributeHelper.addSourceAttribute(item, sourceValue);
		}
		
		NodeList impactAssessmentEffects =
			Constants.XPATH_UTIL.getNodes("Impact_Assessment/Effects/Effect", incidentNode);
		
		if (impactAssessmentEffects.getLength() > 0)
		{
			StringBuilder sb = new StringBuilder();
			
			// for each of the nodes in the list
			for (int i = 0; i < impactAssessmentEffects.getLength(); i++)
			{
				// retrieve the current stix package node
				Node effectNode = impactAssessmentEffects.item(i);
				
				// detach this node from the parent
				effectNode.getParentNode().removeChild(effectNode);
				
				if (sb.length() > 0)
				{
					sb.append(", ");
				}
				
				sb.append(Constants.XPATH_UTIL.getString("text()", effectNode));
			}
			
			AttributeHelper.addAttributeIfExists(item, ATTR_IMPACT_ASSESSMENT, sb.toString());
		}
		
		// :FIXME: handle the unknown mappings
		DebugUtil.logUnknownMapping("Affected_Assets", incidentNode);
		DebugUtil.logUnknownMapping("Impact_Assessment", incidentNode);
		DebugUtil.logUnknownMapping("Related_Indicators", incidentNode);
		DebugUtil.logUnknownMapping("Related_Observables", incidentNode);
		DebugUtil.logUnknownMapping("Leveraged_TTPs", incidentNode);
		DebugUtil.logUnknownMapping("Security_Compromise", incidentNode);
		DebugUtil.logUnknownMapping("Discovery_Method", incidentNode);
		DebugUtil.logUnknownMapping("Attributed_Threat_Actors", incidentNode);
		DebugUtil.logUnknownMapping("Related_Incidents", incidentNode);
		DebugUtil.logUnknownMapping("COA_Requested", incidentNode);
		DebugUtil.logUnknownMapping("COA_Taken", incidentNode);
		DebugUtil.logUnknownMapping("History", incidentNode);
		
		return incidents;
	}
}
