package com.threatconnect.sdk.parser;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.app.SdkAppConfig;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.parser.service.save.SaveResults;
import com.threatconnect.sdk.parser.service.writer.BatchWriter;
import com.threatconnect.sdk.server.entity.BatchConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Collections;

/**
 * @author Greg Marut
 */
public class BatchWriterIT
{
	private static final Logger logger = LoggerFactory.getLogger(BatchWriterIT.class);
	
	private BatchWriter batchWriter;
	private JsonParser jsonParser;
	
	@Before
	public void init() throws IOException
	{
		Connection connection = new Connection(getConfiguration(SdkAppConfig.getInstance()));
		batchWriter = new BatchWriter(connection, Collections.emptyList());
		jsonParser = new JsonParser();
	}
	
	@Test
	public void sampleTest() throws Exception
	{
		File file = new File("src/test/resources/batch_sample4.json");
		Assert.assertTrue(file.exists());
		
		try (InputStream inputStream = new FileInputStream(file))
		{
			JsonElement jsonElement = jsonParser.parse(new InputStreamReader(inputStream));
			BatchWriter.BatchUploadResponse response =
				uploadIndicators(jsonElement, "System", BatchConfig.AttributeWriteType.Replace,
					BatchConfig.Action.Create, BatchConfig.Version.V2);
			SaveResults results = pollBatch(response, "System", 0, 1);
			logger.info(new Gson().toJson(results));
		}
	}
	
	protected BatchWriter.BatchUploadResponse uploadIndicators(final JsonElement json,
		final String ownerName, final BatchConfig.AttributeWriteType attributeWriteType,
		final BatchConfig.Action action, BatchConfig.Version version) throws Exception
	{
		Method method = batchWriter.getClass().getDeclaredMethod("uploadIndicators", JsonElement.class, String.class,
			BatchConfig.AttributeWriteType.class, BatchConfig.Action.class, BatchConfig.Version.class);
		method.setAccessible(true);
		return (BatchWriter.BatchUploadResponse) method
			.invoke(batchWriter, json, ownerName, attributeWriteType, action, version);
	}
	
	private SaveResults pollBatch(final BatchWriter.BatchUploadResponse batchUploadResponse, final String ownerName,
		final int batchIndex, final int batchTotal) throws Exception
	{
		Method method = batchWriter.getClass().getDeclaredMethod("pollBatch", BatchWriter.BatchUploadResponse.class,
			String.class, int.class, int.class);
		method.setAccessible(true);
		return (SaveResults) method.invoke(batchWriter, batchUploadResponse, ownerName, batchIndex, batchTotal);
	}
	
	/**
	 * Creates the configuration object
	 *
	 * @param appConfig
	 * @return
	 */
	protected Configuration getConfiguration(final AppConfig appConfig)
	{
		// create the configuration for the threatconnect server
		Configuration configuration = new Configuration(appConfig.getTcApiPath(), appConfig.getTcApiAccessID(),
			appConfig.getTcApiUserSecretKey(), appConfig.getApiDefaultOrg(), appConfig.getTcToken(),
			appConfig.getTcTokenExpires());
		
		return configuration;
	}
}
