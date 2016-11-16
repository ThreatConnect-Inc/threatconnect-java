package com.threatconnect.apps.app.test.util;

import com.threatconnect.app.apps.AppConfig;

import java.io.File;

/**
 * @author Greg Marut
 */
public class AppTestUtil
{
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
		}
		
		appConfig.set(AppConfig.TC_OUT_PATH, pathOut);
		appConfig.set(AppConfig.TC_TEMP_PATH, pathTemp);
		appConfig.set(AppConfig.TC_IN_PATH, pathIn);
		appConfig.set(AppConfig.TC_LOG_PATH, pathLog);
	}
}
