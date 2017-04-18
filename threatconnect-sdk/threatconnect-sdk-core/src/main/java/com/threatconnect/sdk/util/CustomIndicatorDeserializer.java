package com.threatconnect.sdk.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.threatconnect.sdk.server.entity.CustomIndicator;
import com.threatconnect.sdk.server.entity.Indicator;

public class CustomIndicatorDeserializer extends JsonDeserializer<CustomIndicator> {

	@Override
	public CustomIndicator deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		List<String> indicatorFields = getDeclaredFields();
		ObjectCodec oc = p.getCodec();
        ObjectNode node = oc.readTree(p);
        CustomIndicator obj = new CustomIndicator(oc.treeToValue(node, Indicator.class));
        Map<String, String> map = new HashMap<String, String>(); 
        //indicator specific fields
        Iterator<String> keys = node.fieldNames();
        while( keys.hasNext() ) {
            String key = (String)keys.next();
            String value = node.get(key).textValue();
            if(!indicatorFields.contains(key)) {
            	map.put(key,  value);
            }
        }
        obj.setMap(map);
        return obj;
        
	}
	
	private List<String> getDeclaredFields()
	{
		//these are the fields for indicator, can use reflection to retrieve instead
		List<String> result = new ArrayList<String>();
		result.add("id");
		result.add("owner");
		result.add("ownerName");
		result.add("dateAdded");
		result.add("type");
		result.add("lastModified");
		result.add("rating");
		result.add("confidence");
		result.add("threatAssessRating");
		result.add("webLink");
		result.add("source");
		result.add("description");
		result.add("summary");
		return result;

	}

}
