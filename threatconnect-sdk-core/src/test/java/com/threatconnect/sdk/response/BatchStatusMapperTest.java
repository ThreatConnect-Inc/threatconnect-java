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
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void mapBatchStatusResponseCreate() throws JsonParseException, JsonMappingException, IOException
	{
		final String content = "{\"status\":\"Success\",\"data\":{\"batchStatus\":{\"id\":99,\"status\":\"Queued\"}}}";
		BatchStatusResponse result = mapper.readValue(content, BatchStatusResponse.class);
		
		Assert.assertEquals(99, result.getItem().getId().intValue());
		Assert.assertEquals(Status.Queued, result.getItem().getStatus());
	}
	
	@Test
	public void mapBatchStatusResponseRead() throws JsonParseException, JsonMappingException, IOException
	{
		final String content =
			"{\"status\":\"Success\",\"data\":{\"batchStatus\":{\"id\":105,\"status\":\"Completed\",\"errorCount\":0,\"successCount\":8,\"unprocessCount\":0}}}";
		BatchStatusResponse result = mapper.readValue(content, BatchStatusResponse.class);
		
		Assert.assertEquals(105, result.getItem().getId().intValue());
		Assert.assertEquals(8, result.getItem().getSuccessCount().intValue());
		Assert.assertEquals(0, result.getItem().getErrorCount().intValue());
		Assert.assertEquals(0, result.getItem().getUnprocessCount().intValue());
		Assert.assertEquals(Status.Completed, result.getItem().getStatus());
	}
}
