package com.threatconnect.sdk.log;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.threatconnect.sdk.app.AppConfig;
import com.threatconnect.sdk.app.LoggerUtil;

public class ServerLogMain
{
	private static final String LOG_FILE = "target/log.log";
	
	public static void main(String[] args) throws IOException
	{
		File logFile = new File(LOG_FILE);
		logFile.delete();
		
		AppConfig appConfig = AppConfig.getInstance();
		
		LoggerUtil.reconfigureGlobalLogger(logFile, appConfig);
		Logger logger = LoggerFactory.getLogger(ServerLogMain.class);
		
		logger.info("Log Test 1");
		logger.info("Log Test 2");
		
		Exception e = new IllegalArgumentException("Some argument was not valid");
		logger.error(e.getMessage(), e);
		
		logger.info("Log Test 3");
		
		Assert.assertTrue(logFile.length() > 0);
		
		ServerLogger.getInstance().flushToServer(false);
	}
}
