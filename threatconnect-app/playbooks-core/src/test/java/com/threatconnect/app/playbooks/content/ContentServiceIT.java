package com.threatconnect.app.playbooks.content;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.SystemPropertiesAppConfig;
import com.threatconnect.app.playbooks.content.accumulator.ContentException;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.db.DBServiceFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This integration test is used for testing simple data reads/writes from an external database
 * Configure the Content Service using the VM arguments
 *
 * Example:
 * -Dtc_playbook_db_type=Redis
 * -Dtc_playbook_db_context=test
 * -Dtc_playbook_db_path=localhost
 * -Dtc_playbook_db_port=6379
 *
 * @author Greg Marut
 */
public class ContentServiceIT
{
	public static final String KEY_BINARY_DATA = "#Test:0:binary!Binary";
	
	private AppConfig appConfig;
	private DBService dbService;
	private ContentService contentService;
	
	@Before
	public void init()
	{
		appConfig = new SystemPropertiesAppConfig();
		dbService = DBServiceFactory.buildFromAppConfig(appConfig);
		contentService = new ContentService(dbService);
	}
	
	@Test
	public void saveAndReadBinaryData() throws ContentException
	{
		final String testData = "This is a Test";
		final byte[] bytes = testData.getBytes();
		
		//write the data to the service
		contentService.writeBinary(KEY_BINARY_DATA, bytes);
		
		//read the data from the service
		final byte[] result = contentService.readBinary(KEY_BINARY_DATA);
		
		Assert.assertArrayEquals(bytes, result);
	}
}
