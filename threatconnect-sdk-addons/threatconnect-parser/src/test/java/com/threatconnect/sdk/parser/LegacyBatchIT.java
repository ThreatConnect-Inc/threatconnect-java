package com.threatconnect.sdk.parser;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.app.SdkAppConfig;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.parser.service.save.LegacyBatchApiSaveService;
import com.threatconnect.sdk.parser.service.save.SaveService;
import com.threatconnect.sdk.parser.util.AttributeHelper;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

/**
 * @author Greg Marut
 */
public class LegacyBatchIT
{
	@Test
	public void saveSingleItem() throws IOException
	{
		SaveService saveService = new LegacyBatchApiSaveService(getConfiguration(SdkAppConfig.getInstance()),
			SdkAppConfig.getInstance().getApiDefaultOrg());
		
		File file = new File();
		file.setMd5("2E3FE54F862985BDC9BFA0C5792B0C20");
		file.setSha1("120C5D9B7E5F61B8BE8D7702EFD3296C58DD475A");
		file.setSha256("1C92296D0C1A9F13AF0A8D95CBF3EDC5ECA05C8EBB5EE60F37E4F1C820A7E93D");
		file.setSize(11111);
		
		AttributeHelper.addSourceAttribute(file, "http://example.com");
		AttributeHelper.addSourceDateTimeAttribute(file, new Date());
		
		saveService.saveItems(Collections.singletonList(file));
	}
	
	/**
	 * Creates the configuration object
	 *
	 * @param appConfig
	 * @return
	 */
	private Configuration getConfiguration(final AppConfig appConfig)
	{
		// create the configuration for the threatconnect server
		Configuration configuration = new Configuration(appConfig.getTcApiPath(), appConfig.getTcApiAccessID(),
			appConfig.getTcApiUserSecretKey(), appConfig.getApiDefaultOrg(), appConfig.getTcToken(),
			appConfig.getTcTokenExpires());
		
		return configuration;
	}
}
