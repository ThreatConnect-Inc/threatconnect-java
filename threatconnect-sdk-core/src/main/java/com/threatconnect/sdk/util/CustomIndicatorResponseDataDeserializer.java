package com.threatconnect.sdk.util;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.threatconnect.sdk.server.entity.CustomIndicator;
import com.threatconnect.sdk.server.response.entity.data.CustomIndicatorResponseData;

public class CustomIndicatorResponseDataDeserializer extends JsonDeserializer<CustomIndicatorResponseData> {

	@Override
	public CustomIndicatorResponseData deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		CustomIndicatorResponseData result = new CustomIndicatorResponseData();
		ObjectCodec oc = p.getCodec();
		ObjectNode rootNode = oc.readTree(p);
		Iterator<String> keys = rootNode.fieldNames();
		if (keys.hasNext()) {
			String key = (String) keys.next();
			result.setType(key);
			JsonNode value = rootNode.get(key);
			CustomIndicator obj = oc.treeToValue(value, CustomIndicator.class);
			//obj.setType(key);
			obj.setIndicatorType(key);
			result.setCustomIndicator(obj);
		}
		return result;

	}

}
