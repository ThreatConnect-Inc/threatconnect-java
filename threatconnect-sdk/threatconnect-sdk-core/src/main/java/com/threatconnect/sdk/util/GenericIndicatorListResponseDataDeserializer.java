package com.threatconnect.sdk.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.threatconnect.sdk.server.entity.CustomIndicator;
import com.threatconnect.sdk.server.entity.EmailAddress;
import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.entity.Url;
import com.threatconnect.sdk.server.response.entity.data.GenericIndicatorListResponseData;

public class GenericIndicatorListResponseDataDeserializer extends JsonDeserializer<GenericIndicatorListResponseData> {
	private static final ObjectMapper mapper = new ObjectMapper();
    private static final CollectionType collectionType =
            TypeFactory
            .defaultInstance()
            .constructCollectionType(List.class, CustomIndicator.class);
    
	@Override
	public GenericIndicatorListResponseData deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		GenericIndicatorListResponseData result = new GenericIndicatorListResponseData();
		ObjectCodec oc = p.getCodec();
		ObjectNode rootNode = oc.readTree(p);
		Iterator<String> keys = rootNode.fieldNames();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			//System.out.println("json node key="+key);
			//ignore "resultCount"
			if("resultCount".compareTo(key) != 0)
			{
				JsonNode value = rootNode.get(key);
				switch(key) {
				case "host":
				{
					List<Host> indicators =  mapper.reader(TypeFactory
				            .defaultInstance()
				            .constructCollectionType(List.class, Host.class)).readValue(value);					
					result.getData().addAll(indicators);
				}
					break;
				case "emailAddress":
				{
					List<EmailAddress> indicators =  mapper.reader(TypeFactory
				            .defaultInstance()
				            .constructCollectionType(List.class, EmailAddress.class)).readValue(value);					
					result.getData().addAll(indicators);
				}
					break;
				case "file":
				{
					List<File> indicators =  mapper.reader(TypeFactory
				            .defaultInstance()
				            .constructCollectionType(List.class, File.class)).readValue(value);					
					result.getData().addAll(indicators);
				}
					break;
				case "url":
				{
					List<Url> indicators =  mapper.reader(TypeFactory
				            .defaultInstance()
				            .constructCollectionType(List.class, Url.class)).readValue(value);					
					result.getData().addAll(indicators);
				}
					break;
					
					
				default:
				{
					List<CustomIndicator> indicators =  mapper.reader(collectionType).readValue(value);
					for(CustomIndicator indicator : indicators) {
						indicator.setIndicatorType(key);
					}
					result.getData().addAll(indicators);
				}
					break;
				}
			}
			
		}
		return result;

	}

}
