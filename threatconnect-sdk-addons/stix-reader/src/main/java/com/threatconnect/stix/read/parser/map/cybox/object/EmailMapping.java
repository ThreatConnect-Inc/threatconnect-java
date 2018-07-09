package com.threatconnect.stix.read.parser.map.cybox.object;

import com.threatconnect.sdk.model.Email;
import com.threatconnect.sdk.model.EmailAddress;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.sdk.model.util.AttributeUtil;
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

public class EmailMapping extends CyboxObjectMapping
{
	private static final Logger logger = LoggerFactory.getLogger(EmailMapping.class);
	
	private static final String ATTR_EMAIL_SERVER = "Email Server";
	private static final String ATTR_IS_SPOOFED = "Is Spoofed";
	
	public EmailMapping(final Double defaultRating, final Double defaultConfidence)
	{
		super(defaultRating, defaultConfidence);
	}
	
	@Override
	public List<? extends Item> map(final Node objectNode, final String observableNodeID, final Document document,
		final List<SecurityLabel> securityLabels) throws XPathExpressionException
	{
		List<Item> items = new ArrayList<Item>();
		
		// get the properties node for this observable
		Node propertiesNode = Constants.XPATH_UTIL.getNode("Properties", objectNode);
		
		// holds the email object to return
		Email email = new Email();
		items.add(email);
		
		email.getSecurityLabels().addAll(securityLabels);
		
		// use the object ID for the email name
		String id = Constants.XPATH_UTIL.getString("@id", objectNode);
		email.setName(id);
		
		// extract the raw body of the email
		String rawBody = Constants.XPATH_UTIL.getString("Raw_Body", propertiesNode);
		if (StringUtils.isNotBlank(rawBody))
		{
			email.setBody(rawBody);
		}
		
		// extract the raw header of the email
		String rawHeader = Constants.XPATH_UTIL.getString("Raw_Header", propertiesNode);
		if (StringUtils.isNotBlank(rawHeader))
		{
			email.setHeader(rawHeader);
		}
		
		// extract the from address value for this email address
		final Node fromNode = Constants.XPATH_UTIL.getNode("Header/From", propertiesNode);
		
		//make sure the from node is not null
		if (null != fromNode)
		{
			String fromAddress = Constants.XPATH_UTIL.getString("Address_Value", fromNode);
			if (StringUtils.isNotBlank(fromAddress))
			{
				email.setFrom(fromAddress);
				
				//create a new email address indicator
				EmailAddress emailAddress = new EmailAddress();
				setDefaultRatingConfidence(emailAddress);
				addStixObservableIDAttribute(emailAddress, observableNodeID);
				emailAddress.setAddress(fromAddress);
				
				//check to see if the spoofed text is set
				String spoofedText = Constants.XPATH_UTIL.getString("@is_spoofed", fromNode);
				if (StringUtils.isNotBlank(spoofedText))
				{
					AttributeUtil.addAttribute(emailAddress, ATTR_IS_SPOOFED, spoofedText);
				}
				
				//add this indicator to the email group
				email.getAssociatedItems().add(emailAddress);
				items.add(emailAddress);
			}
		}
		
		// set the to recipients
		email.setTo(buildRecipientList("Header/To/Recipient", propertiesNode));
		
		// extract the subject
		String subject = Constants.XPATH_UTIL.getString("Header/Subject", propertiesNode);
		if (StringUtils.isNotBlank(subject))
		{
			email.setSubject(subject);
		}
		
		// add all of the attributes for this object if they exist
		AttributeUtil.addAttributeIfExists(email, ATTR_EMAIL_SERVER,
			Constants.XPATH_UTIL.getString("Email_Server", propertiesNode));
		
		// :FIXME: handle the unknown mappings
		DebugUtil.logUnknownMapping("Attachments", propertiesNode);
		DebugUtil.logUnknownMapping("Links", propertiesNode);
		
		return items;
	}
	
	private String buildRecipientList(final String xPath, final Node propertiesNode) throws XPathExpressionException
	{
		NodeList recipientNodeList = Constants.XPATH_UTIL.getNodes(xPath, propertiesNode);
		
		if (null != recipientNodeList)
		{
			StringBuilder recipients = new StringBuilder();
			
			// for each of the items in the list
			for (int i = 0; i < recipientNodeList.getLength(); i++)
			{
				Node recipientNode = recipientNodeList.item(i);
				recipientNode.getParentNode().removeChild(recipientNode);
				
				String address = Constants.XPATH_UTIL.getString("Address_Value", recipientNode);
				if (StringUtils.isNotBlank(address))
				{
					if (recipients.length() > 0)
					{
						recipients.append(", ");
					}
					
					recipients.append(address);
				}
			}
			
			return recipients.toString();
		}
		else
		{
			return null;
		}
	}
}
