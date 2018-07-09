package com.threatconnect.sdk.log;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.SystemPropertiesAppConfig;
import com.threatconnect.sdk.app.LoggerUtil;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class LoggingTest
{
	private static final String LOG_FILE = "target/log.log";
	
	@Test
	public void configureLoggerTest() throws IOException
	{
		File logFile = new File(LOG_FILE);
		logFile.delete();
		
		AppConfig appConfig = new SystemPropertiesAppConfig();
		
		LoggerUtil.reconfigureGlobalLogger(logFile, appConfig);
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.warn("Log Test");
		
		Assert.assertTrue(logFile.length() > 0);
	}
}
