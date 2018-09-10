package com.threatconnect.sdk.parser.service.writer;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.app.SdkAppConfig;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.parser.service.save.SaveResults;
import com.threatconnect.sdk.server.entity.BatchConfig;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collections;

/**
 * @author Greg Marut
 */
public class LegacyBatchWriterIT
{
	private final Logger logger = LoggerFactory.getLogger(LegacyBatchWriterIT.class);
	
	@Test
	public void upload() throws Exception
	{
		File file = new File("src/test/resources/batchJob_178988.json");
		Assert.assertTrue(file.exists());
		
		final String ownerName = SdkAppConfig.getInstance().getApiDefaultOrg();
		
		//override the legacy batch indicator writer to allow us to upload our own json
		LegacyBatchIndicatorWriter legacyBatchIndicatorWriter = new LegacyBatchIndicatorWriter(new Connection(
			getConfiguration(SdkAppConfig.getInstance())), Collections.emptyList());
		
		try (FileInputStream fileInputStream = new FileInputStream(file))
		{
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement = jsonParser.parse(new InputStreamReader(fileInputStream));
			LegacyBatchIndicatorWriter.BatchUploadResponse batchUploadResponse =
				legacyBatchIndicatorWriter.uploadIndicators(jsonElement, ownerName,
					BatchConfig.AttributeWriteType.Replace, BatchConfig.Action.Create, 1, 1);
			SaveResults saveResults = legacyBatchIndicatorWriter.pollBatch(batchUploadResponse, ownerName, 1, 1);
			logger.debug(saveResults.getFailedItems().entrySet().size() + " failed items");
		}
	}
	
	protected Configuration getConfiguration(final AppConfig appConfig)
	{
		// create the configuration for the threatconnect server
		Configuration configuration = new Configuration(appConfig, appConfig.getTcApiPath(), appConfig.getTcApiAccessID(),
			appConfig.getTcApiUserSecretKey(), appConfig.getApiDefaultOrg(), appConfig.getTcToken(),
			appConfig.getTcTokenExpires());
		
		return configuration;
	}
}
