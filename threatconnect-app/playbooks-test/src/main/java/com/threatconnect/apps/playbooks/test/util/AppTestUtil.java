package com.threatconnect.apps.playbooks.test.util;

import com.threatconnect.app.apps.AppConfig;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author Greg Marut
 */
public class AppTestUtil
{
	private static final Logger logger = LoggerFactory.getLogger(AppTestUtil.class);
	
	public static void configureAppTestDirectories(final String appName, final AppConfig appConfig)
	{
		final String pathIn = "target/apptest/" + appName + "/in";
		final String pathOut = "target/apptest/" + appName + "/out";
		final String pathTemp = "target/apptest/" + appName + "/tmp";
		final String pathLog = "target/apptest/" + appName + "/log";
		
		File[] dirs = {
			new File(pathIn), new File(pathOut), new File(pathTemp), new File(pathLog)
		};
		
		//for each of the dirs
		for (File dir : dirs)
		{
			//check to see directory needs to be created
			if (!dir.exists())
			{
				dir.mkdirs();
			}
			else
			{
				try
				{
					//clean the directory
					logger.debug("Cleaning directory: " + dir.getAbsolutePath());
					FileUtils.cleanDirectory(dir);
				}
				catch (IOException e)
				{
					logger.warn(e.getMessage(), e);
				}
			}
		}
		
		appConfig.set(AppConfig.TC_OUT_PATH, pathOut);
		appConfig.set(AppConfig.TC_TEMP_PATH, pathTemp);
		appConfig.set(AppConfig.TC_IN_PATH, pathIn);
		appConfig.set(AppConfig.TC_LOG_PATH, pathLog);
	}
}
