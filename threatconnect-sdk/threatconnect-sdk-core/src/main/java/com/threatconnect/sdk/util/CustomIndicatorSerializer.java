package com.threatconnect.sdk.util;

import java.io.IOException;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.threatconnect.sdk.server.entity.CustomIndicator;

public  class CustomIndicatorSerializer extends JsonSerializer<CustomIndicator> {

	@Override
	public void serialize(CustomIndicator value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		
 		Map<String, String> map = value.getMap();
 		DateTimeFormatter dtfmt = ISODateTimeFormat.dateTimeNoMillis();
 		
 		ObjectNode obj = new ObjectNode(JsonNodeFactory.instance);
 		
 		
 		 if(value.getOwner() != null)
         {
             ObjectNode ownerNode = new ObjectNode(JsonNodeFactory.instance);
             ownerNode.put("id", value.getOwner().getId());
             ownerNode.put("name", value.getOwner().getName());
             ownerNode.put("type", value.getOwner().getType());
             obj.set("owner", ownerNode);
         }

 		 if(value.getOwnerName() != null)
 			 obj.put("ownerName", value.getOwnerName());
 		 
 		 if(value.getId() != null)
 			 obj.put("id", value.getId());
 		obj.put("type", value.getType());
 		if(value.getDateAdded() != null)
 			obj.put("dateAdded", dtfmt.print(new DateTime(value.getDateAdded())));
 		if(value.getLastModified() != null)
 			obj.put("lastModified", dtfmt.print(new DateTime(value.getLastModified())));
 		if(value.getWebLink() != null)
 			obj.put("webLink", value.getWebLink());
        if(value.getRating() != null && !value.getRating().equals(""))
        {
            obj.put("rating", value.getRating());
        }
        if(value.getConfidence() != null && !value.getConfidence().equals(""))
        {
            obj.put("confidence", value.getConfidence());
        }
        if(value.getThreatAssessRating() != null && !value.getThreatAssessRating().equals(""))
        {
            obj.put("threatAssessRating", value.getThreatAssessRating());
        }
        if(value.getThreatAssessConfidence() != null && !value.getThreatAssessConfidence().equals(""))
        {
            obj.put("threatAssessConfidence", value.getThreatAssessConfidence());
        }
        if(value.getSource() != null && !value.getSource().equals(""))
        {
            obj.put("source", value.getSource());
        }
        if(value.getDescription() != null && !value.getDescription().equals(""))
        {
            obj.put("description", value.getDescription());
        }
        if(value.getObservationCount() != null && !value.getObservationCount().equals(""))
        {
            obj.put("observationCount", value.getObservationCount());
        }
        /*
        if(value.getLastObserved() != null && !value.getLastObserved().equals(""))
        {
            obj.put("lastObserved", dtfmt.print(new DateTime(value.getLastObserved())));
        }
        */
        if(value.getFalsePositiveCount() != null && !value.getFalsePositiveCount().equals(""))
        {
            obj.put("falsePositiveCount", value.getFalsePositiveCount());
        }
        if(value.getFalsePositiveLastReported() != null && !value.getFalsePositiveLastReported().equals(""))
        {
            obj.put("falsePositiveLastReported", dtfmt.print(new DateTime(value.getFalsePositiveLastReported())));
        }
        
 		
 		//add custom fields
 		if(map != null) {
    		for(String key : map.keySet()) {
    			obj.put(key, map.get(key));
    		}
    	}
 		//gen.writeStartObject();
        gen.writeObject(obj);
        //gen.writeEndObject();
	}
}
