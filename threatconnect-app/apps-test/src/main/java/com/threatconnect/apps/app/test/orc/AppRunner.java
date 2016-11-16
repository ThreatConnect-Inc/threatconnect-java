package com.threatconnect.apps.app.test.orc;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.ExitStatus;
import com.threatconnect.app.apps.SystemPropertiesAppConfig;
import com.threatconnect.apps.app.test.config.AppConfiguration;
import com.threatconnect.apps.app.test.config.AppTestConfiguration;
import com.threatconnect.apps.app.test.orc.test.TestFailureException;
import com.threatconnect.apps.app.test.orc.test.Testable;
import com.threatconnect.apps.app.test.util.AppTestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author Greg Marut
 */
public class AppRunner<A extends App> implements Runnable
{
	private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);
	
	private final AppOrchestration<A> rootAppOrchestration;
	
	public AppRunner(final AppOrchestration<A> rootAppOrchestration)
	{
		this.rootAppOrchestration = rootAppOrchestration;
	}
	
	public void run()
	{
		run(rootAppOrchestration);
	}
	
	private void run(final AppOrchestration<A> appOrchestration)
	{
		try
		{
			//create an instance of the app config object and copy the default params from the test config
			AppConfig appConfig = new SystemPropertiesAppConfig()
				.copyFrom(AppTestConfiguration.getInstance().getDefaultAppConfig());
			
			//get the playbook config
			AppConfiguration appConfiguration = appOrchestration.getAppConfiguration();
			
			//retrieve the app
			A app = appOrchestration.getApp();
			
			//set up the test directories for the app
			AppTestUtil.configureAppTestDirectories(app.getClass().getName(), appConfig);
			
			//initialize the app
			app.init(appConfig);
			
			//configure the parameters before running this app
			logger.info("Configuring params for {}", appConfiguration.getAppClass());
			configureParams(appOrchestration, app, appConfig);
			
			//run this app
			run(appOrchestration, app, appConfig);
		}
		catch (Exception e)
		{
			throw new AppRunnerException(e);
		}
	}
	
	private void run(final AppOrchestration<A> appOrchestration, final A app, final AppConfig appConfig) throws Exception
	{
		//get the playbook config
		AppConfiguration appConfiguration = appOrchestration.getAppConfiguration();
		
		logger.info("Running {}", appConfiguration.getAppClass());
		ExitStatus exitStatus = app.execute(appConfig);
		
		//check to see if this was successful
		if (ExitStatus.Success.equals(exitStatus))
		{
			logger.info("{} finished successfully", appConfiguration.getAppClass());
			
			//check to see if there is an onsuccess defined
			if (null != appOrchestration.getOnSuccess())
			{
				//check to see if there are tests to run
				if (!appOrchestration.getOnSuccess()
					.getTests().isEmpty())
				{
					logger.info("Running tests for {}", appConfiguration.getAppClass());
					runTests(appOrchestration.getOnSuccess().getTests(), app);
				}
				
				//check to see if this runner has an app to run
				if (null != appOrchestration.getOnSuccess().getRunApp())
				{
					run(appOrchestration.getOnSuccess().getRunApp());
				}
			}
			//check to see if there is an onfailure definied
			else if (null != appOrchestration.getOnFailure())
			{
				//we were expecting the app to fail but it was successful
				throw new AppRunnerException(
					appConfiguration.getAppClass() + " finished with an unexpected status of \"" + exitStatus
						.toString() + "\"");
			}
		}
		else
		{
			logger.info("{} failed", appConfiguration.getAppClass());
			
			//check to see if there is an onfailure defined
			if (null != appOrchestration.getOnFailure())
			{
				//check to see if there are tests to run
				if (!appOrchestration.getOnFailure().getTests().isEmpty())
				{
					logger.info("Running tests for {}", appConfiguration.getAppClass());
					runTests(appOrchestration.getOnFailure().getTests(), app);
				}
				
				//check to see if this runner has an app to run
				if (null != appOrchestration.getOnFailure().getRunApp())
				{
					run(appOrchestration.getOnFailure().getRunApp());
				}
			}
			//check to see if there is an onsuccess defined
			else if (null != appOrchestration.getOnSuccess())
			{
				//we were expecting the app to succeed but it was failed
				throw new AppRunnerException(
					appConfiguration.getAppClass() + " finished with an unexpected status of \"" + exitStatus
						.toString() + "\"");
			}
		}
	}
	
	private void configureParams(final AppOrchestration<A> appOrchestration, final A app,
		final AppConfig appConfig)
	{
		//for each of the app params
		for (Map.Entry<String, String> appParams : appOrchestration.getAppParams().entrySet())
		{
			//set this app param
			appConfig.set(appParams.getKey(), appParams.getValue());
			logger.debug("Setting App Param \"{}\":\"{}\"", appParams.getKey(), appParams.getValue());
		}
	}
	
	private void runTests(final List<Testable<A>> tests, final A app)
	{
		try
		{
			//for each test
			for (Testable<A> test : tests)
			{
				//run the test
				test.run(app);
			}
		}
		catch (Exception e)
		{
			throw new TestFailureException(e);
		}
	}
}
