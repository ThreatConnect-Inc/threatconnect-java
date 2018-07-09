package com.threatconnect.stix.read.parser.map.cybox.object;

import com.threatconnect.sdk.model.Address;
import com.threatconnect.sdk.model.Host;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.sdk.model.util.AttributeUtil;
import com.threatconnect.sdk.parser.util.RegexUtil;
import com.threatconnect.stix.read.parser.Constants;
import com.threatconnect.stix.read.parser.exception.InvalidObservableException;
import com.threatconnect.stix.read.parser.util.StixNodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;

public class DNSRecordMapping extends CyboxObjectMapping
{
	private static final String ATTR_DNS_QUERIED_DATE = "DNS Queried Date";
	private static final String ATTR_DNS_ADDRESS_CLASS = "DNS Address Class";
	private static final String ATTR_DNS_RESOURCE_RECORD_TYPE = "DNS Resource Record Type";
	private static final String ATTR_DNS_RECORD_NAME = "DNS Record Name";
	private static final String ATTR_DNS_RECORD_TYPE = "DNS Record Type";
	private static final String ATTR_DNS_RECORD_TTL = "DNS Record TTL";
	private static final String ATTR_DNS_RECORD_FLAGS = "DNS Record Flags";
	private static final String ATTR_DNS_RECORD_LENGTH = "DNS Record Length";
	private static final String ATTR_DNS_RECORD_DATA = "DNS Record Data";
	
	public DNSRecordMapping(final Double defaultRating, final Double defaultConfidence)
	{
		super(defaultRating, defaultConfidence);
	}
	
	@Override
	public List<? extends Item> map(final Node objectNode, final String observableNodeID, final Document document,
		final List<SecurityLabel> securityLabels)
		throws XPathExpressionException, InvalidObservableException
	{
		// holds the list of items to return
		List<Item> items = new ArrayList<Item>();
		
		// get the properties node for this observable
		Node propertiesNode = Constants.XPATH_UTIL.getNode("Properties", objectNode);
		
		// extract the indicator fields
		Node ipAddressNode = Constants.XPATH_UTIL.getNode("IP_Address", propertiesNode);
		Node domainNameNode = Constants.XPATH_UTIL.getNode("Domain_Name", propertiesNode);
		
		// check to see if the ip address is not null
		if (null != ipAddressNode)
		{
			String ipAddressValue = Constants.XPATH_UTIL.getString("Address_Value", ipAddressNode);
			if (StringUtils.isNotBlank(ipAddressValue))
			{
				if (RegexUtil.REGEX_IP_FORMAT.matcher(ipAddressValue.trim()).matches())
				{
					Address address = new Address();
					address.getSecurityLabels().addAll(securityLabels);
					setDefaultRatingConfidence(address);
					addStixObservableIDAttribute(address, observableNodeID);
					address.setIp(ipAddressValue.trim());
					
					setDNSAttributes(address, propertiesNode);
					
					items.add(address);
				}
				else
				{
					throw new InvalidObservableException(
						"Address \"" + ipAddressNode + "\" was not recognized for Observable \""
							+ StixNodeUtil.getID(objectNode) + "\"");
				}
			}
		}
		
		// check to see if the domain name is not null
		if (null != domainNameNode)
		{
			String domainNameValue = Constants.XPATH_UTIL.getString("Value", domainNameNode);
			if (StringUtils.isNotBlank(domainNameValue))
			{
				if (RegexUtil.REGEX_HOST.matcher(domainNameValue.trim()).matches())
				{
					Host host = new Host();
					host.getSecurityLabels().addAll(securityLabels);
					setDefaultRatingConfidence(host);
					addStixObservableIDAttribute(host, observableNodeID);
					host.setHostName(domainNameValue.trim());
					
					setDNSAttributes(host, propertiesNode);
					
					items.add(host);
				}
				else
				{
					throw new InvalidObservableException(
						"Host \"" + domainNameNode + "\" was not recognized for Observable \""
							+ StixNodeUtil.getID(objectNode) + "\"");
				}
			}
		}
		
		return items;
	}
	
	private void setDNSAttributes(final Indicator indicator, final Node propertiesNode) throws XPathExpressionException
	{
		final String description = Constants.XPATH_UTIL.getString("Description", propertiesNode);
		if (StringUtils.isNotBlank(description))
		{
			AttributeUtil.addDescriptionAttribute(indicator, description);
		}
		
		// add all of the attributes for this object if they exist
		AttributeUtil.addAttributeIfExists(indicator, ATTR_DNS_QUERIED_DATE,
			Constants.XPATH_UTIL.getString("Queried_Date", propertiesNode));
		AttributeUtil.addAttributeIfExists(indicator, ATTR_DNS_ADDRESS_CLASS,
			Constants.XPATH_UTIL.getString("Address_Class", propertiesNode));
		AttributeUtil.addAttributeIfExists(indicator, ATTR_DNS_RESOURCE_RECORD_TYPE,
			Constants.XPATH_UTIL.getString("Entry_Type", propertiesNode));
		AttributeUtil.addAttributeIfExists(indicator, ATTR_DNS_RECORD_NAME,
			Constants.XPATH_UTIL.getString("Record_Name", propertiesNode));
		AttributeUtil.addAttributeIfExists(indicator, ATTR_DNS_RECORD_TYPE,
			Constants.XPATH_UTIL.getString("Record_Type", propertiesNode));
		AttributeUtil.addAttributeIfExists(indicator, ATTR_DNS_RECORD_TTL,
			Constants.XPATH_UTIL.getString("TTL", propertiesNode));
		AttributeUtil.addAttributeIfExists(indicator, ATTR_DNS_RECORD_FLAGS,
			Constants.XPATH_UTIL.getString("Flags", propertiesNode));
		AttributeUtil.addAttributeIfExists(indicator, ATTR_DNS_RECORD_LENGTH,
			Constants.XPATH_UTIL.getString("Data_Length", propertiesNode));
		AttributeUtil.addAttributeIfExists(indicator, ATTR_DNS_RECORD_DATA,
			Constants.XPATH_UTIL.getString("Record_Data", propertiesNode));
	}
}