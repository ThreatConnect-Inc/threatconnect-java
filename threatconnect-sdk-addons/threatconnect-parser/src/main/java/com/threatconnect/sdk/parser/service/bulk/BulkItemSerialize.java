package com.threatconnect.sdk.parser.service.bulk;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.threatconnect.sdk.model.Attribute;
import com.threatconnect.sdk.model.Group;
import com.threatconnect.sdk.model.GroupType;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.ItemType;
import com.threatconnect.sdk.model.util.ItemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BulkItemSerialize
{
	private static final Logger logger = LoggerFactory.getLogger(BulkItemSerialize.class);
	
	//holds the map of item to xids
	private final Map<Item, String> xidMap;
	
	//holds the map to store the indicator and the list of associated group xids
	private final Map<Indicator, List<String>> associatedGroupXids;
	
	//holds the collection of items to serialize
	private final Collection<? extends Item> items;
	
	private JsonObject root;
	
	public BulkItemSerialize(final Item... items)
	{
		this(Arrays.asList(items));
	}
	
	public BulkItemSerialize(final Collection<? extends Item> items)
	{
		this.items = items;
		this.xidMap = new HashMap<Item, String>();
		this.associatedGroupXids = new HashMap<Indicator, List<String>>();
	}
	
	public synchronized JsonObject convertToJson()
	{
		//check to see if the root object is null
		if (null == root)
		{
			// create the root indicator json object
			root = new JsonObject();
			
			// create a new json array to hold all of the indicators
			JsonArray indicatorsArray = new JsonArray();
			root.add("indicator", indicatorsArray);
			
			// create a new json array to hold all of the groups
			JsonArray groupsArray = new JsonArray();
			root.add("group", groupsArray);
			
			//split all of the items based on groups and indicators
			Set<Group> groups = new HashSet<Group>();
			Set<Indicator> indicators = new HashSet<Indicator>();
			ItemUtil.separateGroupsAndIndicators(items, groups, indicators);
			
			//generate xids for each of the groups and indicators
			populateXidMap(groups, indicators);
			
			//for each of the groups
			for (Group group : groups)
			{
				//for each of the associations on this group
				for (Item item : group.getAssociatedItems())
				{
					//check to see if the associated item is an indicator
					if (ItemType.INDICATOR.equals(item.getItemType()))
					{
						//add this group's xid as an association for this indicator
						addAssociatedGroupXid((Indicator) item, xidMap.get(group));
					}
				}
				
				//convert this group to a json object to be added to the groups array
				JsonObject groupJsonObject = convertToJson(group);
				groupsArray.add(groupJsonObject);
			}
		}
		
		return root;
	}
	
	private JsonObject convertToJson(final Group group)
	{
		// holds the json object that will represent this group
		JsonObject groupJsonObject = new JsonObject();
		
		groupJsonObject.addProperty("name", group.getName());
		groupJsonObject.addProperty("type", convertGroupType(group.getGroupType()));
		
		JsonArray associatedGroupXidArray = new JsonArray();
		groupJsonObject.add("associatedGroupXid", associatedGroupXidArray);
		
		//for each of the associations on this group
		for (Item item : group.getAssociatedItems())
		{
			//check to see if the associated item is another group
			if (ItemType.GROUP.equals(item.getItemType()))
			{
				//add this item's xid to the assocaited group xid array
				associatedGroupXidArray.add(xidMap.get(item));
			}
		}
		
		return groupJsonObject;
	}
	
	private JsonObject convertToJson(final Indicator indicator)
	{
		// holds the json object that will represent this indicator
		JsonObject indicatorJsonObject = new JsonObject();
		
		indicatorJsonObject.addProperty("rating", indicator.getRating());
		indicatorJsonObject.addProperty("confidence", indicator.getConfidence());
		indicatorJsonObject.addProperty("summary", indicator.toString());
		indicatorJsonObject.addProperty("type", indicator.getIndicatorType());
		
		addTagsAndAttributes(indicatorJsonObject, indicator);
		
		return indicatorJsonObject;
	}
	
	private void addTagsAndAttributes(final JsonObject jsonObject, final Item item)
	{
		// check to see if this item has attributes
		if (!item.getAttributes().isEmpty())
		{
			// create a new attribute array for this object and add it
			JsonArray attributeJsonArray = new JsonArray();
			jsonObject.add("attribute", attributeJsonArray);
			
			// for each of the attributes
			for (Attribute attribute : item.getAttributes())
			{
				// convert this attribute to an object and add it to the array
				attributeJsonArray.add(convertToJson(attribute));
			}
		}
		
		// check to see if this item has any tags
		if (!item.getTags().isEmpty())
		{
			// create a new attribute array for this object and add it
			JsonArray tagJsonArray = new JsonArray();
			jsonObject.add("tag", tagJsonArray);
			
			// for each of the attributes
			for (String tag : item.getTags())
			{
				// holds the json object that will represent this tag
				JsonObject attributeJsonObject = new JsonObject();
				attributeJsonObject.addProperty("name", tag);
				tagJsonArray.add(attributeJsonObject);
			}
		}
	}
	
	private JsonObject convertToJson(final Attribute attribute)
	{
		// holds the json object that will represent this attribute
		JsonObject attributeJsonObject = new JsonObject();
		
		attributeJsonObject.addProperty("type", attribute.getType());
		attributeJsonObject.addProperty("value", attribute.getValue());
		attributeJsonObject.addProperty("displayed", (null != attribute.getDisplayed() && attribute.getDisplayed()));
		
		return attributeJsonObject;
	}
	
	private String convertGroupType(final GroupType groupType)
	{
		switch (groupType)
		{
			
			case ADVERSARY:
				return "Adversary";
			case CAMPAIGN:
				return "Campaign";
			case DOCUMENT:
				return "Document";
			case EMAIL:
				return "Email";
			case INCIDENT:
				return "Incident";
			case SIGNATURE:
				return "Signature";
			case THREAT:
				return "Threat";
			default:
				return null;
		}
	}
	
	private void addAssociatedGroupXid(final Indicator indicator, final String xid)
	{
		//retrieve the list of group xids for this indicator
		List<String> xids = associatedGroupXids.get(indicator);
		
		//make sure the list is not null, otherwise, create it
		if (null == xids)
		{
			xids = new ArrayList<String>();
			associatedGroupXids.put(indicator, xids);
		}
		
		xids.add(xid);
	}
	
	private void populateXidMap(final Set<Group> groups, final Set<Indicator> indicators)
	{
		//for each of the groups
		for (Group group : groups)
		{
			xidMap.put(group, generateXid());
		}
		
		//for each of the indicators
		for (Indicator indicator : indicators)
		{
			xidMap.put(indicator, generateXid());
		}
	}
	
	private String generateXid()
	{
		return UUID.randomUUID().toString();
	}
}
