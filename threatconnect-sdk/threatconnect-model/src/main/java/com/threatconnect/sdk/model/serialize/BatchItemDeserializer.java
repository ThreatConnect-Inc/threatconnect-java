package com.threatconnect.sdk.model.serialize;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.threatconnect.sdk.model.Address;
import com.threatconnect.sdk.model.Adversary;
import com.threatconnect.sdk.model.Attribute;
import com.threatconnect.sdk.model.Campaign;
import com.threatconnect.sdk.model.CustomIndicator;
import com.threatconnect.sdk.model.Document;
import com.threatconnect.sdk.model.Email;
import com.threatconnect.sdk.model.EmailAddress;
import com.threatconnect.sdk.model.Event;
import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.FileOccurrence;
import com.threatconnect.sdk.model.Group;
import com.threatconnect.sdk.model.GroupType;
import com.threatconnect.sdk.model.Host;
import com.threatconnect.sdk.model.Incident;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.IntrusionSet;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.ItemType;
import com.threatconnect.sdk.model.Report;
import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.sdk.model.Signature;
import com.threatconnect.sdk.model.Threat;
import com.threatconnect.sdk.model.Url;
import com.threatconnect.sdk.model.util.IndicatorUtil;
import com.threatconnect.sdk.model.util.JsonUtil;
import com.threatconnect.sdk.model.util.TagUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchItemDeserializer
{
	private static final Logger logger = LoggerFactory.getLogger(BatchItemDeserializer.class);
	
	private final JsonObject root;
	
	//holds the map of xids to item
	private final Map<String, Item> xidMap;
	
	public BatchItemDeserializer(final String root)
	{
		this(JsonUtil.getAsJsonObject(new JsonParser().parse(root)));
	}
	
	public BatchItemDeserializer(final JsonObject root)
	{
		this.root = root;
		this.xidMap = new HashMap<String, Item>();
	}
	
	public synchronized List<Item> convertToItems()
	{
		// holds the list of indicators to return
		List<Item> items = new ArrayList<Item>();
		
		JsonArray groupArray = JsonUtil.getAsJsonArray(root, "group");
		JsonArray indicatorArray = JsonUtil.getAsJsonArray(root, "indicator");
		
		//check to see if there is a groups array
		if (null != groupArray)
		{
			// for each object in the groups array
			for (JsonElement groupElement : groupArray)
			{
				//retrieve the xid for this group element
				final String xid = JsonUtil.getAsString(groupElement, "xid");
				
				try
				{
					//determine the group type for this element
					GroupType groupType = determineGroupType(groupElement);
					Group group = createGroup(groupType, groupElement);
					group.setXid(xid);
					
					group.setName(JsonUtil.getAsString(groupElement, "name"));
					
					if (group instanceof Email)
					{
						loadAdditionalData((Email) group, groupElement);
					}
					
					//copy the security labels for the group
					copySecurityLabels(JsonUtil.getAsJsonArray(groupElement, "securityLabel"), group);
					
					//copy tags and attributes for the group
					copyTagsAndAttributes(groupElement, group);
					
					//add this group to the list of items
					items.add(group);
					
					//make sure this group has an xid
					xidMap.put(xid, group);
				}
				catch (InvalidGroupException e)
				{
					logger.warn("Skipping group - invalid group type");
				}
			}
			
			// make a second pass through the groups to make group-group associations
			for (JsonElement groupElement : groupArray)
			{
				//retrieve the array of associatedGroups
				JsonArray associatedGroupsArray = JsonUtil.getAsJsonArray(groupElement, "associatedGroupXid");
				if (null != associatedGroupsArray)
				{
					//retrieve the xid for this group element
					final String xid = JsonUtil.getAsString(groupElement, "xid");
					final Group group = (Group) xidMap.get(xid);
					
					//make sure the group is not null
					if (null != group)
					{
						//for each of the associated groups
						for (JsonElement associatedGroupElement : associatedGroupsArray)
						{
							//get the group xid
							String groupXid = associatedGroupElement.getAsString();
							
							//retrieve the item and make sure it exists and is a group
							Item associatedItem = xidMap.get(groupXid);
							if (null != associatedItem && ItemType.GROUP.equals(associatedItem.getItemType()))
							{
								//associate this group with the parent group
								group.getAssociatedItems().add(associatedItem);
								items.remove(associatedItem);
							}
						}
					}
				}
			}
		}
		
		//check to see if there is an indicator array
		if (null != indicatorArray)
		{
			// for each object in the indicator array
			for (JsonElement indicatorElement : indicatorArray)
			{
				//make sure this indicator has an xid
				final String xid = JsonUtil.getAsString(indicatorElement, "xid");
				if (null != xid)
				{
					try
					{
						// inflate the indicator and save the xid to the map
						Indicator indicator = convertToIndicator(indicatorElement);
						indicator.setXid(xid);
						xidMap.put(xid, indicator);
						
						//copy the rating and confidence values
						indicator.setRating(JsonUtil.getAsDouble(indicatorElement, "rating"));
						indicator.setConfidence(JsonUtil.getAsDouble(indicatorElement, "confidence"));
						
						//copy the security labels for the indicator
						copySecurityLabels(JsonUtil.getAsJsonArray(indicatorElement, "securityLabel"), indicator);
						
						//copy tags and attributes for the indicator
						copyTagsAndAttributes(indicatorElement, indicator);
						
						//check to see if this indicator is a file
						if (indicator instanceof File)
						{
							//load additional data for this file
							loadAdditionalData((File) indicator, indicatorElement);
						}
						
						//determines if this indicator was already associated with a group
						boolean associatedWithGroup = false;
						
						//retrieve the array of associatedGroups
						JsonArray associatedGroupsArray = JsonUtil.getAsJsonArray(indicatorElement, "associatedGroups");
						if (null != associatedGroupsArray)
						{
							//for each of the associated groups
							for (JsonElement associatedGroupElement : associatedGroupsArray)
							{
								//get the group xid
								String groupXid = JsonUtil.getAsString(associatedGroupElement, "groupXid");
								
								//make sure the group xid is not null
								if (null != groupXid)
								{
									//retrieve the item and make sure it exists and is a group
									Item parentItem = xidMap.get(groupXid);
									if (null != parentItem && ItemType.GROUP.equals(parentItem.getItemType()))
									{
										//associate this indicator with the parent group
										Group group = (Group) parentItem;
										group.getAssociatedItems().add(indicator);
										associatedWithGroup = true;
									}
								}
							}
						}
						
						//check to see if the indicator was not already associated with a parent group
						if (!associatedWithGroup)
						{
							items.add(indicator);
						}
					}
					catch (InvalidIndicatorException e)
					{
						logger.warn(e.getMessage(), e);
					}
				}
				else
				{
					logger.warn("Skipping due to missing xid: {}", indicatorElement.toString());
				}
			}
		}
		
		return items;
	}
	
	private void loadAdditionalData(final File file, final JsonElement indicatorElement)
	{
		//filesize is stored as "intValue1"
		file.setSize(JsonUtil.getAsInt(indicatorElement, "intValue1"));
		
		//check to see if there is a file action
		//:DEPRECATED - the right way is to read the fileOccurrence array
		JsonElement fileDataElement = JsonUtil.get(indicatorElement, "fileAction", "fileData");
		if (null != fileDataElement)
		{
			file.getFileOccurrences().add(loadFileOccurrence(fileDataElement));
		}
		
		//check to see if there is a file occurrence array
		JsonArray fileOccurrenceArray = JsonUtil.getAsJsonArray(indicatorElement, "fileAction", "fileOccurrence");
		if (null != fileOccurrenceArray)
		{
			//for each of the file occurrences
			for (JsonElement fileOccurrenceElement : fileOccurrenceArray)
			{
				file.getFileOccurrences().add(loadFileOccurrence(fileOccurrenceElement));
			}
		}
	}
	
	private void loadAdditionalData(final Email email, final JsonElement groupElement)
	{
		email.setBody(JsonUtil.getAsString(groupElement, "body"));
		email.setFrom(JsonUtil.getAsString(groupElement, "from"));
		email.setHeader(JsonUtil.getAsString(groupElement, "header"));
		email.setSubject(JsonUtil.getAsString(groupElement, "subject"));
		email.setScore(JsonUtil.getAsInt(groupElement, "score"));
		email.setTo(JsonUtil.getAsString(groupElement, "to"));
	}
	
	private FileOccurrence loadFileOccurrence(final JsonElement jsonElement)
	{
		FileOccurrence fileOccurrence = new FileOccurrence();
		fileOccurrence.setFileName(JsonUtil.getAsString(jsonElement, "fileName"));
		fileOccurrence.setPath(JsonUtil.getAsString(jsonElement, "path"));
		
		try
		{
			String date = JsonUtil.getAsString(jsonElement, "date");
			
			//make sure the date is not null
			if (null != date)
			{
				Date d = new SimpleDateFormat(Constants.ISO_DATE_TIME_FORMAT).parse(date);
				fileOccurrence.setDate(d);
			}
		}
		catch (ParseException e)
		{
			logger.warn(e.getMessage(), e);
		}
		
		return fileOccurrence;
	}
	
	private void copyTagsAndAttributes(JsonElement element, final Item item)
	{
		// check to see if there are tags
		JsonArray tagArray = JsonUtil.getAsJsonArray(element, "tag");
		if (null != tagArray)
		{
			// for each of the tags
			for (JsonElement tagElement : tagArray)
			{
				TagUtil.addTag(JsonUtil.getAsString(tagElement, "name"), item);
			}
		}
		
		// check to see if there are attributes
		JsonArray attributeArray = JsonUtil.getAsJsonArray(element, "attribute");
		if (null != attributeArray)
		{
			// for each of the attributes
			JsonArray attributes = attributeArray.getAsJsonArray();
			for (JsonElement attribute : attributes)
			{
				item.getAttributes().add(convertToAttribute(attribute.getAsJsonObject()));
			}
		}
	}
	
	private GroupType determineGroupType(final JsonElement groupElement) throws InvalidGroupException
	{
		final String groupType = JsonUtil.getAsString(groupElement, "type");
		
		if (null == groupType)
		{
			throw new IllegalArgumentException("indicatorType cannot be null");
		}
		
		switch (groupType)
		{
			case "Adversary":
				return GroupType.ADVERSARY;
			case "Campaign":
				return GroupType.CAMPAIGN;
			case "Document":
				return GroupType.DOCUMENT;
			case "Email":
				return GroupType.EMAIL;
			case "Event":
				return GroupType.EVENT;
			case "Incident":
				return GroupType.INCIDENT;
			case "Report":
				return GroupType.REPORT;
			case "Signature":
				return GroupType.SIGNATURE;
			case "Threat":
				return GroupType.THREAT;
			case "Intrusion Set":
				return GroupType.INTRUSION_SET;
			default:
				throw new InvalidGroupException("Invalid group type: " + groupType);
		}
	}
	
	private Group createGroup(final GroupType groupType, final JsonElement groupElement) throws InvalidGroupException
	{
		switch (groupType)
		{
			case ADVERSARY:
				return new Adversary();
			case CAMPAIGN:
				return new Campaign();
			case DOCUMENT:
				return new Document();
			case EMAIL:
				return new Email();
			case EVENT:
				return new Event();
			case INCIDENT:
				return new Incident();
			case REPORT:
				return new Report();
			case SIGNATURE:
				return new Signature();
			case THREAT:
				return new Threat();
			case INTRUSION_SET:
				return new IntrusionSet();
			default:
				throw new InvalidGroupException("Invalid group type: " + groupType.toString());
		}
	}
	
	private Indicator convertToIndicator(final JsonElement indicatorElement) throws InvalidIndicatorException
	{
		final String indicatorType = JsonUtil.getAsString(indicatorElement, "type");
		
		if (null == indicatorType)
		{
			throw new IllegalArgumentException("indicatorType cannot be null");
		}
		
		final String summary = JsonUtil.getAsString(indicatorElement, "summary");
		logger.debug("Converting Indicator: {}", summary);
		
		switch (indicatorType)
		{
			case "Address":
				Address address = new Address();
				address.setIp(summary);
				return address;
			case "EmailAddress":
				EmailAddress emailAddress = new EmailAddress();
				emailAddress.setAddress(summary);
				return emailAddress;
			case "File":
				return IndicatorUtil.createFile(summary);
			case "Host":
				Host host = new Host();
				host.setHostName(summary);
				return host;
			case "Url":
			case "URL":
				Url url = new Url();
				url.setText(summary);
				return url;
			default:
				CustomIndicator customIndicator = new CustomIndicator(indicatorType);
				customIndicator.setSummary(summary);
				return customIndicator;
		}
	}
	
	private void copySecurityLabels(final JsonArray securityLabelArray, final Item item)
	{
		//make sure the array is not null
		if (null != securityLabelArray)
		{
			// for each of the elements in the array
			for (JsonElement securityLabelElement : securityLabelArray)
			{
				SecurityLabel securityLabel = new SecurityLabel();
				securityLabel.setName(JsonUtil.getAsString(securityLabelElement, "name"));
				securityLabel.setDescription(JsonUtil.getAsString(securityLabelElement, "description"));
				securityLabel.setColor(JsonUtil.getAsString(securityLabelElement, "color"));
				
				String dateAdded = JsonUtil.getAsString(securityLabelElement, "dateAdded");
				if (null != dateAdded)
				{
					try
					{
						securityLabel.setDateAdded(BatchItemSerializer.DEFAULT_DATE_FORMATTER.parse(dateAdded));
					}
					catch (ParseException e)
					{
						logger.warn(e.getMessage(), e);
					}
				}
				
				item.getSecurityLabels().add(securityLabel);
			}
		}
	}
	
	private Attribute convertToAttribute(final JsonObject jsonObject)
	{
		Attribute attribute = new Attribute();
		attribute.setType(JsonUtil.getAsString(jsonObject, "type"));
		attribute.setValue(JsonUtil.getAsString(jsonObject, "value"));
		attribute.setDisplayed(JsonUtil.getAsBoolean(jsonObject, "displayed"));
		
		return attribute;
	}
}
