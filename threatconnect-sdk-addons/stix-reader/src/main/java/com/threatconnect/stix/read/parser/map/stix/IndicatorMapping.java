package com.threatconnect.stix.read.parser.map.stix;

import com.threatconnect.sdk.model.Attribute;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.ItemType;
import com.threatconnect.sdk.parser.util.AttributeHelper;
import com.threatconnect.stix.read.parser.Constants;
import com.threatconnect.stix.read.parser.util.DebugUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IndicatorMapping
{
	private static final Logger logger = LoggerFactory.getLogger(IndicatorMapping.class);
	
	private static final String ATTR_TITLE = "Title";
	private static final String ATTR_STIX_ID = "STIX ID";
	private static final String ATTR_STIX_INDICATOR_TYPE = "STIX Indicator Type";
	private static final String ATTR_PRODUCER = "Producer";
	private static final String ATTR_ALTERNATIVE_ID = "Alternative ID";
	private static final String ATTR_VALID_TIME_POSITION = "Valid Time Position";
	private static final String ATTR_PHASE_OF_INTRUSION = "Phase of Intrusion";
	private static final String ATTR_LIKELY_IMPACT = "Likely Impact";
	private static final String ATTR_SIGHTINGS = "Sightings";
	
	public void map(final Node indicatorNode, final Document document, final List<? extends Item> items)
		throws XPathExpressionException
	{
		// for each of the signatures
		for (Item item : items)
		{
			map(indicatorNode, document, item);
		}
	}
	
	public void map(final Node indicatorNode, final Document document, final Item item) throws XPathExpressionException
	{
		final String stixID = Constants.XPATH_UTIL.getString("@id", indicatorNode);
		
		//holds the list of attributes that were built
		List<Attribute> attributes = new ArrayList<Attribute>();
		
		// add all of the attributes for this object if they exist
		attributes.add(AttributeHelper.addAttributeIfExists(item, ATTR_STIX_ID, stixID));
		attributes.add(AttributeHelper.addAttributeIfExists(item, ATTR_TITLE,
			Constants.XPATH_UTIL.getString("Title", indicatorNode)));
		attributes.add(AttributeHelper.addAttributeIfExists(item, ATTR_PRODUCER,
			Constants.XPATH_UTIL.getString("Producer/Identity/Name", indicatorNode)));
		
		//retrieve the types
		NodeList nodeList = Constants.XPATH_UTIL.getNodes("Type", indicatorNode);
		if (null != nodeList)
		{
			// for each of the nodes in the list
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				Node node = nodeList.item(i);
				attributes.add(AttributeHelper.addAttributeIfExists(item, ATTR_STIX_INDICATOR_TYPE, node.getTextContent()));
			}
		}
		
		// retrieve the two description fields
		final String description = Constants.XPATH_UTIL.getString("Description", indicatorNode);
		final String shortDescription = Constants.XPATH_UTIL.getString("Short_Description", indicatorNode);
		
		StringBuilder descriptionBuilder = new StringBuilder();
		if (StringUtils.isNotBlank(shortDescription))
		{
			descriptionBuilder.append(shortDescription.trim());
		}
		if (StringUtils.isNotBlank(description))
		{
			if (descriptionBuilder.length() > 0)
			{
				descriptionBuilder.append("\n");
			}
			
			descriptionBuilder.append(description.trim());
		}
		if (!descriptionBuilder.toString().isEmpty())
		{
			attributes.add(AttributeHelper.addDescriptionAttribute(item, descriptionBuilder.toString()));
		}
		
		//check to see if the item is an indicator
		if (ItemType.INDICATOR == item.getItemType())
		{
			Indicator indicator = (Indicator) item;
			
			final String confidence = Constants.XPATH_UTIL.getString("Confidence/Value", indicatorNode);
			if (null != confidence)
			{
				try
				{
					indicator.setConfidence(Double.parseDouble(confidence));
				}
				catch (NumberFormatException e)
				{
					logger.warn(e.getMessage(), e);
				}
			}
		}
		
		NodeList killChainPhaseNodeList =
			Constants.XPATH_UTIL.getNodes("Kill_Chain_Phases/Kill_Chain_Phase", indicatorNode);
		if (null != killChainPhaseNodeList)
		{
			// for each of the nodes in the list
			for (int i = 0; i < killChainPhaseNodeList.getLength(); i++)
			{
				// retrieve the current package node
				Node killChainNode = killChainPhaseNodeList.item(i);
				String name = Constants.XPATH_UTIL.getString("@name", killChainNode);
				attributes.add(AttributeHelper.addAttributeIfExists(item, ATTR_PHASE_OF_INTRUSION, name));
			}
		}
		
		//make sure the stix id is not null
		if (null != stixID)
		{
			//set the attribute source as the stix id
			attributes.stream()
				//remove all null attributes
				.filter(Objects::nonNull)
				//remove the ATTR_STIX_ID attribute
				.filter(a -> !a.getType().equals(ATTR_STIX_ID))
				//add the stixid as the source for each of the remaining attributes
				.forEach(attribute -> attribute.setSource("@id:" + stixID));
		}
		
		// :FIXME: handle the unknown mappings
		DebugUtil.logUnknownMapping("Type", indicatorNode);
		DebugUtil.logUnknownMapping("Alternative_ID", indicatorNode);
		DebugUtil.logUnknownMapping("Likely_Impact/Value", indicatorNode);
		DebugUtil.logUnknownMapping("Valid_Time_Position", indicatorNode);
		DebugUtil.logUnknownMapping("Composite_Indicator_Expression", indicatorNode);
		DebugUtil.logUnknownMapping("Test_Mechanisms", indicatorNode);
		DebugUtil.logUnknownMapping("Handling", indicatorNode);
		DebugUtil.logUnknownMapping("Sightings", indicatorNode);
	}
}
