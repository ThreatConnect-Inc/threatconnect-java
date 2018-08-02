package com.threatconnect.sdk.app.executor;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.ExitStatus;
import com.threatconnect.sdk.app.LoggerUtil;
import com.threatconnect.sdk.app.exception.AppInstantiationException;
import com.threatconnect.sdk.app.exception.PartialFailureException;
import com.threatconnect.sdk.app.exception.TCMessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAppExecutor extends AppExecutor
{
	private static final Logger logger = LoggerFactory.getLogger(DefaultAppExecutor.class);
	
	public DefaultAppExecutor(final AppConfig appConfig, final Class<? extends App> appClass)
	{
		super(appConfig, appClass);
	}
	
	@Override
	public int execute()
	{
		logger.error("DefaultAppExecutor execute()");
		
		// holds the most recent exit status from the app
		ExitStatus exitStatus = null;
		
		try
		{
			try
			{
				// instantiate and initialize the new app
				App app = instantiateApp();
				initializeApp(app);
				
				try
				{
					// execute this app
					logger.trace("Executing app: " + getAppClass().getName());
					exitStatus = app.execute(getAppConfig());
				}
				catch (PartialFailureException e)
				{
					app.writeMessageTc(e.getMessage());
					logger.error(e.getMessage(), e);
					LoggerUtil.logErr(e, e.getMessage());
					exitStatus = ExitStatus.Partial_Failure;
				}
				catch (TCMessageException e)
				{
					app.writeMessageTc(e.getMessage());
					logger.error(e.getMessage(), e);
					LoggerUtil.logErr(e, e.getMessage());
					exitStatus = ExitStatus.Failure;
				}
			}
			catch (IllegalAccessException | InstantiationException e)
			{
				throw new AppInstantiationException(e, getAppClass());
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			LoggerUtil.logErr(e, e.getMessage());
			exitStatus = ExitStatus.Failure;
		}
		finally
		{
			// ensure that the exit status is not null. This should not normally happen
			if (null == exitStatus)
			{
				LoggerUtil.logErr("Exit status is null.");
				exitStatus = ExitStatus.Failure;
			}
		}
		
		return exitStatus.getExitCode();
	}
	
	protected void initializeApp(final App app)
	{
		// initialize this app
		logger.trace("Initializing App: " + getAppClass().getName());
		app.init(getAppConfig());
	}
}
