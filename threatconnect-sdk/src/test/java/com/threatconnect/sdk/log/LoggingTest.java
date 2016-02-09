package com.threatconnect.sdk.log;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.threatconnect.sdk.app.AppConfig;
import com.threatconnect.sdk.app.LoggerUtil;

public class LoggingTest
{
	private static final String LOG_FILE = "target/log.log";
	
	@Test
	public void configureLoggerTest() throws IOException
	{
		File logFile = new File(LOG_FILE);
		logFile.delete();
		
		AppConfig appConfig = new AppConfig();
		
		LoggerUtil.reconfigureGlobalLogger(logFile, appConfig);
		
		Logger logger = LoggerFactory.getLogger(getClass());
		
		logger.info("Log Test");
	}
}
