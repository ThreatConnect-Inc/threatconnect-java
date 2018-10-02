package com.threatconnect.stix.read.parser.map.cybox.object;

import com.threatconnect.sdk.model.Email;
import com.threatconnect.sdk.model.EmailAddress;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.sdk.parser.util.AttributeHelper;
import com.threatconnect.stix.read.indicator.EmailSubject;
import com.threatconnect.stix.read.parser.Constants;
import com.threatconnect.stix.read.parser.observer.ItemObserver;
import com.threatconnect.stix.read.parser.resolver.NodeResolver;
import com.threatconnect.stix.read.parser.resolver.Resolver;
import com.threatconnect.stix.read.parser.util.DebugUtil;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;

public class EmailMapping extends CyboxObjectMapping
{
	//:FIXME: the api requires some field to be something
	private static final String EMPTY_VALUE = "Empty";
	
	private static final String ATTR_EMAIL_SERVER = "Email Server";
	
	public EmailMapping(final Double defaultRating, final Double defaultConfidence)
	{
		super(defaultRating, defaultConfidence);
	}
	
	@Override
	public List<? extends Item> map(final Node objectNode, final String observableNodeID, final Document document,
		final List<SecurityLabel> securityLabels, final NodeResolver nodeResolver,
		final Resolver<List<? extends Item>, ItemObserver> cyboxObjectResolver) throws XPathExpressionException
	{
		List<Item> items = new ArrayList<Item>();
		
		// get the properties node for this observable
		Node propertiesNode = Constants.XPATH_UTIL.getNode("Properties", objectNode);
		
		// holds the email object to return
		final Email email = new Email();
		items.add(email);
		
		email.getSecurityLabels().addAll(securityLabels);
		
		// use the object ID for the email name
		final String id = Constants.XPATH_UTIL.getString("@id", objectNode);
		email.setName(id);
		
		// extract the raw body of the email
		final String rawBody = Constants.XPATH_UTIL.getString("Raw_Body", propertiesNode);
		email.setBody(StringUtils.isNotBlank(rawBody) ? rawBody : EMPTY_VALUE);
		
		// extract the raw header of the email
		final String rawHeader = Constants.XPATH_UTIL.getString("Raw_Header", propertiesNode);
		email.setHeader(StringUtils.isNotBlank(rawHeader) ? rawHeader : EMPTY_VALUE);
		
		// extract the subject
		final String subject = Constants.XPATH_UTIL.getString("Header/Subject", propertiesNode);
		if (StringUtils.isNotBlank(subject))
		{
			email.setSubject(subject);
			
			//create a new email subject object
			EmailSubject emailSubject = new EmailSubject();
			emailSubject.getSecurityLabels().addAll(securityLabels);
			setDefaultRatingConfidence(emailSubject);
			addStixObservableIDAttribute(emailSubject, observableNodeID);
			emailSubject.setValue(subject);
			
			//add this indicator to the email group
			email.getAssociatedItems().add(emailSubject);
			items.add(emailSubject);
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
				
				//add this indicator to the email group
				email.getAssociatedItems().add(emailAddress);
				items.add(emailAddress);
			}
		}
		
		//get the address value object
		String addressValue = Constants.XPATH_UTIL.getString("Address_Value", propertiesNode);
		if (StringUtils.isNotBlank(addressValue))
		{
			//create a new email address indicator
			EmailAddress emailAddress = new EmailAddress();
			setDefaultRatingConfidence(emailAddress);
			addStixObservableIDAttribute(emailAddress, observableNodeID);
			emailAddress.setAddress(addressValue);
			
			//add this indicator to the email group
			email.getAssociatedItems().add(emailAddress);
			items.add(emailAddress);
		}
		
		// set the to recipients
		email.setTo(buildRecipientList("Header/To/Recipient", propertiesNode));
		
		// add all of the attributes for this object if they exist
		AttributeHelper.addAttributeIfExists(email, ATTR_EMAIL_SERVER,
			Constants.XPATH_UTIL.getString("Email_Server", propertiesNode));
		
		//retrieve all of the file nodes
		NodeList fileNodeList = Constants.XPATH_UTIL.getNodes("Attachments/File", propertiesNode);
		if (null != fileNodeList)
		{
			// for each of the nodes in the list
			for (int i = 0; i < fileNodeList.getLength(); i++)
			{
				// retrieve the current stix package node
				Node fileNode = fileNodeList.item(i);
				
				//get the object reference and see if it is not null
				final String fileReferenceID = Constants.XPATH_UTIL.getString("@object_reference", fileNode);
				if (StringUtils.isNotBlank(fileReferenceID))
				{
					//create an item observer to add all of the items to this email
					ItemObserver itemObserver = (foundItems) -> email.getAssociatedItems().addAll(foundItems);
					cyboxObjectResolver.addObserver(fileReferenceID, itemObserver);
				}
			}
		}
		
		// :FIXME: handle the unknown mappings
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
