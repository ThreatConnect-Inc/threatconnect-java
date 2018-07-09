package com.threatconnect.sdk.log;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.SystemPropertiesAppConfig;
import com.threatconnect.sdk.app.LoggerUtil;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ServerLogMain
{
	private static final String LOG_FILE = "target/log.log";
	
	public static void main(String[] args) throws IOException
	{
		File logFile = new File(LOG_FILE);
		logFile.delete();
		
		AppConfig appConfig = new SystemPropertiesAppConfig();
		appConfig.set(AppConfig.TC_TOKEN,
			"1:301:a2f6c6e7-2952-4c07-8249-4c75c477cb40:1468248049:Ok35Q:2vmDWmqJoSQKAFdwfzeLViYJBByD/0qBArJaspvLPgM=");
			
		LoggerUtil.reconfigureGlobalLogger(logFile, appConfig);
		Logger logger = LoggerFactory.getLogger(ServerLogMain.class);
		
		logger.info("Log Test 1");
		logger.info("Log Test 2");
		
		Exception e = new IllegalArgumentException("Some argument was not valid");
		logger.error(e.getMessage(), e);
		
		logger.info("Log Test 3");
		
		Assert.assertTrue(logFile.length() > 0);
		
		ServerLogger.getInstance(appConfig).flushToServer();
		
		logger.debug("Log Test 4");
		logger.debug("Log Test 5");
		
		ServerLogger.getInstance(appConfig).flushToServer();
	}
}
