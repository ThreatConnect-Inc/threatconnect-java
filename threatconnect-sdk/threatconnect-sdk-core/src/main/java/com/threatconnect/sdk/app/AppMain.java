package com.threatconnect.sdk.app;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.AppConfigInitializationException;
import com.threatconnect.app.apps.AppLauncher;
import com.threatconnect.app.apps.ParamFileAppConfig;
import com.threatconnect.app.apps.SystemPropertiesAppConfig;
import com.threatconnect.app.apps.UnsupportedAppConfigException;
import com.threatconnect.sdk.app.exception.AppInitializationException;
import com.threatconnect.sdk.app.exception.MultipleAppClassFoundException;
import com.threatconnect.sdk.app.exception.NoAppClassFoundException;
import com.threatconnect.sdk.app.executor.AppExecutor;
import com.threatconnect.sdk.app.executor.AppExecutorFactory;
import com.threatconnect.sdk.log.ServerLogger;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class AppMain extends SDKAppLauncher<App>
{
	private static final Logger logger = LoggerFactory.getLogger(AppMain.class);
	
	public AppMain()
	{
		this(findAppConfig());
	}
	
	public AppMain(final AppConfig appConfig)
	{
		super(App.class, appConfig);
	}
	
	@Override
	public void launch() throws ClassNotFoundException, IOException, AppInitializationException
	{
		logger.info("Retrieving class to execute...");
		final Class<? extends App> appClass = getAppClassToExecute();
		
		// reconfigure the log file for this app
		logger.info("Configuring App Logging...");
		File logFile = new File(getAppConfig().getTcLogPath() + File.separator + appClass.getSimpleName() + ".log");
		LoggerUtil.reconfigureGlobalLogger(logFile, getAppConfig());
		
		// set whether or not api logging is enabled
		ServerLogger.getInstance(getAppConfig()).setEnabled(getAppConfig().isTcLogToApi());
		
		//holds the resulting exit code from the app execution
		final int exitCode;
		
		try
		{
			//retrieve the type of execution that this app should use
			AppExecutor appExecutor = AppExecutorFactory.create(getAppConfig(), appClass);
			
			//execute the app and retrieve the exit code
			exitCode = appExecutor.execute();
		}
		finally
		{
			// flush the logs to the server
			ServerLogger.getInstance(getAppConfig()).flushToServer();
		}
		
		// exit the app with this exit status
		System.exit(exitCode);
	}
	
	public static void main(String[] args) throws Exception
	{
		new AppMain().launch();
	}
}
