package com.threatconnect.sdk.parser;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.model.CustomIndicator;
import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.Host;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.Threat;
import com.threatconnect.sdk.model.util.TagUtil;
import com.threatconnect.sdk.parser.service.save.SaveService;
import com.threatconnect.sdk.parser.util.AttributeHelper;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Greg Marut
 */
public abstract class SaveServiceIT
{
	@Test
	public void runTest() throws IOException
	{
		SaveService saveService = getSaveService();
		
		saveService.saveItems(buildItems());
	}
	
	protected List<Item> buildItems()
	{
		File file = new File();
		file.setMd5("2E3FE54F862985BDC9BFA0C5792B0C20");
		file.setSha1("120C5D9B7E5F61B8BE8D7702EFD3296C58DD475A");
		file.setSha256("1C92296D0C1A9F13AF0A8D95CBF3EDC5ECA05C8EBB5EE60F37E4F1C820A7E93D");
		file.setSize(11111);
		file.setRating(3.0);
		file.setConfidence(50.0);
		
		AttributeHelper.addSourceAttribute(file, "http://example.com");
		AttributeHelper.addSourceDateTimeAttribute(file, new Date());
		TagUtil.addTag("Test Data", file);
		
		Host host = new Host();
		host.setHostName("malware.bad");
		
		AttributeHelper.addSourceAttribute(host, "http://somewebsite.com");
		AttributeHelper.addSourceDateTimeAttribute(host, new Date());
		TagUtil.addTag("Test Data", host);
		
		file.setRating(5.0);
		file.setConfidence(100.0);
		
		Threat threat = new Threat();
		threat.setName("This is a test threat");
		
		AttributeHelper.addSourceAttribute(threat, "http://lotsofthreats.bad");
		AttributeHelper.addSourceDateTimeAttribute(threat, new Date());
		TagUtil.addTag("Test Data", threat);
		
		threat.getAssociatedItems().add(file);
		threat.getAssociatedItems().add(host);
		
		return Arrays.asList(threat);
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
		Configuration configuration = new Configuration(appConfig, appConfig.getTcApiPath(), appConfig.getTcApiAccessID(),
			appConfig.getTcApiUserSecretKey(), appConfig.getApiDefaultOrg());
		
		return configuration;
	}
	
	protected abstract SaveService getSaveService();
}
