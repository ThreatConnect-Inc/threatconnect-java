package com.threatconnect.sdk.log;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.app.LoggerUtil;
import com.threatconnect.sdk.app.SdkAppConfig;
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
		
		AppConfig appConfig = SdkAppConfig.getInstance();
		
		LoggerUtil.reconfigureGlobalLogger(logFile, appConfig);
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.info("Log Test");
		
		Assert.assertTrue(logFile.length() > 0);
	}
}
