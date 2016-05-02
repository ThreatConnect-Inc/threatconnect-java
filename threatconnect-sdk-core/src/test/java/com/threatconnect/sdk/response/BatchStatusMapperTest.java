package com.threatconnect.sdk.response;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.threatconnect.sdk.server.entity.BatchStatus.Status;
import com.threatconnect.sdk.server.response.entity.BatchStatusResponse;

public class BatchStatusMapperTest
{
	@Test
	public void mapBatchStatusResponse() throws JsonParseException, JsonMappingException, IOException
	{
		final ObjectMapper mapper = new ObjectMapper();
		final String content = "{\"status\":\"Success\",\"data\":{\"batchStatus\":{\"id\":99,\"status\":\"Queued\"}}}";
		BatchStatusResponse result = mapper.readValue(content, BatchStatusResponse.class);
		
		Assert.assertEquals(99, result.getItem().getId().intValue());
		Assert.assertEquals(Status.Queued, result.getItem().getStatus());
	}
}
